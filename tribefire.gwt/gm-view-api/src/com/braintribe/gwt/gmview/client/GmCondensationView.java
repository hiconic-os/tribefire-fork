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
package com.braintribe.gwt.gmview.client;

import com.braintribe.gwt.gmview.actionbar.client.GmViewActionBar;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.prompt.CondensationMode;


public interface GmCondensationView extends GmSelectionSupport, GmSessionHandler, UseCaseHandler {
	
	/**
	 * Checks if local condensation and uncondensation is enabled
	 */
	boolean isLocalCondensationEnabled();
	
	/**
	 * Checks if the last selected entry may be uncondensed.
	 */
	boolean checkUncondenseLocalEnablement();
	
	/**
	 * Gets the condensed property of the last selected entry.
	 */
	String getCondensendProperty();
	
	String getCurrentCondensedProperty(EntityType<?> entityType);
	
	void uncondenseLocal();
	
	void condenseLocal();
	
	void condense(String propertyName, CondensationMode condensationMode, EntityType<?> entityType);
	
	GmViewActionBar getGmViewActionBar();
	
	boolean isUseCondensationActions();
	
	EntityType<GenericEntity> getEntityTypeForProperties();

}
