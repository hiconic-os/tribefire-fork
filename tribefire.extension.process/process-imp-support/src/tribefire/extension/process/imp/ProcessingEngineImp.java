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

import java.util.Set;
import java.util.stream.Collectors;

import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.product.rat.imp.AbstractImp;
import com.braintribe.product.rat.imp.ImpException;
import com.braintribe.product.rat.imp.impl.deployable.BasicDeployableImp;

import tribefire.extension.process.model.deployment.ProcessDefinition;
import tribefire.extension.process.model.deployment.ProcessingEngine;

/**
 * A {@link BasicDeployableImp} specialized in {@link ProcessingEngine}
 */
public class ProcessingEngineImp extends AbstractImp<ProcessingEngine> {
	private final ProcessingEngine processingEngine;

	public ProcessingEngineImp(PersistenceGmSession session, ProcessingEngine processingEngine) {
		super(session, processingEngine);
		this.processingEngine = processingEngine;
	}

	/**
	 * Scans all ProcessDefinitions of this imp's processing engine and returns an imp managing it (when it finds one)
	 *
	 * @param name
	 *            the name of the ProcessDefinition that you want to manage
	 * @throws ImpException
	 *             when no (or multiple) process definitions with provided name belong to this imp's ProcessingEngine
	 */
	public ProcessDefinitionImp definition(String name) {
		//@formatter:off
		Set<ProcessDefinition> foundDefinitions = processingEngine.getProcessDefinitions().stream()
			.filter(d -> d.getName().equals(name))
			.collect(Collectors.toSet());
		//@formatter:on

		if (foundDefinitions.size() != 1) {
			throw new ImpException("Expected to find exactly one process definition with name '" + name + "' but found " + foundDefinitions.size());
		}

		ProcessDefinition foundDefinition = foundDefinitions.iterator().next();

		return new ProcessDefinitionImp(session(), foundDefinition);
	}

	/**
	 * assumes that this imp's ProcessingEngine has exactly one ProcessDefinition and returns an imp managing it
	 *
	 * @throws ImpException
	 *             if no or multiple process definitions belong to this imp's ProcessingEngine
	 */
	public ProcessDefinitionImp definition() {
		Set<ProcessDefinition> foundDefinitions = processingEngine.getProcessDefinitions();

		if (foundDefinitions.size() != 1) {
			throw new ImpException("Expected to find exactly one process definition but found " + foundDefinitions.size());
		}

		ProcessDefinition foundDefinition = foundDefinitions.iterator().next();

		return new ProcessDefinitionImp(session(), foundDefinition);

	}

	public ProcessingEngineImp addDefinition(ProcessDefinition definition) {
		instance.getProcessDefinitions().add(definition);

		return this;
	}
}
