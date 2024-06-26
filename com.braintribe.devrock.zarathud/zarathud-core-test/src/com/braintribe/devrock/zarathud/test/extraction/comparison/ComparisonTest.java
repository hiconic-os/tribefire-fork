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

import org.junit.Test;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.zarathud.test.common.HasCommonFilesystemNode;

public class ComparisonTest extends AbstractComparisonTest implements HasCommonFilesystemNode{
	
	private File input;
	private File output;

	{	
		Pair<File,File> pair = filesystemRoots("extraction");
		input = pair.first;
		output = pair.second;				
	}
	
	@Test 
	public void compareIdenticalExtractionsOnAnalysisArtifactModel() {			
		File fileA = new File( input, "analysis-artifact-model-1.yaml");
		File fileB = new File( input, "analysis-artifact-model-2.yaml");		
		compare( fileA, fileB, new File( output, "analysis-artifact-identical.comparision.yaml"));				
	}
	
	@Test
	public void compareAnalysisArtifactModel() {
		File fileA = new File( input, "analysis-artifact-model-2.0.22.yaml");
		File fileB = new File( input, "analysis-artifact-model-2.0.18.yaml");		
		compare( fileA, fileB, new File( output, "analysis-artifact-model.comparision.yaml"));
	}
	
	@Test 
	public void compareIdenticalExtractionsOnMcCore() {
		File fileA = new File( input, "mc-core-1.yaml");
		File fileB = new File( input, "mc-core-2.yaml");
		File out = new File( output, "mc-core-identical.comparision.yaml");		
		compare( fileA, fileB, out);				
	}
	
	@Test 
	public void compareOnMcCore() {
		File fileA = new File( input, "mc-core-2.0.55.yaml");
		File fileB = new File( input, "mc-core-2.0.54.yaml");
		File out = new File( output, "mc-core.comparision.yaml");		
		compare( fileA, fileB, out);				
	}
	
	@Test
	public void compareLibraries() {
		File fileA = new File( input, "library-base.yaml");
		File fileB = new File( input, "library-other.yaml");		
		compare( fileA, fileB, new File( output, "library.comparision.yaml"));
	}

	
}
