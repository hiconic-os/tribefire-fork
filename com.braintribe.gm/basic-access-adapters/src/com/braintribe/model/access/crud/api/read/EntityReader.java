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
package com.braintribe.model.access.crud.api.read;

import com.braintribe.model.access.crud.api.DataReader;
import com.braintribe.model.generic.GenericEntity;

/**
 * A {@link DataReader} expert that is responsible for providing a single instance of the
 * type he is registered for identified by the id provided through the passed context.
 * 
 * @author gunther.schenk
 */
public interface EntityReader<T extends GenericEntity> extends DataReader<T> {
	
	/**
	 * @return the instance of the requested type identified by the id provided
	 *         through the context. <br/>
	 *         This method can return <code>null</code> in case no instance could be
	 *         found for passed id.
	 */
	T getEntity (EntityReadingContext<T> context);

}
