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
package tribefire.extension.scripting.imp;

import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.UUID;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.product.rat.imp.impl.deployable.BasicDeployableImp;
import com.braintribe.utils.IOTools;

import tribefire.extension.scripting.model.deployment.Script;
import tribefire.extension.scripting.statechange.model.deployment.ScriptedStateChangeProcessor;

public class ScriptedStateChangeProcessorImp<T extends ScriptedStateChangeProcessor> extends BasicDeployableImp<T> {

	public ScriptedStateChangeProcessorImp(PersistenceGmSession session, T instance) {
		super(session, instance);
	}

	public ScriptedStateChangeProcessorImp<T> addScript(EntityType<? extends Script> entityType, String script, Phase phase) {
		logger.info("Creating Script of type [" + entityType.getShortName() + "] \nScript: '" + script + "'");

		Script scriptEntity = session().create(entityType);
		
		Resource r = session().resources().create().name(entityType.getShortName() + "-script-" + UUID.randomUUID().toString()).store(o -> {
			OutputStreamWriter writer = new OutputStreamWriter(o, "UTF-8");
			try(Reader reader = new StringReader(script)) {
				IOTools.pump(reader, writer);
			}
		});
		
		scriptEntity.setSource(r);

		switch(phase) {
			case after:
				instance.setAfterScript(scriptEntity);
				break;
			case before:
				instance.setBeforeScript(scriptEntity);
				break;
			case process:
				instance.setProcessScript(scriptEntity);
				break;
		}

		return this;
	}
}
