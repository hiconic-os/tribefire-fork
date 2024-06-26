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
package com.braintribe.model.generic.reflection;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.pr.criteria.BasicCriterion;
import com.braintribe.model.generic.pr.criteria.CriterionType;
import com.braintribe.model.generic.pr.criteria.matching.Matcher;

@SuppressWarnings("unusable-by-js")
public class StandardTraversingContext implements TraversingContext {
	private final Map<Object, Object> associatedObjects = new IdentityHashMap<>();
	private final Map<Object, GenericEntity> visitedObjects = new IdentityHashMap<>();
	private final Stack<BasicCriterion> traversingStack = new Stack<>();
	private final Stack<Object> objectStack = new Stack<>();
	private Matcher matcher;
	private TraversingVisitor traversingVisitor;
	private Boolean matching;
	private boolean visitMatchInclusive;
	
	/**
	 * By default, absence info will not be resolved.
	 * 
	 * @see #isAbsenceResolvable(Property, GenericEntity, AbsenceInformation)
	 */
	private boolean absenceResolvable = false;
	private boolean propertyValueUsedForMatching = true;
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#getTraversingStack()
	 */
	@Override
	public Stack<BasicCriterion> getTraversingStack() {
		return traversingStack;
	}
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#getObjectStack()
	 */
	@Override
	public Stack<Object> getObjectStack() {
		return objectStack;
	}
	
	public void setTraversingVisitor(TraversingVisitor traversingVisitor) {
		this.traversingVisitor = traversingVisitor;
	}
	
	public void setVisitMatchInclusive(boolean visitMatchInclusive) {
		this.visitMatchInclusive = visitMatchInclusive;
	}
	
	/**
	 * If the <code>Matcher</code> matches, traversing will be stopped.
	 */
	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#pushTraversingCriterion(com.braintribe.model.generic.pr.criteria.BasicCriterion, java.lang.Object)
	 */
	@Override
	public void pushTraversingCriterion(BasicCriterion criterion, Object object) {
		matching = null;
		traversingStack.push(criterion);
		objectStack.push(object);
		if (traversingVisitor != null && (visitMatchInclusive || !isTraversionContextMatching())) 
			traversingVisitor.visitTraversing(this);
	}
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#popTraversingCriterion()
	 */
	@Override
	public BasicCriterion popTraversingCriterion() {
		objectStack.pop();
		return traversingStack.pop();
	}
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#isTraversionContextMatching()
	 */
	@Override
	public boolean isTraversionContextMatching() {
		if (matching == null) {
			matching = matcher != null && matcher.matches(this);
		}

		return matching;
	}
	

	@Override
	public CriterionType getCurrentCriterionType() {
		if (traversingStack.isEmpty()) {
			return null;
		}
		
		return traversingStack.peek().criterionType();
	}

	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#registerAsVisited(com.braintribe.model.generic.GenericEntity, com.braintribe.model.generic.GenericEntity)
	 */
	@Override
	public void registerAsVisited(GenericEntity entity, Object associate) {
		if (entity == null) return;
		associatedObjects.put(entity, associate);
		visitedObjects.put(entity, entity);
	}
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#getAssociated(com.braintribe.model.generic.GenericEntity)
	 */
	@Override
	public <T> T getAssociated(GenericEntity entity) {
		return (T)associatedObjects.get(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#getVisitedObjects()
	 */
	@Override
	public Collection<GenericEntity> getVisitedObjects() {
		return visitedObjects.values();
	}
	
	@Override
	public <T> Collection<T> getAssociatedObjects() {
		return (Collection<T>)associatedObjects.values();
	}
	
	/* (non-Javadoc)
	 * @see com.braintribe.model.generic.reflection.ITraversingContext#isVisited(com.braintribe.model.generic.GenericEntity)
	 */
	@Override
	public boolean isVisited(GenericEntity entity) {
		if (entity == null) return false;
		
		return visitedObjects.containsKey(entity);
	}
	
	/**
	 * @see com.braintribe.model.generic.reflection.TraversingContext#isPropertyValueUsedForMatching(com.braintribe.model.generic.reflection.EntityType, com.braintribe.model.generic.GenericEntity, com.braintribe.model.generic.reflection.Property)
	 */
	@Override
	public boolean isPropertyValueUsedForMatching(EntityType<?> type, GenericEntity entity, Property property) {
		return propertyValueUsedForMatching;
	}

	@Override
	public boolean isAbsenceResolvable(Property property, GenericEntity entity, AbsenceInformation absenceInformation) {
		return absenceResolvable;
	}

	/**
	 * Sets the result for {@link #isPropertyValueUsedForMatching(EntityType, GenericEntity, Property)} (unless
	 * overridden).
	 */
	public void setPropertyValueUsedForMatching(boolean propertyValueUsedForMatching) {
		this.propertyValueUsedForMatching = propertyValueUsedForMatching;
	}

	/**
	 * Sets the result for {@link #isAbsenceResolvable(Property, GenericEntity, AbsenceInformation)} (unless
	 * overridden).
	 */
	public void setAbsenceResolvable(boolean absenceResolvable) {
		this.absenceResolvable = absenceResolvable;
	}	

}
