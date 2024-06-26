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
package tribefire.extension.xml.schemed.marshaller.xml.processor.commons;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.SimpleType;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.meta.GmProperty;

import tribefire.extension.xml.schemed.mapper.api.MapperInfoRegistry;
import tribefire.extension.xml.schemed.mapping.metadata.EnumConstantMappingMetaData;
import tribefire.extension.xml.schemed.marshaller.xml.SchemedXmlMarshallingException;

/**
 * a encoder for {@link SimpleType} and for {@link EnumType}
 * @author pit
 *
 */
public class ValueEncoder {
	private static Logger log = Logger.getLogger(ValueEncoder.class);

	/**
	 * encode a value to the proper string representation 
	 * @param property - the property to the value from (just to be able to throw a proper exception)
	 * @param type - the {@link SimpleType}
	 * @param valueAsObject - the value as an {@link Object}
	 * @param mappedType - the actual XSD type 
	 * @param fixedValue - the fixed value if set
	 * @return - the resulting string
	 * @throws SchemedXmlMarshallingException
	 */
	public static String encode(GmProperty property, SimpleType type, Object valueAsObject, String rawMappedType, String fixedValue) throws SchemedXmlMarshallingException {
		// if the value's null, we just return a possible fixed value (a string, as
		// declared in the mapping meta model, may be null if no fixed value has been
		// declared)
		if (valueAsObject == null) {
			return fixedValue;
		}
		String mappedType = rawMappedType.contains( ":") ? rawMappedType.substring( rawMappedType.indexOf(':')+1) : rawMappedType;		
		String value = null;
		switch (type.getTypeCode()) {
		case dateType:
			try {
				if (mappedType == null || mappedType.equalsIgnoreCase("dateTime")) {
					value = new DateInterpreter().formatDateTime((Date) valueAsObject);
				} else if (mappedType.equalsIgnoreCase("time")) {
					value = new DateInterpreter().formatTime((Date) valueAsObject);
				} else {
					value = new DateInterpreter().formatDate((Date) valueAsObject);
				}
			} catch (Exception e) {
				throw new SchemedXmlMarshallingException(e);
			}
			break;
		default:
			value = type.instanceToString(valueAsObject);
			break;
		}
		if (fixedValue != null) {
			if (value.equalsIgnoreCase(fixedValue) == false) {
				String msg = "property [" + property.getName() + "] of type ["
						+ property.getDeclaringType().getTypeSignature() + "] has a fixed value [" + fixedValue
						+ "], however [" + value + "] is specified";
				log.warn(msg);
			}
		}
		return value;
	}
	
	/**
	 * encode an enum value
	 * @param mappingRegistry - the {@link MapperInfoRegistry}
	 * @param enumType - the property's {@link GmEnumType} 
	 * @param property - the {@link Property}
	 * @param instance - the {@link GenericEntity} 
	 * @return - the encoded value
	 */
	public static String encodeEnumValue(MapperInfoRegistry mappingRegistry, GmEnumType enumType, Property property, GenericEntity instance) {	
		@SuppressWarnings("rawtypes")
		Enum enumValue = property.get(instance);
		// if no value set, don't write it
		if (enumValue == null)
			return null;
		// translate
		Map<String, String> constantValues = new HashMap<String, String>();
		for (GmEnumConstant constant : enumType.getConstants()) {
			
			EnumConstantMappingMetaData metaData = mappingRegistry.getEnumConstantMetaData(constant);
			if (metaData == null) {
				constantValues.put(constant.getName(), constant.getName());
			} else {
				constantValues.put(constant.getName(), metaData.getXsdName());
			}
		}
		String propertyValue = constantValues.get(enumValue.toString());
		return propertyValue;
	}


}
