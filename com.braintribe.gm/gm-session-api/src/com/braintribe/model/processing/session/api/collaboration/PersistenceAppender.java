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
package com.braintribe.model.processing.session.api.collaboration;

import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.session.InputStreamProvider;
import com.braintribe.model.processing.session.api.managed.EntityManager;
import com.braintribe.model.processing.session.api.managed.ManipulationMode;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.smoodstorage.stages.PersistenceStage;

/**
 * Component of {@link CollaborativeAccess} which is responsible for persisting applied manipulations.
 */
public interface PersistenceAppender {

	PersistenceStage getPersistenceStage();

	/**
	 * Appends given {@link Manipulation} to the underlying persistence.
	 * 
	 * @return array of {@link AppendedSnippet}s - one for data and for model. This object provides a stream with data which correspond to the
	 *         persisted manipulation, and when wrapped into a {@link Resource} is compatible with the {@link #append(Resource[], EntityManager)}
	 *         method.
	 */
	AppendedSnippet[] append(Manipulation manipulation, ManipulationMode mode);

	/**
	 * {@link Resource} based alternative to {@link #append(Manipulation, ManipulationMode)}. See also the description of what this other method
	 * returns.
	 */
	void append(Resource[] gmmlResources, EntityManager entityManager);

	static interface AppendedSnippet extends InputStreamProvider {

		long sizeInBytes();

	}

}
