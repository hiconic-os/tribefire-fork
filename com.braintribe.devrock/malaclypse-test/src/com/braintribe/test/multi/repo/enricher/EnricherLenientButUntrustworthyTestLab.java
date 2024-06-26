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
package com.braintribe.test.multi.repo.enricher;

import java.io.File;
import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braintribe.build.artifact.retrieval.multi.repository.reflection.impl.SolutionListPresence;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.malaclypse.cfg.denotations.WalkKind;


/**
 *
 * 
 * 
 *
 * @author pit
 *
 */
public class EnricherLenientButUntrustworthyTestLab extends AbstractEnricherRepoLab {
		
	
	protected static File settings = new File( "res/enricherLab/contents/settings.listing-lenient.untrustworthy.xml");
	protected static String group = "com.braintribe.devrock.test.lenient";
	protected static String version = "1.0";
	protected static String [] knownMissing = new String [] { "javadoc.zip", ".jdar", "javadoc.jar", "classes.jar"};	
	protected static String [] knownPresent = new String [] { "sources.jar", "javadoc.jar", "javadoc.zip", ".jdar", ".jar", ".pom", ".md5", ".sha1", ".xml"};
	
	@BeforeClass
	public static void before() {
		before( settings);
	}


	@Override
	protected void testPresence(Collection<Solution> solutions, File repository) {
		super.testPresence(solutions, repository);
	}	
	
	@Test
	public void testLenientAndTrustworthy() {
		String[] expectedNames = new String [] {						
				group + ":" + "a" + "#" + version, // no packaging -> jar
				group + ":" + "b" + "#" + version, // jar packaging
		};
		
		// first run 
		runTest( null, group + ":" + "lenient-test-terminal-one" + "#" + version, expectedNames, ScopeKind.compile, WalkKind.classpath, false);
		
		// second run 
		runTest( null, group + ":" + "lenient-test-terminal-one" + "#" + version, expectedNames, ScopeKind.compile, WalkKind.classpath, false);
		
		validateIndices("braintribe.Base", expectedNames[0], knownPresent);
		validateIndices("braintribe.Base", expectedNames[1], knownPresent);
	}
	
	protected void validateStatus( String name, SolutionListPresence presence) {
		if (presence == SolutionListPresence.missing) {
			boolean accepted = false;
			for (String s : knownMissing) {
				if (name.endsWith(s)) {			
					accepted = true;
					break;
				}
			}
			if (accepted == false) {				
				Assert.fail( "file [" + name + "] has status [" + presence + "]");
			}
		}	
	}
		
}