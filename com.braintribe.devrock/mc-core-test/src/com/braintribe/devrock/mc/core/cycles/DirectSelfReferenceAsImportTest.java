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
package com.braintribe.devrock.mc.core.cycles;

import java.io.File;

import org.junit.Assert;

import com.braintribe.devrock.mc.api.resolver.CompiledArtifactResolver;
import com.braintribe.devrock.mc.core.wirings.resolver.contract.ArtifactDataResolverContract;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.artifact.compiled.CompiledArtifact;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;

public class DirectSelfReferenceAsImportTest extends AbstractCycleTest {

	
	@Override
	protected File archiveContentDirectory() {
		return new File( input, "directSelfReferenceAsImport");
	}

	@Override
	protected void runTest(ArtifactDataResolverContract contract) {	
		boolean exceptionThrown = false;
		boolean markedAsInvalid = false;
		try {
			CompiledArtifactResolver compiledArtifactResolver = contract.redirectAwareCompiledArtifactResolver();
			Maybe<CompiledArtifact> resolved = compiledArtifactResolver.resolve( CompiledArtifactIdentification.parse(TERMINAL));
			if (resolved.isUnsatisfied()) {
				System.out.println( "nothing's found");
			}
			if (resolved.get().getInvalid()) {
				markedAsInvalid = true;
			}
		}
		catch (Exception e) {
			exceptionThrown = true;
			System.out.println("as expected [" + e.getMessage() + "] thrown");
		}
		Assert.assertTrue("expected exception not thrown", exceptionThrown || markedAsInvalid);
	}
	
}
