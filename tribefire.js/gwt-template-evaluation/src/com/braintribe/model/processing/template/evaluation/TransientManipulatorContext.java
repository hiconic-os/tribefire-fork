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
package com.braintribe.model.processing.template.evaluation;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.processing.manipulator.expert.basic.AbstractManipulatorContext;

/**
 * @author peter.gazdik
 */
public class TransientManipulatorContext extends AbstractManipulatorContext {

	@Override
	public GenericEntity createPreliminaryEntity(GenericEntity entity) {
		return entity;
	}

	@Override
	public void deleteEntityIfPreliminary(GenericEntity entity) {
		// NO OP
	}

	@Override
	protected GenericEntity resolveEntity(EntityReference entityReference) {
		throw new UnsupportedOperationException("Method 'TransientManipulatorContext.resolveEntity' is currently not supported!");
	}

}
