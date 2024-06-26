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
package com.braintribe.model.processing.traversing.engine.impl.skip.conditional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.criteria.TraversingCriterion;
import com.braintribe.model.generic.pr.criteria.matching.StandardMatcher;
import com.braintribe.model.generic.processing.pr.fluent.TC;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.StandardTraversingContext;
import com.braintribe.model.generic.typecondition.TypeConditions;
import com.braintribe.model.generic.typecondition.basic.TypeKind;
import com.braintribe.model.processing.traversing.engine.impl.misc.model.TypeA;
import com.braintribe.testing.category.KnownIssue;

/**
 * Tests for {@link TcConfigurableSkipper}
 */
@Category(KnownIssue.class)
public class TcConfigurableSkipperTest extends AbstractSkipperTest {

	@Test
	public void testPropertyMatchListItem() throws Exception {
		
		TcConfigurableSkipper skipper = new TcConfigurableSkipper();
		skipper.setMatcherCheckOnlyProperties(false);
		
		TraversingCriterion tc = TC.create().listElement().done();
		skipper.setTraversionCriterion(tc);
		
		propertyMatchListItem(skipper);
	}
	
	@Test
	public void  testPropertyNameMatch() throws Exception {
		
		TcConfigurableSkipper skipper = new TcConfigurableSkipper();

		TraversingCriterion tc = TC.create().property("someB").done();
		
		skipper.setTraversionCriterion(tc);
		
		propertyNameMatch(skipper);
	}
	
	@Test
	public void  testTypeConditionMatch() throws Exception {
		
		TcConfigurableSkipper skipper = new TcConfigurableSkipper();
		
		TraversingCriterion tc = TC.create().typeCondition(TypeConditions.isKind(TypeKind.listType)).done();

		skipper.setTraversionCriterion(tc);
		
		matchesTypeOfMatch(skipper);
	}
	
	@Test
	public void  testNegationMatch() throws Exception {
		
		TcConfigurableSkipper skipper = new TcConfigurableSkipper();
		skipper.setMatcherCheckOnlyProperties(true);
		
		TraversingCriterion tc = TC.create().negation().root().done();

		skipper.setTraversionCriterion(tc);
		
		negationMatch(skipper);
	}
	
	
	@Test
	public void  testPatternMatch() throws Exception {
		
		TcConfigurableSkipper skipper = new TcConfigurableSkipper();
		skipper.setMatcherCheckOnlyProperties(false);
		
		TraversingCriterion tc = TC.create().pattern().
												root().
												entity().
												property("someB").
												close().
											done();

		skipper.setTraversionCriterion(tc);
		
		sequenceMatch(skipper);
	}
	
	@Test
	public void  testDisjunctionMatch() throws Exception {
		
		TcConfigurableSkipper skipper = new TcConfigurableSkipper();
		skipper.setMatcherCheckOnlyProperties(false);
		
		TraversingCriterion tc = TC.create().disjunction().
												property("enumA").
												property("someB").
												close().
											done();

		skipper.setTraversionCriterion(tc);
		
		disjunctionMatch(skipper);
	}
	
	@Test
	public void  testConjunctionMatch() throws Exception {
		
		TcConfigurableSkipper skipper = new TcConfigurableSkipper();
		skipper.setMatcherCheckOnlyProperties(false);
		
		TraversingCriterion tc = TC.create().conjunction().
												typeCondition(TypeConditions.isKind(TypeKind.listType)).
												property("list").
												close().
											done();
		
		skipper.setTraversionCriterion(tc);
		
		conjunctionMatch(skipper);
	}
	
	@Ignore
	@Test
	public void testOldTraversing(){
		
		TraversingCriterion tc = TC.create().negation().root().done();
		
		StandardMatcher matcher = new StandardMatcher();
		matcher.setCheckOnlyProperties(false);
		matcher.setCriterion(tc);
		
		StandardTraversingContext traversingContext = new StandardTraversingContext();
		traversingContext.setMatcher(matcher);
		
		EntityType<GenericEntity> entityType = GMF.getTypeReflection().getEntityType(TypeA.class);
		
		Object root = getAssembly();
		
		entityType.traverse(traversingContext, root);
		
		Object[] array= traversingContext.getVisitedObjects().toArray();
		for(Object obj : array){
			System.out.println(obj);
		}
	}
	
}
