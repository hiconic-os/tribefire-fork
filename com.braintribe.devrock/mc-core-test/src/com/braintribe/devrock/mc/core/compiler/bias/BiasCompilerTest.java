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
package com.braintribe.devrock.mc.core.compiler.bias;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.mc.core.commons.ManagedFilesystemLockSupplier;
import com.braintribe.devrock.mc.core.commons.test.HasCommonFilesystemNode;
import com.braintribe.devrock.mc.core.configuration.bias.PcBiasCompiler;
import com.braintribe.devrock.mc.core.filters.ArtifactFilterExpert;
import com.braintribe.devrock.mc.core.filters.ArtifactFilters;
import com.braintribe.devrock.model.repository.filters.ArtifactFilter;
import com.braintribe.model.artifact.essential.ArtifactIdentification;
import com.braintribe.testing.category.KnownIssue;

/**
 * tests the {@link PcBiasCompiler} 
 * 
 * @author pit
 *
 */
@Category( KnownIssue.class)
public class BiasCompilerTest implements HasCommonFilesystemNode {
	
	private File input;
	private File output;
	
	{
		Pair<File, File> pair = filesystemRoots( "compiler/bias");
		input = pair.first;
		output = pair.second;		
	}
	
	// dominance filter : true if repo is dominant
	// block filter - actually pass filter.. 
	private Map<String, Pair<Map<ArtifactIdentification, Boolean>, Map<ArtifactIdentification, Boolean>>> bias_one_expectations = new HashMap<>();
	{
		
		Map<ArtifactIdentification, Boolean> dominanceMap = new HashMap<>();
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "mc-core"), true);
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "malaclypse"), true);
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.gm", "version-model"), false);
		
		Map<ArtifactIdentification, Boolean> filterMap = new HashMap<>();
		filterMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "mc-core"), true);		
		filterMap.put( ArtifactIdentification.create( "com.braintribe.gm", "malaclypse"), true);
		filterMap.put( ArtifactIdentification.create( "com.braintribe.gm", "version-model"), true);
		
		bias_one_expectations.put( "local", Pair.of( dominanceMap, filterMap));	
	}

	private Map<String, Pair<Map<ArtifactIdentification, Boolean>, Map<ArtifactIdentification, Boolean>>> bias_two_expectations = new HashMap<>();
	{
		// local : dominance
		Map<ArtifactIdentification, Boolean> dominanceMap = new HashMap<>();
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "malaclypse"), true);
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "mc-core"), false);
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.gm", "version-model"), true);
		
		Map<ArtifactIdentification, Boolean> filterMap = new HashMap<>();
		filterMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "malaclypse"), true);
		bias_two_expectations.put( "local", Pair.of( dominanceMap, filterMap));
		
		
		// remote 
		dominanceMap = new HashMap<>();
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "malaclypse"), false);
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "mc-core"), false);
		dominanceMap.put( ArtifactIdentification.create( "com.braintribe.gm", "version-model"), false);
		
		
		filterMap = new HashMap<>();
		filterMap.put( ArtifactIdentification.create( "com.braintribe.gm", "version-model"), false);
		filterMap.put( ArtifactIdentification.create( "com.braintribe.gm", "gm-core-api"), false);
		filterMap.put( ArtifactIdentification.create( "com.braintribe.devrock", "malaclypse"), true);
		
		bias_two_expectations.put( "remote", Pair.of( dominanceMap, filterMap));
		
	}
	

	private PcBiasCompiler test( File file) {
		PcBiasCompiler compiler = new PcBiasCompiler();
		compiler.setLocalRepository( input);
		compiler.setLockSupplier( new ManagedFilesystemLockSupplier());		
		
		try {
			compiler.loadPcBias( file);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail( "exception thrown");
		}
		return compiler;
	}
	

	/**
	 * validate compiler's result 
	 * @param compiler - the compiler with its data loaded 
	 * @param expectations - the matching expectations
	 */
	private void validate( PcBiasCompiler compiler, Map<String, Pair<Map<ArtifactIdentification, Boolean>, Map<ArtifactIdentification, Boolean>>> expectations) {
		for (Map.Entry<String, Pair<Map<ArtifactIdentification, Boolean>, Map<ArtifactIdentification, Boolean>>> entry : expectations.entrySet()) {
			String repoKey = entry.getKey();
			Pair<Map<ArtifactIdentification, Boolean>, Map<ArtifactIdentification, Boolean>> expectedResults = entry.getValue();
			
			Pair<ArtifactFilter,ArtifactFilter> biasFilters = compiler.getBiasFilters(repoKey);
			ArtifactFilterExpert dominanceArtifactFilter = ArtifactFilters.forDenotation(biasFilters.first);
			
			ArtifactFilterExpert blockFilter = ArtifactFilters.forDenotation(biasFilters.second);
						
			validate( "dominance", repoKey, dominanceArtifactFilter, expectedResults.first);
			
			validate( "block", repoKey, blockFilter, expectedResults.second);						
		}
	}

	/**
	 * validates a filter 
	 * @param tag - the filter type 
	 * @param artifactFilter - the {@link ArtifactFilterExpert} as constructed 
	 * @param expectation - the expectation
	 */
	private void validate(String tag, String repo, ArtifactFilterExpert artifactFilter, Map<ArtifactIdentification, Boolean> expectation) {
		for (Map.Entry< ArtifactIdentification, Boolean> entry : expectation.entrySet()) {
			boolean matches = artifactFilter.matches( entry.getKey());
			Assert.assertTrue("expected [" + tag + "]-filter attached to repository [" + repo + "] result for [" + entry.getKey().asString() + "] to be [" + entry.getValue() + "], yet it's [" + matches + "]", entry.getValue().equals( matches));
		}
		
	}
	
	/**
	 * tests simplest (one entry) local bias
	 */
	@Test
	public void testOne() {
		PcBiasCompiler compiler = test( new File( input, "pc_bias.one.txt"));	
		validate(compiler, bias_one_expectations);
	}
	
	@Test
	public void testTwo() {
		PcBiasCompiler compiler = test( new File( input, "pc_bias.two.txt"));	
		validate(compiler, bias_two_expectations);
	}
}
