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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.context.filter;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionContext;
import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionScope;
import com.braintribe.devrock.mc.api.transitive.TransitiveResolutionContext;
import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.model.artifact.essential.ArtifactIdentification;

/**
 * tests global exclusions - not via pom, but via the context
 * 
 * @author pit
 *
 */

public class GlobalExclusionsTest extends AbstractResolvingContextTest {

	@Override
	protected RepoletContent archiveInput() {		
		return archiveInput( COMMON_CONTEXT_DEFINITION_YAML);
	}
	
	/**
	 * test run with the TDR
	 */
	@Test
	public void runGlobalExclusionPerContextTest() {
		// remove a-1, b-1
		Set<ArtifactIdentification> exclusions = new HashSet<>();
		exclusions.add( ArtifactIdentification.create( "com.braintribe.devrock.test", "a-1"));
		exclusions.add( ArtifactIdentification.create( "com.braintribe.devrock.test", "b-1"));
		TransitiveResolutionContext trc = TransitiveResolutionContext.build().globalExclusions(exclusions).done();
		runAndValidate(trc, "filtered.context.validation.yaml");
	}
	
	/**
	 * test run with CPR
	 */
	@Test
	public void runGlobalExclusionPerContextCprTest() {
		// remove a-1, b-1
		Set<ArtifactIdentification> exclusions = new HashSet<>();
		exclusions.add( ArtifactIdentification.create( "com.braintribe.devrock.test", "a-1"));
		exclusions.add( ArtifactIdentification.create( "com.braintribe.devrock.test", "b-1"));
		ClasspathResolutionContext trc = ClasspathResolutionContext.build().scope(ClasspathResolutionScope.compile).globalExclusions(exclusions).done();
		runAndValidate(trc, "filtered.context.validation.yaml");
	}


}
