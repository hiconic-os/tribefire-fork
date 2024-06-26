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

import java.util.List;
import java.util.Set;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.gme.assemblypanel.client.AssemblyPanel;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.GenericModelType;

import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

public abstract class CollectionTreeModel extends AbstractGenericTreeModel {
	protected CollectionType collectionType;
	
	public void setCollectionType(CollectionType collectionType) {
		this.collectionType = collectionType;
	}
	
	public CollectionType getCollectionType() {
		return collectionType;
	}
	
	public abstract void insertNewItems(List<Pair<Object, Object>> itemsToInsert, AbstractGenericTreeModel parentModel, AssemblyPanel assemblyPanel,
			AbstractGenericTreeModel triggerModel, boolean addToRootInCaseParentNotInTree);
	
	public abstract void removeItems(Set<Object> itemsKeyToRemove, AbstractGenericTreeModel parentModel,
			TreeGrid<AbstractGenericTreeModel> treeGrid, AbstractGenericTreeModel triggerModel);
	
	public abstract void replaceItems(List<Pair<Object, Object>> itemsToReplace, AbstractGenericTreeModel parentModel,
			AssemblyPanel assemblyPanel, AbstractGenericTreeModel triggerModel);
	
	public abstract void clearItems();
	
	public Object getCollectionObject() {
		return modelObject;
	}
	
	protected static GenericModelType getCollectionELementType(GenericModelType defaultType, Object elementValue) {
		if (elementValue instanceof GenericEntity)
			return ((GenericEntity) elementValue).entityType();
		
		if (elementValue != null)
			return GMF.getTypeReflection().getType(elementValue);
		
		return defaultType;
	}
	
	@Override
	public boolean isCollectionTreeModel() {
		return true;
	}

}
