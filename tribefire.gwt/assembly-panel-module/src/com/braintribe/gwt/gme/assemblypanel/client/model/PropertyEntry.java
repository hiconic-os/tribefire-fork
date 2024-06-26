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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelException;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;

public class PropertyEntry implements AbsentModel {
	
	private EntityType<GenericEntity> entityType;
	private String propertyName;
	private GenericEntity entity;
	private boolean absent;
	private Property property;
	private GenericModelType propertyType;
	private int depth;
	private boolean mapAsList;
	private Integer maxSize;
	private boolean isBasedType;
	
	public PropertyEntry(GenericEntity entity, EntityType<GenericEntity> entityType, String propertyName, boolean absent, boolean mapAsList,
			Integer maxSize, Property property, GenericModelType propertyType, int depth, boolean basedType) {
		this.mapAsList = mapAsList;
		setEntity(entity);
		setEntityType(entityType);
		setPropertyName(propertyName);
		setAbsent(absent);
		setMaxSize(maxSize);
		setProperty(property);
		setPropertyType(propertyType);
		setDepth(depth);
		setBasedType(basedType);
	}
	
	public GenericEntity getEntity() {
		return entity;
	}
	
	public void setEntity(GenericEntity entity) {
		this.entity = entity;
	}
	
	public EntityType<GenericEntity> getEntityType() {
		return entityType;
	}
	
	public void setEntityType(EntityType<GenericEntity> entityType) {
		this.entityType = entityType;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Override
	public boolean isAbsent() {
		return absent;
	}
	
	@Override
	public void setAbsent(boolean absent) {
		this.absent = absent;
	}
	
	public Property getProperty() {
		return property;
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public <V> V getPropertyValue() throws GenericModelException {
		if (property != null)
			return property.get(getEntity());
		return getEntityType().getProperty(getPropertyName()).get(getEntity());
	}
	
	public GenericModelType getPropertyType() {
		return propertyType;
	}
	
	public void setPropertyType(GenericModelType propertyType) {
		this.propertyType = propertyType;
	}
	
	public boolean getMapAsList() {
		return mapAsList;
	}
	
	public boolean isBasedType() {
		return isBasedType;
	}
	
	public void setBasedType(boolean isBasedType) {
		this.isBasedType = isBasedType;
	}
	
	public Integer getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

}
