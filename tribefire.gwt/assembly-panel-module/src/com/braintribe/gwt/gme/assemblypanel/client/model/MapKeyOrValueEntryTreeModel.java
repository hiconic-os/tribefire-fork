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
package com.braintribe.gwt.gme.assemblypanel.client.model;

import java.util.function.Function;

/**
 * This model represents a Key or a Value for a Map of type <X,Y>.
 * @author michel.docouto
 *
 */
public class MapKeyOrValueEntryTreeModel extends DelegatingTreeModel {
	
	private boolean isKey;
	
	public MapKeyOrValueEntryTreeModel(ObjectAndType objectAndType, Function<ObjectAndType, ? extends AbstractGenericTreeModel> modelFactory,
			boolean isKey) {
		delegate = modelFactory.apply(objectAndType);
		this.isKey = isKey;
	}
	
	@Override
	public <X> X get(String property) {
		return (X) delegate.get(property);
	}
	
	public boolean isKey() {
		return isKey;
	}

}
