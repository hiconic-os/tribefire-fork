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
package com.braintribe.devrock.mc.core.wired.resolving.transitive;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.braintribe.devrock.mc.api.transitive.TransitiveResolutionContext;
import com.braintribe.devrock.mc.core.wired.resolving.Validator;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;

public class SolutionOrderTest extends AbstractTransitiveResolverTest {

	@Override
	protected File archiveInput() {
		return new File( input, "sortorder.definition.yaml");
	}

	private AnalysisArtifact generate( String name, int visitOrder, int dependencyOrder) {
		AnalysisArtifact artifact = AnalysisArtifact.T.create();
		artifact.setGroupId("com.braintribe.devrock.test");
		artifact.setArtifactId(name);
		artifact.setVersion( "1.0.1");
		artifact.setVisitOrder(visitOrder);
		artifact.setDependencyOrder(dependencyOrder);
		return artifact;
	}
	
	 @Test
	 public void runSolutionOrderingTest() {
		 
		 List<AnalysisArtifact> expectedSolutions = new ArrayList<>();
		 expectedSolutions.add( generate("t", 0, 6));
		 expectedSolutions.add( generate("a", 1, 5));
		 expectedSolutions.add( generate("b", 2, 4));
		 expectedSolutions.add( generate("c", 3, 1));
		 
		 expectedSolutions.add( generate("d", 5, 3));
		 expectedSolutions.add( generate("e", 4, 0));
		 
		 expectedSolutions.add( generate("f", 6, 2));
		 
		 AnalysisArtifactResolution resolution = run("com.braintribe.devrock.test:t#1.0.1", TransitiveResolutionContext.build().done());
		 
		 Validator validator = new Validator();
		 
		 validator.validate( new File( input,  "sortorder.definition.yaml"), resolution);
		 				 
		 validator.validateSolutionOrdering(resolution.getSolutions(), expectedSolutions);
		 validator.assertResults(); 
	 }
	
}
