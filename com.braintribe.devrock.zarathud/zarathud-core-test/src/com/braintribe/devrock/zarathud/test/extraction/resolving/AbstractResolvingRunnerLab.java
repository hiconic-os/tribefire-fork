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
package com.braintribe.devrock.zarathud.test.extraction.resolving;

import java.util.Map;

import org.junit.Assert;
import org.junit.experimental.categories.Category;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.zarathud.model.ResolvingRunnerContext;
import com.braintribe.devrock.zarathud.runner.api.ZedWireRunner;
import com.braintribe.devrock.zarathud.runner.commons.ClasspathResolvingUtil;
import com.braintribe.devrock.zarathud.runner.wire.ZedRunnerWireTerminalModule;
import com.braintribe.devrock.zarathud.runner.wire.contract.ZedRunnerContract;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.logging.Logger;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.compiled.CompiledDependencyIdentification;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;

@Category(KnownIssue.class)
public abstract class AbstractResolvingRunnerLab {
	private static Logger log = Logger.getLogger(AbstractResolvingRunnerLab.class);

	public Pair<ForensicsRating, Map<FingerPrint, ForensicsRating>> test(String terminal) {
		
		CompiledDependencyIdentification cdi = CompiledDependencyIdentification.parse(terminal);
		
		Maybe<CompiledArtifactIdentification> caiMaybe = ClasspathResolvingUtil.resolve(cdi, null);
		
		if ( caiMaybe.isUnsatisfied()) {
			String msg = "cannot resolve [" + terminal + "]: " + caiMaybe.whyUnsatisfied().stringify();
			log.error( msg);
			Assert.fail( msg);
		}
		CompiledArtifactIdentification cai = caiMaybe.get();
		WireContext<ZedRunnerContract> wireContext = Wire.context( ZedRunnerWireTerminalModule.INSTANCE);
		
		ResolvingRunnerContext rrc = ResolvingRunnerContext.T.create();
		rrc.setTerminal( cai.asString());
		rrc.setConsoleOutputVerbosity( com.braintribe.devrock.zarathud.model.context.ConsoleOutputVerbosity.verbose);
		
		ZedWireRunner zedWireRunner = wireContext.contract().resolvingRunner( rrc);
		
		return zedWireRunner.run().get();
	}	
		
	
}
