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
package tribefire.extension.audit.processing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.AtomicManipulation;
import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.manipulation.PropertyManipulation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.generic.value.EntityReferenceType;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.PropertyQuery;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.record.ListRecord;
import com.braintribe.utils.CollectionTools;

import tribefire.extension.audit.model.ManipulationRecord;

public class TypeUsageInfoIndex {
	private Map<String, TypeUsageInfo> typeUsages = new HashMap<>();
	private PersistenceGmSession session;
	private CmdResolver cmdResolver;
	
	public TypeUsageInfoIndex(PersistenceGmSession session) {
		super();
		this.session = session;
		this.cmdResolver = session.getModelAccessory().getCmdResolver();
	}
	
	public TypeUsageInfo getTypeUsageInfo(String typeSignature) {
		return typeUsages.get(typeSignature);
	}

	public void register(AtomicManipulation manipulation) {
		EntityReference reference = (EntityReference)manipulation.manipulatedEntity();
		String typeSignature = reference.getTypeSignature();
		
		// don't track new created manipulation record changes
		if (typeSignature.equals(ManipulationRecord.T.getTypeSignature()) && reference.referenceType() == EntityReferenceType.preliminary)
			return;
		
		TypeUsageInfo typeUsageInfo = acquireType(typeSignature);
		
		boolean incrementalCollectionManipulation = false;
		
		switch (manipulation.manipulationType()) {
		// lifecycle manipulations
		case INSTANTIATION:
		case DELETE:
			typeUsageInfo.manageLifeCycleTracked(reference);
			break;
		// property manipulations
		case ADD:
		case CLEAR_COLLECTION:
		case REMOVE:
			incrementalCollectionManipulation = true;
			// FALL-THROUGH
		case CHANGE_VALUE:
			PropertyManipulation propertyManipulation = (PropertyManipulation)manipulation;
			String propertyName = propertyManipulation.getOwner().getPropertyName();
			typeUsageInfo.manageTrackedProperty(propertyName, reference, incrementalCollectionManipulation);
			break;
		default:
			break;
		}
	}
	
	public void onRegistrationCompleted() {
		for (TypeUsageInfo info: typeUsages.values()) {
			String typeSignature = info.getTypeSignature();
			
			Set<PersistentEntityReference> preserveReferences = info.getPreserveReferences();
			
			if (preserveReferences != null) {
				EntityType<?> entityType = EntityTypes.get(typeSignature);

				List<Set<PersistentEntityReference>> bulks = CollectionTools.split(preserveReferences, 100, HashSet::new);
				
				List<String> preserveProperties = info.getAccumulatedPreservedNonCollectionProperties();
				int propertyNum = preserveProperties.size();
				
				for (Set<PersistentEntityReference> bulk: bulks) {
					SelectQuery query = AuditSelectQueries.queryEntities(typeSignature, bulk, preserveProperties);
					
					List<ListRecord> results = session.queryDetached().select(query).list();
					
					for (ListRecord result: results) {
						List<Object> values = result.getValues();
						
						Object id = values.get(0);
						String partition = (String)values.get(1);
						
						GenericEntity entity = info.acquireExistingEntity(id, partition);
						
						for (int i = 0; i < propertyNum; i++) {
							Object value = values.get(i + 2);
							String propertyName = preserveProperties.get(i);
							entityType.getProperty(propertyName).set(entity, value);
						}
						
					}
				}
			}
			
			List<EntityProperty> collectionProperties = info.getPreservedCollectionProperties();
			
			if (collectionProperties != null) {
				for (EntityProperty entityProperty : collectionProperties) {
					
					PropertyQuery propertyQuery = PropertyQuery.create(entityProperty);
					Object value = session.query().property(propertyQuery).value();
					
					EntityReference reference = entityProperty.getReference();
					GenericEntity entity = info.acquireExistingEntity(reference.getRefId(), reference.getRefPartition());
					
					Property property = info.getEntityType().getProperty(entityProperty.getPropertyName());
					property.set(entity, value);
				}
			}
			
		}
	}
	
	private TypeUsageInfo acquireType(String typeSignature) {
		return typeUsages.computeIfAbsent(typeSignature, s -> new TypeUsageInfo(cmdResolver, typeSignature));
	}
}