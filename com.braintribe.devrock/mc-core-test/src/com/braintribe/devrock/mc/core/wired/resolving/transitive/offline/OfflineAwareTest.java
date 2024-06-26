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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.offline;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.devrock.repolet.generator.RepositoryGenerations;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.testing.category.KnownIssue;

/**
 * runs TDR and CPR resolutions, for each first with an online repolet, then shutsdown the repolet and runs the 
 * resolution again. There should be no failure on the second run, and the two resolutions MUST be identical, 
 * even if the repolet is offline during the second run.
 *  
 * @author pit
 *
 */
@Category(KnownIssue.class)
public class OfflineAwareTest extends AbstractOfflineHandlingTest {

	@Override
	protected RepoletContent archiveInput() {
		File file = new File( input, "archive.definition.yaml");
		try {
			return RepositoryGenerations.unmarshallConfigurationFile( file);
		} catch (Exception e) {
			Assert.fail("cannot load [" + file.getAbsolutePath() + "] as " + e.getLocalizedMessage());
		}
		return null;
	}
	
	
	/**
	 * tests the TDR
	 */
	@Test
	public void testTdr() {		
		launchRepolet();
		// run first resolving
		AnalysisArtifactResolution resolutionOnline;
		try {
			resolutionOnline = run("com.braintribe.devrock.test:t#1.0.1",standardTransitiveResolutionContext);
		} catch (Exception e) {
			Assert.fail("cannot run online phase of test as " + e);
			return;
		}
		stopRepolet();
		
		// run second resolving
		AnalysisArtifactResolution resolutionOffline;
		try {
			resolutionOffline = run("com.braintribe.devrock.test:t#1.0.1",standardTransitiveResolutionContext);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("cannot run offline phase of test as " + e);
			return;
		}
		// compare resolvings 
	
		compare(resolutionOnline, resolutionOffline);		
	}
	/**
	 * tests the CPR
	 */
	@Test
	public void testCpr() {		
		launchRepolet();
		// run first resolving
		AnalysisArtifactResolution resolutionOnline;
		try {
			resolutionOnline = run("com.braintribe.devrock.test:t#1.0.1",standardClasspathResolutionContext);
		} catch (Exception e) {
			Assert.fail("cannot run online phase of test as " + e);
			return;
		}
		stopRepolet();
		
		// run second resolving
		AnalysisArtifactResolution resolutionOffline;
		try {
			resolutionOffline = run("com.braintribe.devrock.test:t#1.0.1",standardClasspathResolutionContext);
		} catch (Exception e) {
			Assert.fail("cannot run offline phase of test as " + e);
			return;
		}
		// compare resolvings 
	
		compare(resolutionOnline, resolutionOffline);		
	}

	
}
