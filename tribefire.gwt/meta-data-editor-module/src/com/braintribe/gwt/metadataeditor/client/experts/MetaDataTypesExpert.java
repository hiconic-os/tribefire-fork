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
package com.braintribe.gwt.metadataeditor.client.experts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.braintribe.gwt.metadataeditor.client.experts.MetaDataEditorExpert.CallbackEntityType;
import com.braintribe.gwt.metadataeditor.client.experts.MetaDataEditorExpert.CallbackMetaData;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.meta.data.Predicate;
import com.braintribe.model.meta.data.UniversalMetaData;

public class MetaDataTypesExpert {

	@SuppressWarnings({ "deprecation" })
	private Set<EntityType<?>> getSubTypes(Class<? extends GenericEntity> entityClass) {
		Set<EntityType<?>> availableEntityTypes = new HashSet<EntityType<?>>();		
		
		
		EntityType<?> entityType = GMF.getTypeReflection().getEntityType(entityClass);
		//Set<EntityType<?>> types2 = entityType.getSubTypes();
		Set<EntityType<?>> types2 = entityType.getInstantiableSubTypes();  //already recursive subtypes
					
		for ( EntityType<?> entity : types2) {
			if (entity != null) {
				if (!entity.isAbstract()) {
						availableEntityTypes.add(entity);		
				}
				//need call RECURSIVE in all subTypes
				/*
				if (entity instanceof GenericEntity) {
					availableEntityTypes.addAll(getSubTypes((Class<? extends GenericEntity>) entity.getClass()));
				}
				*/
			}
		}		
					
		return availableEntityTypes;	
	}

	protected <T extends GenericEntity> T createEntity(EntityType<T> entityType) {
		T entity = entityType.create();
		return entity;
	}    	
	
	public void provide(Class<? extends GenericEntity> value, CallbackMetaData callback) {
		try {
			List<MetaData> mdList = new ArrayList<MetaData>();
			List<EntityType<?>> mdListEntityType = getMetaDataList(value);
			
			for (EntityType<?> entityType : mdListEntityType) {	
				MetaData metaData = (MetaData) createEntity(entityType);	
				if (metaData != null) {
					mdList.add(metaData);
				}
			}
			
			callback.onSuccess(mdList);				
		} catch (Exception e) {
			callback.onFailure(e);
		}				
	}

	public void provide(Set<Class<? extends GenericEntity>> value, CallbackMetaData callback) {
		try {
			List<MetaData> mdList = new ArrayList<MetaData>();
			List<EntityType<?>> mdListEntityType = getCompleteMetaDataList(value);
			
			for (EntityType<?> entityType : mdListEntityType) {	
				MetaData metaData = (MetaData) createEntity(entityType);	
				if (metaData != null) {
					mdList.add(metaData);
				}
			}
			
			callback.onSuccess(mdList);				
		} catch (Exception e) {
			callback.onFailure(e);
		}				
	}	
	
	public void provide(Class<? extends GenericEntity> value, CallbackEntityType callback) {
		try {
			List<EntityType<?>> mdListEntityType = getMetaDataList(value);
			callback.onSuccess(mdListEntityType);			
			
		} catch (Exception e) {
			callback.onFailure(e);
		}
	}

	private List<EntityType<?>> getMetaDataList(Class<? extends GenericEntity> value) {
		List<EntityType<?>> mdListEntityType = new ArrayList<EntityType<?>>();
		if (value != null) {
			if (value.equals(Predicate.class))
				mdListEntityType.addAll(getPredicateMetaDataList());
			else if (value.equals(UniversalMetaData.class))
				mdListEntityType.addAll(getUniversalMetaDataList());
			else 
				mdListEntityType.addAll(getSubTypes(value));
			
		}
		return mdListEntityType;
	}
	
	public void provide(Set<Class<? extends GenericEntity>> value, CallbackEntityType callback) {
		try {
			List<EntityType<?>> mdListEntityType = getCompleteMetaDataList(value);
			callback.onSuccess(mdListEntityType);			
			
		} catch (Exception e) {
			callback.onFailure(e);
		}
	}

	private List<EntityType<?>> getCompleteMetaDataList(Set<Class<? extends GenericEntity>> value) {
		List<EntityType<?>> mdListEntityType = new ArrayList<EntityType<?>>();
		for (Class<? extends GenericEntity> classEntity : value) {
			for (EntityType<?> entityType : getMetaDataList(classEntity)) {
				if (!mdListEntityType.contains(entityType))
					mdListEntityType.add(entityType);
			}			
		}
		return mdListEntityType;
	}

	
	@SuppressWarnings("deprecation")
	private static Set<EntityType<?>> getPredicateMetaDataList() {
		return Predicate.T.getInstantiableSubTypes();
	}

	@SuppressWarnings("deprecation")
	private static Set<EntityType<?>> getUniversalMetaDataList() {
		return UniversalMetaData.T.getInstantiableSubTypes();
	}
	
	
}
