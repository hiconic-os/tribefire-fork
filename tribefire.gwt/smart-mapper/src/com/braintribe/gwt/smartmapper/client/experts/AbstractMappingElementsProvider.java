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
package com.braintribe.gwt.smartmapper.client.experts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.braintribe.filter.lcd.pattern.CamelCasePatternMatcher;
import com.braintribe.filter.lcd.pattern.SubstringCheckingPatternMatcher;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.action.client.EntitiesProviderResult;
import com.braintribe.gwt.gmview.client.parse.ParserArgument;
import com.braintribe.gwt.smartmapper.client.PropertyAssignmentContext;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.session.api.managed.EntityQueryResultConvenience;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.processing.async.api.AsyncCallback;

public abstract class AbstractMappingElementsProvider implements Function<ParserArgument, Future<EntitiesProviderResult>>{

	public enum MappingElementKind {properties, types}
	
	protected MappingElementKind mappingElementKind;
	protected PropertyAssignmentContext propertyAssignmentContext;
//	protected SmartMapper smartMapper;
	
	CamelCasePatternMatcher ccpm = new CamelCasePatternMatcher();
	SubstringCheckingPatternMatcher scpm = new SubstringCheckingPatternMatcher();
	
	public AbstractMappingElementsProvider(MappingElementKind mappingElementKind) {
		this.mappingElementKind = mappingElementKind;
	}
	
	public void setPropertyAssignmentContext(PropertyAssignmentContext propertyAssignmentContext) {
		this.propertyAssignmentContext = propertyAssignmentContext;
	}
	
//	public void setSmartMapper(SmartMapper smartMapper) {
//		this.smartMapper = smartMapper;
//	}
	
	protected abstract Future<EntitiesProviderResult> handleTypes(PersistenceGmSession session, GmEntityType entityType, ParserArgument index);
	
	protected Future<EntitiesProviderResult> handleProperties(GmEntityType entityType, ParserArgument index){		
		final String propertyName = index.getValue();
		EntitiesProviderResult entitiesProviderResult = new EntitiesProviderResult(new ArrayList<GenericEntity>(), 0, false);
		if(entityType != null){
			if(propertyName != null){
				entitiesProviderResult.getEntities().addAll(AbstractMappingElementsProvider.getProperties(entityType).stream()
						.filter(AbstractMappingElementsProvider.propertyNameFilter(propertyName))
						.collect(Collectors.toList()));
			}else{
				entitiesProviderResult.getEntities().addAll(AbstractMappingElementsProvider.getProperties(entityType));	
			}
		}
		return new Future<EntitiesProviderResult>(entitiesProviderResult);
		/*
		propertyAssignmentContext.session.query().entities(EntityQueryBuilder.from(GmProperty.class)
		.where()
		.property("declaringType")
		.eq(entityType.reference()).tc().negation().joker().done()).result(new AsyncCallback<EntityQueryResultConvenience>() {
			
			@Override
			public void onSuccess(EntityQueryResultConvenience result) {
				EntitiesProviderResult entitiesProviderResult = new EntitiesProviderResult(result.list(), 0, false);
				entitiesProviderResult.getEntities().addAll(entityType.getProperties());
				future.onSuccess(entitiesProviderResult);
			}
			
			@Override
			public void onFailure(Throwable t) {
				future.onFailure(t);
			}
		}); */
		
		//return future;
	}
	
	public static AsyncCallback<EntityQueryResultConvenience> filterTypes(Future<EntitiesProviderResult> future, GmEntityType entityType, String typeSignature){
		return new AsyncCallback<EntityQueryResultConvenience>() {
			@Override
			public void onSuccess(EntityQueryResultConvenience result) {
				List<GenericEntity> entities = new ArrayList<GenericEntity>();
				EntitiesProviderResult entitiesProviderResult = new EntitiesProviderResult(entities, 0, false);
						
				List<GmEntityType> types = new ArrayList<GmEntityType>();
				types.add(entityType);
				types.addAll(new ArrayList<GmEntityType>(result.list()));
				
				if(typeSignature != null){				
					entitiesProviderResult.getEntities().addAll(types.stream()
							.filter(AbstractMappingElementsProvider.typeSignatureFilter(typeSignature))
							.collect(Collectors.toList()));
				}else{
					entitiesProviderResult.getEntities().addAll(types);
				}
				
				future.onSuccess(entitiesProviderResult);
			}
			
			@Override
			public void onFailure(Throwable t) {
				future.onFailure(t);
			}
		};
	}
	
	public static Predicate<GmProperty> propertyNameFilter(String compare){
		return (gmProperty) -> {
			String propertyName = gmProperty.getName();
			return propertyName.toLowerCase().startsWith(compare.toLowerCase()) ||
					propertyName.toLowerCase().endsWith(compare.toLowerCase()) ||
					propertyName.toLowerCase().equalsIgnoreCase(compare);
		};
	}
	
	public static Predicate<GmEntityType> typeSignatureFilter(String compare){
		return (gmEntitytype) -> {
			String typeSig = gmEntitytype.getTypeSignature();
			return typeSig.toLowerCase().startsWith(compare.toLowerCase()) ||
					typeSig.toLowerCase().endsWith(compare.toLowerCase()) ||
					typeSig.toLowerCase().equalsIgnoreCase(compare) ||
					typeSig.toLowerCase().contains(compare.toLowerCase()); 
		};
	}
	
	public static List<GmProperty> getProperties(GmEntityType entityType){
		List<GmProperty> properties = new ArrayList<GmProperty>();
		properties.addAll(entityType.getProperties());
		for(GmEntityType superType : entityType.getSuperTypes()) {
			properties.addAll(getProperties(superType));
		}
		properties.sort(new Comparator<GmProperty>() {
			@Override
			public int compare(GmProperty o1, GmProperty o2) {
				return 0;
			}
		});
		return properties;
	}
	
}
