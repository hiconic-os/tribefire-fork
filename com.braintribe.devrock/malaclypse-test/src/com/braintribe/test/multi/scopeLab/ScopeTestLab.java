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
package com.braintribe.test.multi.scopeLab;

import java.io.File;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import com.braintribe.model.artifact.Solution;
import com.braintribe.model.malaclypse.cfg.denotations.WalkKind;
import com.braintribe.model.malaclypse.cfg.denotations.scopes.DependencyScope;
import com.braintribe.model.malaclypse.cfg.denotations.scopes.ScopeTreatement;


/**
 * ScopeTestTerminal
 * 	A
 * 		C (provided) - should only appear if A is the terminal and compile build is active 
 * 	B
 * 		D (optional) 
 * 	P (provided)
 * 	T (test)
 * 
 * @author pit
 *
 */
public class ScopeTestLab extends AbstractScopeLab {
		
	
	protected static File settings = new File( "res/scopeTest/contents/settings.xml");
	
	@BeforeClass
	public static void before() {
		before( settings);
	}


	@Override
	protected void testPresence(Collection<Solution> solutions, File repository) {
		super.testPresence(solutions, repository);
	}	
	
	@Test
	public void testCompileOptionalIncluded() {
		String[] expectedNames = new String [] {					
				"com.braintribe.test.dependencies.scopeTest:A#1.0",
				"com.braintribe.test.dependencies.scopeTest:B#1.0",					
				"com.braintribe.test.dependencies.scopeTest:D#1.0",				
				"com.braintribe.test.dependencies.scopeTest:P#1.0",
				
		};
		
		runTest( "com.braintribe.test.dependencies.scopeTest:ScopeTestTerminal#1.0", expectedNames, ScopeKind.compile, WalkKind.classpath, false);
	}
	
	@Test
	public void testCompileOnA_ProvidedTest() {
		String[] expectedNames = new String [] {					
				"com.braintribe.test.dependencies.scopeTest:C#1.0",
		};
		
		runTest( "com.braintribe.test.dependencies.scopeTest:A#1.0", expectedNames, ScopeKind.compile, WalkKind.classpath, false);
	}
	
	@Test
	public void testLaunchOptionalIncluded() {
		String[] expectedNames = new String [] {					
				"com.braintribe.test.dependencies.scopeTest:A#1.0",
				"com.braintribe.test.dependencies.scopeTest:B#1.0",
				"com.braintribe.test.dependencies.scopeTest:D#1.0",	
				
		};
		
		runTest( "com.braintribe.test.dependencies.scopeTest:ScopeTestTerminal#1.0", expectedNames, ScopeKind.launch, WalkKind.classpath, false);
	}
	
	@Test
	public void testCompileOptionalExcluded() {
		String[] expectedNames = new String [] {					
				"com.braintribe.test.dependencies.scopeTest:A#1.0",
				"com.braintribe.test.dependencies.scopeTest:B#1.0",	
				"com.braintribe.test.dependencies.scopeTest:P#1.0",	
		};
		
		runTest( "com.braintribe.test.dependencies.scopeTest:ScopeTestTerminal#1.0", expectedNames, ScopeKind.compile, WalkKind.classpath, true);
	}
	
	@Test
	public void testLaunchOptionalExcluded() {
		String[] expectedNames = new String [] {					
				"com.braintribe.test.dependencies.scopeTest:A#1.0",
				"com.braintribe.test.dependencies.scopeTest:B#1.0",			
		};
		
		runTest( "com.braintribe.test.dependencies.scopeTest:ScopeTestTerminal#1.0", expectedNames, ScopeKind.launch, WalkKind.classpath, true);
	}
	
	@Test
	public void testCompileOptionalExcludedPlusTest() {
		String[] expectedNames = new String [] {					
				"com.braintribe.test.dependencies.scopeTest:A#1.0",
				"com.braintribe.test.dependencies.scopeTest:B#1.0",	
				"com.braintribe.test.dependencies.scopeTest:P#1.0",
				"com.braintribe.test.dependencies.scopeTest:T#1.0",
		};
		DependencyScope testScope = DependencyScope.T.create();
		testScope.setName("test");
		testScope.setScopeTreatement(ScopeTreatement.INCLUDE);
		
		runTest( "com.braintribe.test.dependencies.scopeTest:ScopeTestTerminal#1.0", expectedNames, ScopeKind.compile, WalkKind.classpath, true, testScope);
	}
	

	
}
