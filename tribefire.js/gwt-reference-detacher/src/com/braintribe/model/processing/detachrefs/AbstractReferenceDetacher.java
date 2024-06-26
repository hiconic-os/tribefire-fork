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
package com.braintribe.model.processing.detachrefs;

import static com.braintribe.utils.lcd.CollectionTools2.isEmpty;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMapType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.GmTypeKind;
import com.braintribe.model.meta.data.QualifiedProperty;
import com.braintribe.model.processing.manipulator.api.ReferenceDetacher;
import com.braintribe.model.processing.manipulator.api.ReferenceDetacherException;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;

/**
 * 
 */
public abstract class AbstractReferenceDetacher implements ReferenceDetacher {

	protected GenericModelTypeReflection typeReflection = GMF.getTypeReflection();

	@Override
	public void detachReferences(QualifiedProperty qualifiedProperty, GenericEntity entityToDetach, boolean force) {
		Property property = findReflectionProperty(qualifiedProperty);
		if (property == null)
			return;

		EntityQuery query = build(qualifiedProperty, entityToDetach);
		String detachNotAllowedReason = force ? null : ReferenceDetacherTools.canDetachValueFrom(qualifiedProperty, acquireCmdResolver());
		executeDetach(query, qualifiedProperty, property, entityToDetach, detachNotAllowedReason);
	}

	protected abstract CmdResolver acquireCmdResolver();

	protected Property findReflectionProperty(QualifiedProperty qualifiedProperty) {
		GmEntityType propertyOwnerType = qualifiedProperty.getEntityType();
		EntityType<?> entityType = typeReflection.findType(propertyOwnerType.getTypeSignature());

		if (entityType != null)
			return entityType.findProperty(qualifiedProperty.getProperty().getName());
		else
			return null;
	}

	protected abstract void executeDetach(EntityQuery query, QualifiedProperty qualifiedProperty, Property property, GenericEntity entityToDetach,
			String detachProblem);

	protected void checkDetachAllowed(String detachProblem, EntityQueryResult queryResult, QualifiedProperty property, GenericEntity entityToDetach) {
		if (detachProblem != null && !isEmpty(queryResult.getEntities()))
			throw new ReferenceDetacherException("Reference cannot be detached. Entity: " + entityToDetach + " is referenced by: "
					+ queryResult.getEntities() + " via " + property + " Reason: " + detachProblem);
	}

	@Override
	public boolean existsReference(QualifiedProperty property, GenericEntity entityToDetach) {
		EntityQuery query = build(property, entityToDetach);
		EntityQueryResult queryResult = executeQuery(query);

		return !queryResult.getEntities().isEmpty();
	}

	protected abstract EntityQueryResult executeQuery(EntityQuery query);

	/**
	 * Convenience method, delegates to {@link #removeReferences(List, QualifiedProperty, Property, GenericEntity)} with
	 * entities from given result.
	 */
	protected void removeReferences(EntityQueryResult queryResult, QualifiedProperty qualifiedProperty, Property property,
			GenericEntity entityToDetach) {
		removeReferences(queryResult.getEntities(), qualifiedProperty, property, entityToDetach);
	}

	protected void removeReferences(List<GenericEntity> referees, QualifiedProperty qualifiedProperty, Property property,
			GenericEntity entityToDetach) {
		switch (qualifiedProperty.propertyType().typeKind()) {
			case BASE:
			case ENTITY:
				clearProperty(referees, property);
				break;
			case LIST:
				removeFromCollection(referees, property, entityToDetach, false);
				break;
			case SET:
				removeFromCollection(referees, property, entityToDetach, true);
				break;
			case MAP:
				GmMapType mapType = (GmMapType) qualifiedProperty.propertyType();
				removeFromMap(referees, property, entityToDetach, mapType);
				break;
			default:
				break;
		}
	}

	private void removeFromMap(List<GenericEntity> referees, Property property, GenericEntity entityToDetach, GmMapType mapType) {
		boolean handleKeys = isAssignableFromEntity(mapType.getKeyType().typeKind());
		boolean handleValues = isAssignableFromEntity(mapType.getValueType().typeKind());

		for (GenericEntity referee : referees) {
			Map<?, ?> propertyValue = (Map<?, ?>) property.get(referee);

			if (handleKeys)
				removeFromCollection(propertyValue.keySet(), entityToDetach, true);

			if (handleValues)
				removeFromCollection(propertyValue.values(), entityToDetach, false);
		}
	}

	private boolean isAssignableFromEntity(GmTypeKind keyKind) {
		return keyKind == GmTypeKind.ENTITY || keyKind == GmTypeKind.BASE;
	}

	private void removeFromCollection(Collection<GenericEntity> referees, Property property, GenericEntity entityToDetach, boolean fast) {
		for (GenericEntity referee : referees) {
			// TODO OPTIMIZE no need to load if property is Set
			Collection<?> propertyValue = (Collection<?>) property.get(referee);
			removeFromCollection(propertyValue, entityToDetach, fast);
		}
	}

	private void removeFromCollection(Collection<?> collection, GenericEntity entityToDetach, boolean fast) {
		if (fast) {
			collection.remove(entityToDetach);

		} else {
			// we are assuming there are no nested collections
			Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				if (entityToDetach.equals(it.next())) {
					it.remove();
				}
			}
		}
	}

	private void clearProperty(List<GenericEntity> referees, Property property) {
		for (GenericEntity referee : referees) {
			property.set(referee, null);
		}
	}

	// ############################################################
	// ## . . . . . . . . Building Referee Query . . . . . . . . ##
	// ############################################################

	private static final int BATCH_SIZE = 10_000;

	/* TODO: in case of GmMapType we must differ between key and value references but the query model as well as the
	 * query processors must first support containsKey */

	private static final EntityQuery build(QualifiedProperty qualifiedProperty, GenericEntity entity) {
		String signature = qualifiedProperty.getEntityType().getTypeSignature();
		GmProperty property = qualifiedProperty.getProperty();

		if (property.getType().isGmCollection()) {
			return EntityQueryBuilder.from(signature).where().property(property.getName()).contains().entity(entity).limit(BATCH_SIZE).done();
		} else {
			return EntityQueryBuilder.from(signature).where().property(property.getName()).eq().entity(entity).limit(BATCH_SIZE).done();
		}
	}
}
