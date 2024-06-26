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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.codebase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionContext;
import com.braintribe.devrock.mc.core.wired.resolving.Validator;
import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;

/**
 * tests that a resolution (restricted on pom files) works with single codebase repositories, in all 3 structual possiblities
 * 
 * NOTE: test currently runs only successfully if ran as standalone. If run within a group (and 'release' run) it will fail. 
 * 
 * @author pit / dirk
 *
 */
public class PlainCodebaseClasspathResolvingTest extends AbstractCodebaseClasspathResolvingTest {
	
	private List<Pair<File,Boolean>> filesToCheckOnExistance = new ArrayList<>();
	{
		filesToCheckOnExistance.add( Pair.of( new File( repo, "last-probing-result-archive.yaml"), true));
	}
	
	@Override
	protected RepoletContent archiveInput() {
		return RepoletContent.T.create();		
	}
	
		
	/**
	 * tests a 'standard BT style codebase structure' for a single group, i.e. building within a single group directory 
	 */
	@Test
	public void testPlainOnStructuralGroup() {
		AnalysisArtifactResolution resolution = run("com.braintribe.devrock.test:t#1.0.1", ClasspathResolutionContext.build().enrichJar(false).done(), new File(input, "structural.group/complete"), "${artifactId}");		

		Validator validator = new Validator(true);
		validator.validate(new File(input, "structural.group/plain.validation.txt"), resolution);
		validator.validateFileExistance( filesToCheckOnExistance);		 		
		validator.assertResults();
	}
	
	/**
	 * tests a 'standard BT style codebase structure' for multiple groups, i.e. building across multiple group directories
	 */
	@Test
	public void testPlainOnStructuralMetaGroup() {
		AnalysisArtifactResolution resolution = run("com.braintribe.devrock.test.t:t#1.0.1", ClasspathResolutionContext.build().enrichJar(false).done(), new File(input, "structural.metagroup/complete"), "${groupId}/${artifactId}");
	
		Validator validator = new Validator(true);
		validator.validate(new File(input, "structural.metagroup/plain.validation.txt"), resolution);
		validator.validateFileExistance( filesToCheckOnExistance);
		validator.assertResults();
	}
	
	/**
	 *  tests a codebase that is structured like a standard 'maven repository', i.e. building in a 'old BT style' structure
	 */
	@Test
	public void testPlainOnStructuralMaven() {		
		AnalysisArtifactResolution resolution = run("com.braintribe.devrock.test.t:t#1.0.1", ClasspathResolutionContext.build().enrichJar(false).done(), new File(input, "structural.maven/complete"), "${groupId.expanded}/${artifactId}/${version}");
		
		Validator validator = new Validator(true);
		validator.validate(new File(input, "structural.maven/plain.validation.txt"), resolution);
		validator.validateFileExistance( filesToCheckOnExistance);
		validator.assertResults();		
	}

	
}
