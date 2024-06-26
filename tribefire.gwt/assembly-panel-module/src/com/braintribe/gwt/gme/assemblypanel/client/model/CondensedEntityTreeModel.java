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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.gme.assemblypanel.client.AssemblyPanel;
import com.braintribe.gwt.gmview.util.client.GMEUtil;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.session.api.common.GmSessions;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

public class CondensedEntityTreeModel extends CollectionTreeModel implements PropertyEntryModelInterface {
	
	private EntityTreeModel entityTreeModel;
	private PropertyEntryTreeModel propertyEntryTreeModel;
	private CollectionTreeModel collectionTreeModel;
	private PropertyEntry propertyEntry;
	private PropertyEntryTreeModel propertyEntryTreeModelInParent;
	private Property collectionProperty;
	private String collectionPropertyName;
	private String useCase;
	
	public CondensedEntityTreeModel(PropertyEntryTreeModel propertyEntryTreeModelInParent, EntityTreeModel entityTreeModel,
			Property collectionProperty, String useCase, int depth) {
		this.propertyEntryTreeModelInParent = propertyEntryTreeModelInParent;
		this.collectionProperty = collectionProperty;
		this.collectionPropertyName = collectionProperty != null ? collectionProperty.getName() : null;
		this.useCase = useCase;
		parent = entityTreeModel.getParent();
		this.entityTreeModel = entityTreeModel;
		setModelObject(entityTreeModel.getModelObject(), depth);
	}
	
	@Override
	public void setModelObject(Object modelObject, int depth) {
		super.setModelObject(modelObject, depth);
		if (entityTreeModel.getModelObject() != modelObject ||  entityTreeModel.getNotCompleted())
			entityTreeModel.setModelObject((GenericEntity) modelObject, depth, collectionPropertyName);
		propertyEntryTreeModel = null;
		propertyEntry = null;
		collectionTreeModel = null;
		if (entityTreeModel.getChildren() != null) {
			for (AbstractGenericTreeModel modelData : entityTreeModel.getChildren()) {
				PropertyEntryTreeModel propertyEntryTreeModel = (PropertyEntryTreeModel) modelData;
				if (propertyEntryTreeModel.getPropertyEntry().getPropertyName().equals(collectionPropertyName)) {
					this.propertyEntryTreeModel = propertyEntryTreeModel;
					propertyEntry = propertyEntryTreeModel.getPropertyEntry();
					collectionTreeModel = (CollectionTreeModel) propertyEntryTreeModel.getDelegate();
					collectionTreeModel.setParent(this);
					break;
				}
			}
		}
		
		if (collectionTreeModel != null)
			setCollectionType(collectionTreeModel.getCollectionType());
		else {
			EntityType<GenericEntity> entityType = entityTreeModel.getElementType();
			GenericEntity entity = (GenericEntity) entityTreeModel.getModelObject();
			propertyEntry = new PropertyEntry(entity, entityType, collectionPropertyName, GMEUtil.isPropertyAbsent(entity, collectionProperty),
					EntityTreeModel.isMapAsList(entity, entityType, collectionPropertyName, GmSessions.getMetaData(entity), useCase),
					EntityTreeModel.getMaxSize(entity, entityType, collectionProperty, GmSessions.getMetaData(entity), useCase),
					collectionProperty, collectionProperty.getType(), 0, collectionProperty.getType().isBase());
		}
	}
	
	public PropertyEntryTreeModel getPropertyEntryTreeModel() {
		return propertyEntryTreeModel;
	}
	
	public PropertyEntryTreeModel getPropertyEntryTreeModelInParent() {
		return propertyEntryTreeModelInParent;
	}
	
	@Override
	public PropertyEntry getPropertyEntry() {
		return propertyEntry;
	}
	
	@Override
	public void setProperty(Property property) {
		super.setProperty(property);
		entityTreeModel.setProperty(property);
	}
	
	@Override
	public AbstractGenericTreeModel getPropertyDelegate() {
		return collectionTreeModel;
	}
	
	@Override
	public List<AbstractGenericTreeModel> getChildren() {
		if (getPropertyDelegate() != null)
			return getPropertyDelegate().getChildren();
		
		return Collections.emptyList();
	}
	
	@Override
	public int getChildCount() {
		return getPropertyDelegate() != null ? getPropertyDelegate().getChildCount() : 0;
	}
	
	@Override
	public AbstractGenericTreeModel getChild(int index) {
		return getPropertyDelegate().getChild(index);
	}
	
	@Override
	public AbstractGenericTreeModel getDelegate() {
		return this;
	}

	@Override
	public EntityType<GenericEntity> getElementType() {
		return entityTreeModel.getElementType();
	}
	
	@Override
	public EntityTreeModel getEntityTreeModel() {
		return entityTreeModel;
	}
	
	@Override
	public void insertNewItems(List<Pair<Object, Object>> itemsToInsert, AbstractGenericTreeModel parentModel, AssemblyPanel assemblyPanel,
			AbstractGenericTreeModel triggerModel, boolean addToRootInCaseParentNotInTree) {
		this.collectionTreeModel.insertNewItems(itemsToInsert, parentModel, assemblyPanel, triggerModel, addToRootInCaseParentNotInTree);
	}
	
	@Override
	public void removeItems(Set<Object> itemsKeyToRemove, AbstractGenericTreeModel parentModel, TreeGrid<AbstractGenericTreeModel> treeGrid,
			AbstractGenericTreeModel triggerModel) {
		this.collectionTreeModel.removeItems(itemsKeyToRemove, parentModel, treeGrid, triggerModel);
	}
	
	@Override
	public void replaceItems(List<Pair<Object, Object>> itemsToReplace, AbstractGenericTreeModel parentModel, AssemblyPanel assemblyPanel,
			AbstractGenericTreeModel triggerModel) {
		this.collectionTreeModel.replaceItems(itemsToReplace, parentModel, assemblyPanel, triggerModel);
	}

	@Override
	public void clearItems() {
		this.collectionTreeModel.clearItems();
	}
	
	@Override
	public boolean refersTo(Object object) {
		if (collectionTreeModel != null)
			return entityTreeModel.refersTo(object) || collectionTreeModel.refersTo(object);
		
		return entityTreeModel.refersTo(object);
	}
	
	@Override
	public String getPropertyName() {
		return collectionTreeModel != null ? collectionTreeModel.getPropertyName() : (property != null ? property.getName() : null);
	}
	
	public String getDefaultPropertyName() {
		return property != null ? property.getName() : null;
	}
	
	public Property getDefaultProperty() {
		return property;
	}
	
	@Override
	public Object getCollectionObject() {
		return collectionTreeModel != null ? collectionTreeModel.getCollectionObject() : null;
	}
	
	@Override
	public void add(AbstractGenericTreeModel child) {
		getPropertyDelegate().add(child);
	}

	@Override
	public int indexOf(AbstractGenericTreeModel child) {
		return getPropertyDelegate().indexOf(child);
	}

	@Override
	public void insert(AbstractGenericTreeModel child, int index) {
		getPropertyDelegate().insert(child, index);
	}

	@Override
	public boolean remove(AbstractGenericTreeModel child) {
		return getPropertyDelegate().remove(child);
	}

	@Override
	public void clear() {
		getPropertyDelegate().clear();
	}
	
	public Property getCollectionProperty() {
		return collectionProperty;
	}

}
