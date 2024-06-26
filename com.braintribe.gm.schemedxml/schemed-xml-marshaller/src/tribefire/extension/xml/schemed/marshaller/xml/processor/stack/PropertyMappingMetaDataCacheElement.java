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
package tribefire.extension.xml.schemed.marshaller.xml.processor.stack;

import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.TypeCode;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.GmType;

import tribefire.extension.xml.schemed.mapping.metadata.PropertyMappingMetaData;


public class PropertyMappingMetaDataCacheElement {
	
	public String fixedValue;
	public String apparentXsdType;
	public String actualXsdType;
	public boolean isMultiple;
	public boolean isUndefined;
	public GmType gmPropertyType;
	public String gmPropertyName;
	public GenericModelType propertyType;
	public GenericModelType elementType;
	public boolean isBacklinkProperty;
	

	public PropertyMappingMetaDataCacheElement(PropertyMappingMetaData propertyMappingMetaData) {
		GmProperty gmProperty = propertyMappingMetaData.getProperty();
		this.gmPropertyType = gmProperty.getType();
		this.gmPropertyName = gmProperty.getName();
		this.fixedValue = propertyMappingMetaData.getFixedValue();
		this.apparentXsdType = propertyMappingMetaData.getApparentXsdType();
		this.actualXsdType = propertyMappingMetaData.getActualXsdType();
		this.isMultiple = Boolean.TRUE.equals(propertyMappingMetaData.getIsMultiple());
		this.propertyType = gmPropertyType.reflectionType();
		this.isUndefined = propertyMappingMetaData.getIsUndefined();
		this.isBacklinkProperty = propertyMappingMetaData.getIsBacklinkProperty();
		
		TypeCode typeCode = this.propertyType.getTypeCode();
		switch (typeCode) {
			case setType:
			case listType:
				this.elementType = ((CollectionType) this.propertyType).getCollectionElementType();
				break;
			default:
				break;
		}
	}
	
}
