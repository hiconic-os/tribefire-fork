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
package com.braintribe.model.processing.session.api.common;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;
import com.braintribe.model.processing.meta.cmd.empty.EmptyModelMdResolver;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.processing.session.api.managed.ModelAccessory;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public interface GmSessions {
	
	static ModelMdResolver getMetaData(GenericEntity entity) {
		GmSession session = null;
		if (entity != null)
			session = entity.session();
		
		if (session instanceof ManagedGmSession) {
			ModelAccessory modelAccessory = ((ManagedGmSession) session).getModelAccessory();
			if (modelAccessory != null && modelAccessory.getModel() != null)
				return modelAccessory.getMetaData();
		}
		
		return EmptyModelMdResolver.INSTANCE;
	}
	
	/**
	 * Clones an entity with all its referenced entities into the target session.
	 * 
	 * @param source Entity to be cloned
	 * @param session target session
	 * @return cloned entity in target session
	 */
	static <T extends GenericEntity> T cloneIntoSession(T source, PersistenceGmSession session) {
		return cloneIntoSession(source, session, false);
	}

	/**
	 * Clones an entity with all its referenced entities into the target session.
	 * 
	 * @param source Entity to be cloned
	 * @param session target session
	 * @param transferIdProperties whether to transfer id, partition and globalId properties as well to the cloned entities
	 * @return cloned entity in target session
	 */
	static <T extends GenericEntity> T cloneIntoSession(T source, PersistenceGmSession session, boolean transferIdProperties) {
		return source.clone(new InSessionCloningContext(session, transferIdProperties));
	}
	
}
