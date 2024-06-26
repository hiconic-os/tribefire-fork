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
package com.braintribe.model.processing.elasticsearch.indexing;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.StandardCloningContext;
import com.braintribe.model.generic.reflection.TypeCode;
import com.braintribe.model.processing.elasticsearch.util.ElasticsearchUtils;

public class FilteringCloningContext extends StandardCloningContext {

	private List<Property> properties;

	public FilteringCloningContext(List<Property> properties) {
		this.properties = properties;
	}

	@Override
	public boolean canTransferPropertyValue(EntityType<? extends GenericEntity> entityType, Property property, GenericEntity instanceToBeCloned,
			GenericEntity clonedInstance, AbsenceInformation sourceAbsenceInformation) {

		boolean result = false;

		if (property.isIdentifier()) {
			result = true;

		} else if (!properties.contains(property)) {
			result = false;

		} else {
			GenericModelType propertyType = property.getType();
			TypeCode typeCode = propertyType.getTypeCode();

			switch (typeCode) {
				case entityType:
					result = false;
					break;
				case listType:
				case setType:
				case mapType:
					GenericModelType elementType = ((CollectionType) propertyType).getCollectionElementType();
					result = ElasticsearchUtils.isScalarType(elementType);
					break;

				default:
					result = true;
					break;

			}

		}

		return result;

	}

}
