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
package com.braintribe.model.processing.misc;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;

/**
 * GenericEntity Console Printer
 */
public class EntityPrinter {

	private Set<NamableEntity> visited = new HashSet<>();
	private int level;
	private boolean prefixApplied = false;

	public static void print(NamableEntity entity) {
		new EntityPrinter().printHelper(entity);
	}

	private void printHelper(NamableEntity entity) {
		if (visited.contains(entity)) {
			printVisited(entity);
			return;
		}

		visited.add(entity);

		printFirstVisit(entity);
	}

	private void printVisited(NamableEntity entity) {
		EntityType<GenericEntity> et = entityType(entity);
		println(et.getShortName() + "(" + number(entity) + ") <-");
	}

	private void printFirstVisit(NamableEntity entity) {
		EntityType<GenericEntity> et = entityType(entity);
		println(et.getShortName() + "(" + number(entity) + ") [");
		level++;

		for (Property p : et.getProperties()) {
			print(p.getName() + ": ");

			AbsenceInformation ai = p.getAbsenceInformation(entity);
			if (ai != null) {
				println("---");
				continue;
			}

			GenericModelType type = p.getType();
			Object value = p.get(entity);

			printValue(value, type);

		}

		level--;
		println("]");
	}

	@SuppressWarnings("incomplete-switch")
	private void printValue(Object value, GenericModelType type) {
		if (value == null) {
			println("null");
			return;
		}

		switch (type.getTypeCode()) {
			case booleanType:
			case dateType:
			case decimalType:
			case doubleType:
			case enumType:
			case floatType:
			case integerType:
			case longType:
			case stringType:
				println(value);
				return;

			case entityType:
				printReferencedEntity((GenericEntity) value, (EntityType<?>) type);
				return;
			case listType:
				printList((List<?>) value, ((CollectionType) type).getParameterization()[0]);
				return;
			case mapType:
				printMap((Map<?, ?>) value, ((CollectionType) type).getParameterization()[0],
						((CollectionType) type).getParameterization()[1]);
				return;
			case setType:
				printSet((Set<?>) value, ((CollectionType) type).getParameterization()[0]);
				return;
		}
	}

	private void printReferencedEntity(GenericEntity entity, EntityType<?> type) {
		if (NamableEntity.class.isAssignableFrom(type.getJavaType())) {
			printHelper((NamableEntity) entity);
		} else {
			println(entity);
		}
	}

	private void printMap(Map<?, ?> value, GenericModelType keyType, GenericModelType valueType) {
		println("MAP - TODO");
	}

	private void printList(List<?> value, GenericModelType elementType) {
		printCollection(value, elementType);
	}

	private void printSet(Set<?> value, GenericModelType elementType) {
		printCollection(value, elementType);
	}

	private void printCollection(Collection<?> value, GenericModelType elementType) {
		println("[");

		level++;
		for (Object o : value) {
			printValue(o, elementType);
		}
		level--;
	}

	private static EntityType<GenericEntity> entityType(NamableEntity entity) {
		return GMF.getTypeReflection().getEntityType(entity);
	}

	private void println(Object s) {
		print(s + "\n");
		prefixApplied = false;
	}

	private void print(Object s) {
		if (!prefixApplied) {
			for (int i = 0; i < level; i++) {
				System.out.print("    ");
			}
			prefixApplied = true;
		}

		System.out.print(s);
	}

	private static int number(NamableEntity entity) {
		return System.identityHashCode(entity);
	}

}
