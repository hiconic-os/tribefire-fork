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
package tribefire.extension.cache.util;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.reflection.CloningContext;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.StandardCloningContext;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class CloningContextUtil {

	/**
	 * Create a {@link StandardCloningContext} that clones in another session. ATTENTION: this method is also cloning id
	 * and globalId!
	 * 
	 * @param targetSession
	 *            session to which to be cloned - target session
	 * @return {@link CloningContext}
	 */
	public static CloningContext createStandardCloningContextIntoSession(PersistenceGmSession targetSession) {
		CloningContext cloningContext = new StandardCloningContext() {
			@Override
			public GenericEntity supplyRawClone(EntityType<? extends GenericEntity> entityType, GenericEntity instanceToBeCloned) {
				return targetSession.create(entityType);
			}
		};
		return cloningContext;
	}

	/**
	 * Create a {@link StandardCloningContext} that clones into a transient entity. ATTENTION: this method is also
	 * cloning id and globalId!
	 * 
	 * @return {@link CloningContext}
	 */
	public static CloningContext createStandardCloningContextIntoSession() {
		CloningContext cloningContext = new StandardCloningContext() {
			@Override
			public GenericEntity supplyRawClone(EntityType<? extends GenericEntity> entityType, GenericEntity instanceToBeCloned) {
				return entityType.create();
			}
		};
		return cloningContext;
	}

	/**
	 * Create a {@link StandardCloningContext} that clones in another session.
	 * 
	 * @param targetSession
	 *            session to which to be cloned - target session
	 * @return {@link CloningContext}
	 */
	public static CloningContext createStandardCloningContextIntoSessionWithoutIdAndGlobalId(PersistenceGmSession targetSession) {
		CloningContext cloningContext = new StandardCloningContext() {

			@Override
			public boolean canTransferPropertyValue(EntityType<? extends GenericEntity> entityType, Property property,
					GenericEntity instanceToBeCloned, GenericEntity clonedInstance, AbsenceInformation sourceAbsenceInformation) {
				// covers id, partition and gloabalId
				return !property.isIdentifying() && !property.isGlobalId();
			}

			@Override
			public GenericEntity supplyRawClone(EntityType<? extends GenericEntity> entityType, GenericEntity instanceToBeCloned) {
				return targetSession.create(entityType);
			}
		};
		return cloningContext;
	}

	/**
	 * Create a {@link StandardCloningContext} that clones into a transient entitiy.
	 * 
	 * @return {@link CloningContext}
	 */
	public static CloningContext createStandardCloningContextIntoSessionWithoutIdAndGlobalId() {
		CloningContext cloningContext = new StandardCloningContext() {

			@Override
			public boolean canTransferPropertyValue(EntityType<? extends GenericEntity> entityType, Property property,
					GenericEntity instanceToBeCloned, GenericEntity clonedInstance, AbsenceInformation sourceAbsenceInformation) {
				// covers id, partition and gloabalId
				return !property.isIdentifying() && !property.isGlobalId();
			}

			@Override
			public GenericEntity supplyRawClone(EntityType<? extends GenericEntity> entityType, GenericEntity instanceToBeCloned) {
				return entityType.create();
			}
		};
		return cloningContext;
	}
}
