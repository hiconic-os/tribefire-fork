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
package com.braintribe.devrock.zarathud.test.extraction.comparison;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.zarathud.test.common.HasCommonFilesystemNode;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.zarathud.model.data.Artifact;

/**
 * 
 * @author pit
 */
public class FullComparsionTest extends AbstractComparisonTest implements HasCommonFilesystemNode {
	
	private File output;

	{	
		Pair<File,File> pair = filesystemRoots("extraction");	
		output = pair.second;				
	}

	private void runTest( String baseTerminal, String otherTerminal, File output) {
		Maybe<Artifact> maybe = extract(baseTerminal);
		if (maybe.isEmpty()) {
			Assert.fail( maybe.whyUnsatisfied().stringify());
		}

		Artifact baseArtifact = maybe.value();
		
		maybe = extract( otherTerminal);
		if (maybe.isEmpty()) {
			Assert.fail( maybe.whyUnsatisfied().stringify());
		}

		Artifact otherArtifact = maybe.value();
		
		compare(baseArtifact, otherArtifact, output);		
	}
	
	@Test
	public void testAnalysisArtifact() {
		runTest( "com.braintribe.devrock:analysis-artifact-model#2.0.22", "com.braintribe.devrock:analysis-artifact-model#2.0.18", new File( output, "analyis-artifact-result.yaml"));
	}

	@Test
	public void testMcCore() {
		runTest( "com.braintribe.devrock:mc-core#2.0.55", "com.braintribe.devrock:mc-core#2.0.54", new File( output, "mc-core-result.yaml"));
	}
	
	@Test
	public void testLibrary() {
		runTest( "com.braintribe.devrock.zarathud.test:library-base#1.0.1-pc", "com.braintribe.devrock.zarathud.test:library-other#1.0.1-pc", new File( output, "test-library"));
	}
}
