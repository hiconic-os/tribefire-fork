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
package com.braintribe.model.query;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.pr.criteria.TraversingCriterion;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.query.conditions.Condition;

/**
 * Special type of query which is evaluated as the value of the property in the same way as if one would call the getter
 * (short of a possible {@link Restriction}). So for example when querying a Set property, the returned result is an
 * instance of {@link java.util.Set}.
 */
public interface PropertyQuery extends Query {

	EntityType<PropertyQuery> T = EntityTypes.T(PropertyQuery.class);

	PersistentEntityReference getEntityReference();
	void setEntityReference(PersistentEntityReference entityReference);

	String getPropertyName();
	void setPropertyName(String propertyName);

	default EntityType<?> ownerType() {
		return getEntityReference().valueType();
	}

	default Property property() {
		return ownerType().getProperty(getPropertyName());
	}
	
	
	public static PropertyQuery create(EntityType<?> entityType, Object id, String propertyName) {
		return create(entityType.getTypeSignature(), id, null, propertyName);
	}
	
	public static PropertyQuery create(EntityType<?> entityType, Object id, Property property) {
		return create(entityType.getTypeSignature(), id, property.getName());
	}
	
	public static PropertyQuery create(EntityType<?> entityType, Object id, String partition, String propertyName) {
		return create(entityType.getTypeSignature(), id, partition, propertyName);
	}
	
	public static PropertyQuery create(EntityType<?> entityType, Object id, String partition, Property property) {
		return create(entityType, id, partition, property.getName());
	}
	
	public static PropertyQuery create(String entityTypeSignature, Object id, String propertyName) {
		return create(entityTypeSignature, id, null, propertyName);
	}
	
	public static PropertyQuery create(String entityTypeSignature, Object id, Property property) {
		return create(entityTypeSignature, id, property.getName());
	}
	
	public static PropertyQuery create(String entityTypeSignature, Object id, String partition, Property property) {
		return create(entityTypeSignature, id, partition, property.getName());
	}
	
	public static PropertyQuery create(String entityTypeSignature, Object id, String partition, String propertyName) {
		PersistentEntityReference reference = PersistentEntityReference.T.create();
		reference.setRefPartition(partition);
		reference.setRefId(id);
		reference.setTypeSignature(entityTypeSignature);
		return create(reference, propertyName);
	}
	
	public static PropertyQuery create(GenericEntity instance, String propertyName) {
		return create(instance.globalReference(), propertyName);
	}
	
	public static PropertyQuery create(GenericEntity instance, Property property) {
		return create(instance, property.getName());
	}
	
	public static PropertyQuery create(PersistentEntityReference reference, String propertyName) {
		PropertyQuery query = PropertyQuery.T.create();
		query.setEntityReference(reference);
		query.setPropertyName(propertyName);
		return query;
	}
	
	public static PropertyQuery create(PersistentEntityReference reference, Property property) {
		return create(reference, property.getName());
	}
	
	public static PropertyQuery create(EntityProperty entityProperty) {
		return create((PersistentEntityReference)entityProperty.getReference(), entityProperty.getPropertyName());
	}
	
	@Override
	default PropertyQuery where(Condition condition) {
		Query.super.where(condition);
		return this;
	}
	
	@Override
	default PropertyQuery distinct() {
		Query.super.distinct();
		return this;
	}
	
	@Override
	default PropertyQuery orderBy(Ordering ordering) {
		Query.super.orderBy(ordering);
		return this;
	}
	
	@Override
	default PropertyQuery orderBy(Object orderValue) {
		Query.super.orderBy(orderValue);
		return this;
	}
	
	@Override
	default PropertyQuery orderBy(OrderingDirection direction, Object orderValue) {
		Query.super.orderBy(direction, orderValue);
		return this;
	}
	
	@Override
	default PropertyQuery paging(Paging paging) {
		Query.super.paging(paging);
		return this;
	}
	
	@Override
	default PropertyQuery paging(int startIndex, int pageSize) {
		Query.super.paging(Paging.create(startIndex, pageSize));
		return this;
	}
	
	@Override
	default PropertyQuery limit(int limit) {
		Query.super.paging(Paging.create(0, limit));
		return this;
	}
	
	@Override
	default PropertyQuery tc(TraversingCriterion tc) {
		Query.super.tc(tc);
		return this;
	}
	
	@Override
	default PropertyQuery restriction(Restriction restriction) {
		Query.super.restriction(restriction);
		return this;
	}


}
