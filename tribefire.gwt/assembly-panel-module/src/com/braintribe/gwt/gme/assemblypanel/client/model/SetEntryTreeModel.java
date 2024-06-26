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

public class SetEntryTreeModel extends DelegatingTreeModel {
	
	private ListOrSetEntry setEntry;
	
	public SetEntryTreeModel(ObjectAndType objectAndType, Function<ObjectAndType, ? extends AbstractGenericTreeModel> modelFactory) {
		ObjectAndType subObjectAndType = new ObjectAndType();
		setEntry = (ListOrSetEntry)objectAndType.getObject();
		subObjectAndType.setObject(setEntry.getObject());
		subObjectAndType.setType(objectAndType.getType());
		subObjectAndType.setDepth(objectAndType.getDepth());
		delegate = modelFactory.apply(subObjectAndType);
	}
	
	@Override
	public <X> X get(String property) {
		return (X)delegate.get(property);
	}
	
	public ListOrSetEntry getSetEntry() {
		return setEntry;
	}

}
