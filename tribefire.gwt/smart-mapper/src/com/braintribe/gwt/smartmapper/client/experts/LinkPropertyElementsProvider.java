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

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.action.client.EntitiesProviderResult;
import com.braintribe.gwt.gmview.client.parse.ParserArgument;
import com.braintribe.gwt.smartmapper.client.util.TypeAndPropertyInfo;
import com.braintribe.model.accessdeployment.smart.meta.LinkPropertyAssignment;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.data.QualifiedProperty;
import com.braintribe.model.meta.info.GmPropertyInfo;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;


public class LinkPropertyElementsProvider extends AbstractMappingElementsProvider{
	
	public enum Mode {key, otherKey, linkKey, linkOtherKey, linkEntityType, linkIndex}
	
	protected Mode mode = Mode.key;
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public LinkPropertyElementsProvider(MappingElementKind mappingElementKind, Mode mode) {
		super(mappingElementKind);
		this.mode = mode;
	}

	@Override
	public Future<EntitiesProviderResult> apply(ParserArgument index) throws RuntimeException {
		GmPropertyInfo parentProperty = propertyAssignmentContext.parentProperty;
		PersistenceGmSession session  = propertyAssignmentContext.session;
		LinkPropertyAssignment lpa = (LinkPropertyAssignment) propertyAssignmentContext.parentEntity;
		GmEntityType entityType = null;
		
		switch (mode) {
		case key:
			QualifiedProperty qa = lpa.getKey();
			entityType = qa != null && qa.getEntityType() != null ? qa.getEntityType() 
					: (GmEntityType) propertyAssignmentContext.mappedToEntityType;
			break;
		case otherKey:
			qa = lpa.getOtherKey();
			entityType = qa != null && qa.getEntityType() != null ? qa.getEntityType() 
					: TypeAndPropertyInfo.getMappedEntityTypeOfProperty(propertyAssignmentContext.smartMapper.cmdResolver, TypeAndPropertyInfo.getPropertyType(parentProperty));
			break;
		case linkKey:
			entityType = lpa.getLinkEntityType();
			break;
		case linkOtherKey:
			entityType = lpa.getLinkEntityType();
			break;
		case linkIndex:
			entityType = lpa.getLinkEntityType();
			break;
		default:
			break;
		}
		
		switch(mappingElementKind){
		case properties:
			return handleProperties(entityType, index);
		case types:
			return handleTypes(session, entityType, index);
		default:
			return new Future<EntitiesProviderResult>();
		}
	}
	
	@Override
	protected Future<EntitiesProviderResult> handleTypes(PersistenceGmSession session, GmEntityType entityType, ParserArgument index){
		Future<EntitiesProviderResult> future = new Future<>();
		String typeSignature = index.getValue();
		
		if(entityType != null){
			session.query().entities(EntityQueryBuilder.from(GmEntityType.class)
					.where().value(entityType.reference()).in().property("superTypes")
					.tc().negation().joker().done())
			.result(AbstractMappingElementsProvider.filterTypes(future, entityType, typeSignature));
			
			return future;
		}
		else return new Future<EntitiesProviderResult>();
	}

}
