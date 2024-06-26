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
package tribefire.extension.process.imp;
// ============================================================================
// BRAINTRIBE TECHNOLOGY GMBH - www.braintribe.com
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2018 - All Rights Reserved
// It is strictly forbidden to copy, modify, distribute or use this code without written permission
// To this file the Braintribe License Agreement applies.
// ============================================================================



import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.product.rat.imp.AbstractImpCave;

import tribefire.extension.process.model.deployment.ProcessDefinition;

/**
 * An {@link AbstractImpCave} specialized in {@link ProcessDefinition}
 */
public class ProcessDefinitionImpCave extends AbstractImpCave<ProcessDefinition, ProcessDefinitionImp> {

	public ProcessDefinitionImpCave(PersistenceGmSession session) {
		super(session, "globalId", ProcessDefinition.T);
	}

	/**
	 * @param name
	 *            the name for the ProcessDefinition to be created
	 */
	public ProcessDefinitionImp create(String name) {
		ProcessDefinition processDefinition = session().create(ProcessDefinition.T);
		processDefinition.setName(name);

		return buildImp(processDefinition);
	}

	@Override
	protected ProcessDefinitionImp buildImp(ProcessDefinition instance) {
		return new ProcessDefinitionImp(session(), instance);
	}

}
