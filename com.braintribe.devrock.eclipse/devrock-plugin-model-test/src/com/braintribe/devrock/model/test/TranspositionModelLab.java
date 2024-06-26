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
package com.braintribe.devrock.model.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.braintribe.codec.marshaller.yaml.YamlMarshaller;
import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.eclipse.model.resolution.AnalysisArtifactResolutionViewerModel;
import com.braintribe.devrock.eclipse.model.storage.TranspositionContext;
import com.braintribe.devrock.model.transposition.resolution.Transposer;
import com.braintribe.devrock.model.transposition.resolution.context.BasicTranspositionContext;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;

public class TranspositionModelLab implements HasCommonFilesystemNode {
	
	protected File input;
	protected File output;
	
	{	
		Pair<File,File> pair = filesystemRoots("transpose");
		input = pair.first;
		output = pair.second;
					
	}

	private YamlMarshaller marshaller = new YamlMarshaller();
	
	
	
	protected AnalysisArtifactResolutionViewerModel testBuild(String resolutionFilename) {
		
		File resolutionInputFile = new File( input, resolutionFilename);
		AnalysisArtifactResolution resolution;
		
		try (InputStream in = new FileInputStream(resolutionInputFile)) {
			resolution = (AnalysisArtifactResolution) marshaller.unmarshall(in);
		}
		catch (Exception e) {
			throw new IllegalStateException("file [" + resolutionInputFile.getAbsolutePath() + "] can't be unmarshalled", e);	
		}
		
		Transposer transposer = new Transposer();
		TranspositionContext context = BasicTranspositionContext.build().includeDependencies(true).includeDependers(true).includeParents(true).done();
		Map<String, TranspositionContext> tcm = new HashMap<>();
		tcm.put( Transposer.CONTEXT_DEFAULT, context);
		
		AnalysisArtifactResolutionViewerModel vm = transposer.from( tcm, resolution);
		
		File resolutionOutputFile = new File( output, resolutionFilename);
		try (OutputStream out = new FileOutputStream( resolutionOutputFile)){
			marshaller.marshall(out, vm);
		}
		catch( Exception e) {
			throw new IllegalStateException("file [" + resolutionOutputFile.getAbsolutePath() + "] can't be marshalled", e);
		}		
		
		return vm;
		
	}
	//@Test
	public void test_unresolvedDependencies() {
		testBuild( "invalid/unresolved-dependency.dump.tdr.yaml");
	}
	//@Test
	public void test_unresolvedDependenciesMetadata() {
		testBuild( "invalid/unresolved-dependency-through-metadata-mismatch.dump.tdr.yaml");
	}
	//@Test
	public void test_unresolvedImport() {
		testBuild( "invalid/unresolved-import.dump.tdr.yaml");
	}
	//@Test
	public void test_unresolvedParent() {
		testBuild( "invalid/unresolved-parent.dump.tdr.yaml");
	}
	//@Test
	public void test_unresolvedRedirection() {
		testBuild( "invalid/unresolved-redirection.dump.tdr.yaml");
	}
	//@Test
	public void test_mc_core() {
		testBuild( "mc-core.cpr.dump.yaml");
	}
	@Test
	public void test_parents() {
		AnalysisArtifactResolutionViewerModel viewerModel = testBuild( "com.braintribe.devrock.test.t#1.0.1-enriched.yaml");
		System.out.println(viewerModel);
	}
	
}
