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

import com.braintribe.model.generic.reflection.GenericModelType;

/**
 * This model represents the Entries for a Map.
 * @author michel.docouto
 *
 */
public class MapKeyAndValueTreeModel extends AbstractGenericTreeModel {
	
	private MapKeyOrValueEntryTreeModel mapKeyEntryTreeModel;
	private MapKeyOrValueEntryTreeModel mapValueEntryTreeModel;
	private int entryNumber;
	
	public MapKeyAndValueTreeModel(MapKeyOrValueEntryTreeModel mapKeyEntryTreeModel, MapKeyOrValueEntryTreeModel mapValueEntryTreeModel,
			int entryNumber) {
		add(mapKeyEntryTreeModel);
		add(mapValueEntryTreeModel);
		this.mapValueEntryTreeModel = mapValueEntryTreeModel;
		this.mapKeyEntryTreeModel = mapKeyEntryTreeModel;
		this.entryNumber = entryNumber;
	}

	@Override
	public AbstractGenericTreeModel getDelegate() {
		return this;
	}

	@Override
	public <X extends GenericModelType> X getElementType() {
		return mapValueEntryTreeModel.getElementType();
	}
	
	public GenericModelType getKeyElementType() {
		return mapKeyEntryTreeModel.getElementType();
	}
	
	public MapKeyOrValueEntryTreeModel getMapKeyEntryTreeModel() {
		return mapKeyEntryTreeModel;
	}
	
	public MapKeyOrValueEntryTreeModel getMapValueEntryTreeModel() {
		return mapValueEntryTreeModel;
	}
	
	public int getEntryNumber() {
		return entryNumber;
	}
	
	public void setEntryNumber(int entryNumber) {
		this.entryNumber = entryNumber;
	}

}
