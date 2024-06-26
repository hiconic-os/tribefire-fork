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
package com.braintribe.model.access.smart.manipulation;

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.List;
import java.util.Map;

import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityMapping;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityPropertyMapping;

/**
 * 
 * @author peter.gazdik
 */
public class SmartManipulationContextVariables {

	// context variables
	public EntityProperty currentSmartOwner;
	public EntityProperty currentDelegateOwner; // only for induced manipulations

	public EntityReference currentSmartReference;
	public EntityReference currentDelegateReference;
	public GmEntityType currentSmartType;
	public EntityMapping currentEntityMapping;
	public IncrementalAccess currentAccess;

	// this whole block only for direct (not-induced) manipulations
	public GmProperty currentSmartGmProperty; // only if property manipulation
	public GmEntityType currentSmartReferencedEntityType; // only if property is of EntityType or a collection referencing entities
	public boolean currentSmartPropertyReferencesUnmappedType;

	public EntityPropertyMapping currentEpm; // only for induced manipulations

	public final Map<IncrementalAccess, List<Manipulation>> delegateManipulations = newMap();
	public final Map<IncrementalAccess, ManipulationResponse> delegateResponses = newMap();

	public final List<Manipulation> smartInducedManipulations = newList();

}
