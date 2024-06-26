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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.reasoning;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.devrock.mc.core.wired.resolving.Validator;
import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.model.artifact.analysis.AnalysisTerminal;

/**
 * tests whether the reasons of a failing terminal also shows-up in the 
 * resolution's failure.  
 * 
 * @author pit
 *
 */
public class TerminalDependencyReasoningTest extends AbstractReasoningTest {

	private static final String terminal = "com.braintribe.devrock.test:t#1.0.1";
	private List<String> expectedIncompleteArtifacts = new ArrayList<>();
	{
		expectedIncompleteArtifacts.add( terminal);
	}
	private List<String> expectedUnresolvedDependencies = new ArrayList<>();
	{
		expectedUnresolvedDependencies.add( "com.braintribe.devrock.test:missing#[1.0,1.1)/:jar");
	}

	@Override
	protected RepoletContent archiveInput() {		
		return archiveInput( "unresolved.terminal.dependency.definition.yaml");
	}
	
	@Test
	public void terminalReasoningTest() {
		try {
			AnalysisArtifactResolution resolution = run( terminal, standardTransitiveResolutionContext, true);
			AnalysisTerminal analysisTerminal = resolution.getTerminals().get(0);
			if (analysisTerminal instanceof AnalysisArtifact == false) {
				Assert.fail("unexpectedly, the terminal is not an AnalysisArtifact");
				return;
			}
			AnalysisArtifact aa = (AnalysisArtifact) analysisTerminal;

			Validator validator = new Validator();
			validator.validateFailedResolution(resolution, expectedIncompleteArtifacts, expectedUnresolvedDependencies);			

			// two different main reasons, but the causes must be identical (otherwise the transfer doesn't work properly)
			Reason resolutionFailure = resolution.getFailure();			
			Reason terminalFailure = aa.getFailure();
			validator.validateReasons( resolutionFailure.getReasons().get(0).getReasons(), terminalFailure.getReasons());
						
 
			
			validator.assertResults();
			if (dumpResults) {
				dump(new File( output, "unresolved-terminal-dependency.dump.tdr.yaml"), resolution);
			}
		} catch (Exception e) {
			Assert.fail("unexpectedly, the TDR did thrown an exception - if dependency is missing");
		}
	}

}
