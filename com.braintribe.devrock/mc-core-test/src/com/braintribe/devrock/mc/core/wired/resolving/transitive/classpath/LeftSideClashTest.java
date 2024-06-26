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
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.model.artifact.analysis.ClashResolvingStrategy;
import com.braintribe.model.resource.FileResource;

/**
 * tests case https://docs.google.com/drawings/d/1DMIErztsLVXLrnruwdAWqdk35dkeoEiSOPbGsRjoAQA, left side
 * 
 *  tests a dependency tree with clashes where both {@link ClashResolvingStrategy} deliver the same result
 *  
 * @author pit
 *
 */
public class LeftSideClashTest extends AbstractClasspathResolvingTest {
	private static final boolean verbose = false;
	
	@Override
	protected RepoletContent archiveInput() {	
		File file = new File( input, "simpleClashingTree.txt");
		try {
			return RepositoryGenerations.parseConfigurationFile( file);
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "cannot parse file [" + file.getAbsolutePath() + "]", IllegalStateException::new);
		} 
	}	
	
	@Test
	public void testLeftSideFirstOccurrence() {
		ClasspathResolutionContext resolutionContext = ClasspathResolutionContext.build() //
			.clashResolvingStrategy(ClashResolvingStrategy.firstOccurrence) // 
			.lenient(false) // 
			.scope(ClasspathResolutionScope.compile) //
			.done(); //
		
		AnalysisArtifactResolution resolution = run("com.braintribe.devrock.test:t#[1.0,1.1)", resolutionContext, "left-side");
		
		if (verbose) {
			for (AnalysisArtifact artifact: resolution.getSolutions()) {
				System.out.println(artifact.asString());
				
				getCpJarParts(artifact).forEach(p -> System.out.println(" - " + ((FileResource)p.getResource()).getPath())); 
			}
		}
		
		Validator validator = new Validator();
		validator.validateExpressive( new File( input, "simpleClashingTree.validation.txt"), resolution);
		validator.assertResults();
	}
		

	@Test
	public void testLeftSideHighestVersion() {
		ClasspathResolutionContext resolutionContext = ClasspathResolutionContext.build() //
				.clashResolvingStrategy(ClashResolvingStrategy.highestVersion) // 
				.lenient(false) // 
				.scope(ClasspathResolutionScope.compile) //
				.done(); //
		
		AnalysisArtifactResolution resolution = run("com.braintribe.devrock.test:t#[1.0,1.1)", resolutionContext, "left-side-highest");
		
		if (verbose) {
			for (AnalysisArtifact artifact: resolution.getSolutions()) {
				System.out.println(artifact.asString());
				
				getCpJarParts(artifact).forEach(p -> System.out.println(" - " + ((FileResource)p.getResource()).getPath())); 
			}
		}
		Validator validator = new Validator();
		validator.validateExpressive( new File( input, "simpleClashingTree.validation.txt"), resolution);
		validator.assertResults();
	}
}
