// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.model.processing.print;

import static com.braintribe.utils.lcd.CollectionTools2.newSet;

import java.io.StringWriter;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelException;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.ScalarType;
import com.braintribe.model.processing.core.expert.api.MutableDenotationMap;
import com.braintribe.model.processing.core.expert.impl.PolymorphicDenotationMap;

/**
 * @author peter.gazdik
 */
public class BasicCustomizableAssemblyPrinter implements CustomizableAssemblyPrinter {

	private final MutableDenotationMap<GenericEntity, EntityPrinter<?>> fullPrinters = new PolymorphicDenotationMap<>();
	private final MutableDenotationMap<GenericEntity, EntityPrinter<?>> refPrinters = new PolymorphicDenotationMap<>();
	private final Set<Property> ignoredProperties = newSet();

	private boolean writeNulls;

	@Override
	public <T extends GenericEntity> void registerFullPrinter(EntityType<T> denotationType, EntityPrinter<? super T> printer) {
		fullPrinters.put(denotationType, printer);
	}

	@Override
	public <T extends GenericEntity> void registerRefPrinter(EntityType<T> denotationType, EntityPrinter<? super T> printer) {
		refPrinters.put(denotationType, printer);
	}

	@Override
	public void ignoreProperty(Property property) {
		ignoredProperties.add(property);
	}

	public void setWriteNulls(boolean writeNulls) {
		this.writeNulls = writeNulls;
	}

	@Override
	public String toString(Object value) {
		StringWriter writer = new StringWriter();
		toString(value, writer);

		return writer.toString();
	}

	@Override
	public void toString(Object value, StringWriter writer) {
		new BasicPrintingContext(writer).print(value);
	}

	private class BasicPrintingContext implements PrintingContext {

		private final StringWriter writer;
		private final Map<GenericEntity, GenericEntity> visited = new IdentityHashMap<>();

		public BasicPrintingContext(StringWriter writer) {
			this.writer = writer;
		}

		@Override
		public boolean getWriteNulls() {
			return writeNulls;
		}

		@Override
		public boolean ignoreProperty(Property property) {
			return ignoredProperties.contains(property);
		}

		@Override
		public void println(Object object) {
			print(object);
			println();
		}

		@Override
		public void println() {
			println("");
		}

		@Override
		public void print(Object object) {
			if (object == null)
				printNull();

			GenericModelType type = GMF.getTypeReflection().getType(object);
			switch (type.getTypeCode()) {
				case booleanType:
				case dateType:
				case decimalType:
				case doubleType:
				case floatType:
				case integerType:
				case longType:
				case stringType:
				case enumType:
					printScalarValue((ScalarType) type, object);
					break;
				case entityType:
					printEntity((EntityType<?>) type, (GenericEntity) object);
					break;
				case listType:
					printList((List<?>) object);
					break;
				case setType:
					printSet((Set<?>) object);
					break;
				case mapType:
					printMap((Map<?, ?>) object);
					break;
				default:
					throw new GenericModelException("Unexpected type: " + type);
			}
		}

		private void printNull() {
			if (writeNulls)
				writer.write("null");
		}

		@Override
		public void printList(List<?> list) {
			printLinearCollection("[", "]", list);
		}

		@Override
		public void printSet(Set<?> set) {
			printLinearCollection("{", "}", set);
		}

		private void printLinearCollection(String leftBracket, String rightBracket, Collection<?> elements) {
			if (elements.isEmpty()) {
				print(leftBracket);
				print(rightBracket);
				return;
			}

			boolean first = true;

			println(leftBracket);
			levelUp();

			for (Object element : elements) {
				if (first)
					first = false;
				else
					println(",");

				print(element);
			}
			println();

			levelDown();
			print(rightBracket);

		}

		@Override
		public void printMap(Map<?, ?> map) {
			if (map.isEmpty()) {
				print("[:]");
				return;
			}

			boolean first = true;

			println("[");
			levelUp();

			for (Entry<?, ?> entry : map.entrySet()) {
				if (first)
					first = false;
				else
					println(",");

				Object key = entry.getKey();
				Object value = entry.getValue();

				print(key);
				print(" -> ");
				print(value);

			}
			println();

			levelDown();
			print("]");
		}

		public void printScalarValue(ScalarType type, Object value) {
			print(type.instanceToString(value));
		}

		private void printEntity(EntityType<?> entityType, GenericEntity entity) {
			if (visited.put(entity, entity) == null)
				printFull(entityType, entity);
			else
				printEntityReference(entityType, entity);

		}

		private void printFull(EntityType<?> entityType, GenericEntity entity) {
			EntityPrinter<GenericEntity> serializer = findFullPrintrFor(entityType);

			if (serializer != null)
				serializer.print(entity, this);
		}

		private EntityPrinter<GenericEntity> findFullPrintrFor(EntityType<?> entityType) {
			return fullPrinters == null ? null : fullPrinters.find(entityType);
		}

		private void printEntityReference(EntityType<?> entityType, GenericEntity entity) {
			EntityPrinter<GenericEntity> serializer = findRefPrinterFor(entityType);

			if (serializer != null)
				serializer.print(entity, this);
		}

		private EntityPrinter<GenericEntity> findRefPrinterFor(EntityType<?> entityType) {
			if (refPrinters == null)
				return null;

			EntityPrinter<GenericEntity> refSerializer = refPrinters.find(entityType);

			if (refSerializer == null && fullPrinters.find(entityType) != null) {
				throw new GenericModelException("Reference serializer not found, but full serializer is configured for: " + entityType);
			}

			return refSerializer;
		}

		// ####################################
		// ## . . . . . . . MISC . . . . . . ##
		// ####################################

		private String prefix = "";
		private boolean usedPrefix = false;

		@Override
		public void levelUp() {
			prefix += "\t";
		}

		@Override
		public void levelDown() {
			prefix = prefix.isEmpty() ? prefix : prefix.substring(1);
		}

		@Override
		public void print(String s) {
			if (usedPrefix) {
				writer.append(s);
			} else {
				writer.append(prefix);
				writer.append(s);
				usedPrefix = true;
			}
		}

		@Override
		public void println(String s) {
			if (usedPrefix) {
				writer.append(s);
				writer.append("\n");
				usedPrefix = false;
			} else {
				writer.append(prefix);
				writer.append(s);
				writer.append("\n");
			}
		}

	}

}
