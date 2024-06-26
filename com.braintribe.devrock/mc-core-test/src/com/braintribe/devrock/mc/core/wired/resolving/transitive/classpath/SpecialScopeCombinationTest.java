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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.classpath;

import java.io.File;

import org.junit.Test;

import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionContext;
import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionScope;
import com.braintribe.devrock.mc.core.wired.resolving.Validator;
import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.devrock.repolet.generator.RepositoryGenerations;
import com.braintribe.exception.Exceptions;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;

/**
 * tests special scopes, i.e. 'magick' scopes and their effect on dependency scopes
 * @author pit
 *
 */

// TODO: test case for 'test' is returning 't-provided' (but not 'a-provided', 'b-provided'), no 'provided' should be there as it's a runtime cp for running tests?
public class SpecialScopeCombinationTest extends AbstractClasspathResolvingTest {

	@Override
	protected RepoletContent archiveInput() {
		File file = new File( input, "special.scopes.definition.yaml");
		try {
			return RepositoryGenerations.unmarshallConfigurationFile( file);
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "cannot parse file [" + file.getAbsolutePath() + "]", IllegalStateException::new);
		} 	
	}
	
	@Test
	public void testRuntimeScope() {
		AnalysisArtifactResolution resolution = runAsArtifact("com.braintribe.devrock.test:t#1.0.1", ClasspathResolutionContext.build().scope(ClasspathResolutionScope.runtime).done(), "scope-runtime");

		Validator validator = new Validator();
		
		// validate result 
		validator.validate( new File( input, "runtime.scope.validation.yaml"), resolution);
	
		// validate that no error's returned 
		validator.validateFailedResolution(resolution, null, null);
				
		validator.assertResults();
	}
	
	@Test
	public void testCompileScope() {
		AnalysisArtifactResolution resolution = runAsArtifact("com.braintribe.devrock.test:t#1.0.1", ClasspathResolutionContext.build().scope(ClasspathResolutionScope.compile).done(), "scope-compile");

		Validator validator = new Validator();
		
		// validate result 
		validator.validate( new File( input, "compile.scope.validation.yaml"), resolution);
	
		// validate that no error's returned 
		validator.validateFailedResolution(resolution, null, null);
				
		validator.assertResults();

	}


	@Test
	public void testTestScope() {
		AnalysisArtifactResolution resolution = runAsArtifact("com.braintribe.devrock.test:t#1.0.1", ClasspathResolutionContext.build().scope(ClasspathResolutionScope.test).done(), "scope-test");

		Validator validator = new Validator();
		
		// validate result 
		validator.validate( new File( input, "test.scope.validation.yaml"), resolution);
	
		// validate that no error's returned 
		validator.validateFailedResolution(resolution, null, null);
				
		validator.assertResults();

	}
}
