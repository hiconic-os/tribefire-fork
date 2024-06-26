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
package com.braintribe.test.merge;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.build.artifact.name.NameParser;
import com.braintribe.build.artifact.name.NameParserException;
import com.braintribe.build.artifact.walk.multi.clash.merger.DependencyMerger;
import com.braintribe.build.artifact.walk.multi.clash.merger.DependencyMergerImpl;
import com.braintribe.model.artifact.Dependency;

public class DependencyRangeMerging {

	
	private static final String SCOPE_ID = "na";
	private String [] noMergeNames = new String [] { "a.b.c:A#1.0", "a.b.c:A#2.0"};
	private String [] innerIntervalNames = new String [] {"a.b.c:A#[1.0, 2.0]", "a.b.c:A#[1.1, 1.9]"};
	private String [] intersectingIntervalNames = new String [] {"a.b.c:A#[1.0, 1.6]", "a.b.c:A#[1.5, 2.0]"};
	private String [] intersectingIntervalShortNames = new String [] {"a.b.c:A#1.6.^", "a.b.c:A#[1.6.1, 1.6.8]"};	
	private String [] intersectingIntervalShortNames2 = new String [] {"a.b.c:A#[1.6.1, 1.6.8]", "a.b.c:A#1.6.^", };
	private String [] intersectingIntervalShortNames3 = new String [] {"a.b.c:A#1.6.2.^", "a.b.c:A#[1.6.1, 1.6.8]"};
	private String [] noMergeIntervalNames = new String [] {"a.b.c:A#[1.0, 1.9]", "a.b.c:A#[2.0, 2.1]"};
	private String [] collapsingIntervalNames = new String [] {"a.b.c:A#[1.0, 2.0]", "a.b.c:A#[2.0, 2.1]"};
	private String [] collapsingClosedIntervalNames = new String [] {"a.b.c:A#[1.0, 2.0)", "a.b.c:A#(2.0, 2.1]"};
	private String [] mergeToSingleVersionNames = new String[] {"a.b.c:A#[1.0, 2.0)", "a.b.c:A#[1.4,1.6]"};
		
	
	private DependencyMerger dependencyMerger = new DependencyMergerImpl();

	
	private Collection<Dependency> createDependenciesFromStrings( String [] names) {
		Collection<Dependency> dependencies = new ArrayList<Dependency>();
		for (String name : names) {
			try {
				Dependency dependency = NameParser.parseCondensedDependencyName(name);
				dependencies.add( dependency);
			} catch (NameParserException e) {
				fail( "exception thrown while parsing [" + name + "]:" + e);
			}
		}
		return dependencies;
	}
	
	private void listDependencies( Collection<Dependency> dependencies) {
		for (Dependency dependency : dependencies) {
			System.out.println(NameParser.buildName( dependency));
		}
	}

	@Test
	public void noMergetest() {
		Collection<Dependency> suspects = createDependenciesFromStrings(noMergeNames);
		Collection<Dependency> result = dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		Assert.assertTrue( "merge has taken place", suspects.size() == result.size());
	}
	@Test
	public void noMergeIntervalTest() {
		System.out.println("**** no merge intersecting interval ****");
		Collection<Dependency> suspects = createDependenciesFromStrings(noMergeIntervalNames);
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		System.out.println("result");		
		listDependencies(result);
		Assert.assertTrue( "merge has taken place", suspects.size() == result.size());
		
	}
	
	@Test
	public void mergeInnerIntervaltest() {
		System.out.println("**** inner interval ****");
		Collection<Dependency> suspects = createDependenciesFromStrings( innerIntervalNames);
		listDependencies( suspects);
		
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		System.out.println("result");
		listDependencies(result);
		
		Assert.assertTrue( "merge has not taken place", suspects.size() != result.size());
		
	}
	
	@Test
	public void mergeIntersectingIntervaltest() {
		System.out.println("**** intersecting interval ****");
		Collection<Dependency> suspects = createDependenciesFromStrings( intersectingIntervalNames);
		listDependencies( suspects);
		
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		Assert.assertTrue( "merge has not taken place", suspects.size() != result.size());
		//Dependency resultingDependency = result.toArray( new Dependency[0])[0];
		System.out.println("result");
		listDependencies(result);
		
	}
	@Test
	public void mergeIntersectingIntervalShortNameTest() {
		System.out.println("**** intersecting fuzzy interval ****");
		Collection<Dependency> suspects = createDependenciesFromStrings( intersectingIntervalShortNames);
		listDependencies( suspects);
		
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		System.out.println("result");
		listDependencies(result);
		
		Assert.assertTrue( "merge has not taken place", suspects.size() != result.size());
	}
	
	@Test
	public void mergeIntersectingIntervalShortNameTest2() {
		System.out.println("**** intersecting fuzzy interval reversed****");
		Collection<Dependency> suspects = createDependenciesFromStrings( intersectingIntervalShortNames2);
		listDependencies( suspects);
		
		Collection<Dependency> result = dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		//Dependency resultingDependency = result.toArray( new Dependency[0])[0];
		System.out.println("result");
		listDependencies(result);		
		Assert.assertTrue( "merge has not taken place", suspects.size() != result.size());
	}
	
	@Test
	public void mergeIntersectingIntervalShortNameTest3() {
		System.out.println("**** intersecting fuzzy interval indexed ****");
		Collection<Dependency> suspects = createDependenciesFromStrings( intersectingIntervalShortNames3);
		listDependencies( suspects);
		
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		//Dependency resultingDependency = result.toArray( new Dependency[0])[0];
		System.out.println("result");
		listDependencies(result);
		Assert.assertTrue( "merge has not taken place", suspects.size() != result.size());
		
	}
	
	/**
	 * [1.0, 2.0] & [2.0, 2.1] should actually lead to [2.0,2.0], but the question is how represent that.. 
	 */
	@Test
	public void mergeCollapsingIntervalNameTest() {
		System.out.println("**** collapsing interval ****");
		Collection<Dependency> suspects = createDependenciesFromStrings( collapsingIntervalNames);
		listDependencies( suspects);
		
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		System.out.println("result");
		listDependencies(result);		
		Assert.assertTrue( "merge has taken place", suspects.size() == result.size());		
	}
	@Test
	public void mergeCollapsingClosedIntervalNameTest() {
		System.out.println("**** collapsing closed interval ****");
		Collection<Dependency> suspects = createDependenciesFromStrings( collapsingClosedIntervalNames);
		listDependencies( suspects);
		
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		System.out.println("result");
		listDependencies(result);		
		Assert.assertTrue( "merge has taken place", suspects.size() == result.size());		
	}
	
	@Test
	public void mergeCollapsingToSingleNameTest() {
		System.out.println("**** collapsing to single version ****");
		Collection<Dependency> suspects = createDependenciesFromStrings( mergeToSingleVersionNames);
		listDependencies( suspects);
		
		Collection<Dependency> result =  dependencyMerger.mergeDependencies(SCOPE_ID, suspects, new ArrayList<Dependency>());
		
		System.out.println("result");
		listDependencies(result);		
		Assert.assertTrue( "merge has not taken place", suspects.size() != result.size());		
	}
	
	

}
