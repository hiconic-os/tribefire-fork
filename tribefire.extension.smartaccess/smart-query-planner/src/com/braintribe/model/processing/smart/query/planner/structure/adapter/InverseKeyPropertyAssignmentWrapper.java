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
package com.braintribe.model.processing.smart.query.planner.structure.adapter;

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.accessdeployment.smart.meta.ConvertibleQualifiedProperty;
import com.braintribe.model.accessdeployment.smart.meta.InverseKeyPropertyAssignment;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.data.QualifiedProperty;
import com.braintribe.model.processing.smart.query.planner.structure.ModelExpert;

/**
 * 
 */

public class InverseKeyPropertyAssignmentWrapper implements DqjDescriptor {

	private final List<String> joinedEntityDelegatePropertyNames = newList();
	private final Map<String, String> map = newMap();
	private final Map<String, ConversionWrapper> conversionMap = newMap();
	private final boolean forceExternalJoin;

	public InverseKeyPropertyAssignmentWrapper(GmEntityType smartOwnerType, IncrementalAccess ownerAccess,
			Collection<InverseKeyPropertyAssignment> ikpas, ModelExpert modelExpert) {

		boolean force = false;
		for (InverseKeyPropertyAssignment ikpa : ikpas) {
			force |= ikpa.getForceExternalJoin();

			QualifiedProperty qualifiedKeyProperty = ikpa.getKeyProperty();
			ConvertibleQualifiedProperty property = ikpa.getProperty();

			String ownerProperty = qualifiedKeyProperty.getProperty().getName();
			String joinedProperty = property.getProperty().getName();

			// FYI: We only do this "ensuring we have delegate property" if IKPA (no KPA). Makes sense, think about it.
			ownerProperty = modelExpert.findDelegatePropertyForKeyProperty(qualifiedKeyProperty.propertyOwner(), ownerAccess, ownerProperty,
					smartOwnerType);

			joinedEntityDelegatePropertyNames.add(joinedProperty);
			map.put(joinedProperty, ownerProperty);

			ConversionWrapper cw = ConversionWrapper.instanceFor(property.getConversion());
			if (cw != null) {
				conversionMap.put(joinedProperty, cw);
			}
		}

		this.forceExternalJoin = force;
	}

	@Override
	public List<String> getJoinedEntityDelegatePropertyNames() {
		return joinedEntityDelegatePropertyNames;
	}

	@Override
	public String getRelationOwnerDelegatePropertyName(String joinedEntityDelegatePropertyName) {
		return map.get(joinedEntityDelegatePropertyName);
	}

	@Override
	public ConversionWrapper getRelationOwnerPropertyConversion(String joinedEntityDelegatePropertyName) {
		return ConversionWrapper.inverseOf(conversionMap.get(joinedEntityDelegatePropertyName));
	}

	@Override
	public ConversionWrapper getJoinedEntityPropertyConversion(String joinedEntityDelegatePropertyName) {
		return conversionMap.get(joinedEntityDelegatePropertyName);
	}

	@Override
	public boolean getForceExternalJoin() {
		return forceExternalJoin;
	}

}
