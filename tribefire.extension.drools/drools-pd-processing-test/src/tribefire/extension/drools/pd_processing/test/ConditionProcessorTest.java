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
package tribefire.extension.drools.pd_processing.test;

import org.junit.Test;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.condition.impl.BasicConditionProcessorContext;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;
import com.braintribe.utils.lcd.StopWatch;

import tribefire.extension.drools.model.test.TestProcess;
import tribefire.extension.drools.pe.DroolsConditionProcessor;
import tribefire.extension.drools.pe.DroolsTransitionProcessor;

public class ConditionProcessorTest extends DroolsPdProcessingTestBase {

	@Test
	public void testConditionProcessor() {
		StopWatch stopWatch = new StopWatch();
		DroolsConditionProcessor conditionProcessor = testContract.conditionProcessor();
		
		System.out.println("condition processor instantiation in " + stopWatch.getElapsedTime() + "ms");
		
		stopWatch = new StopWatch();
		DroolsTransitionProcessor transitionProcessor = testContract.transitionProcessor();
		
		System.out.println("transition processor instantiation in " + stopWatch.getElapsedTime() + "ms");
		
		PersistenceGmSession session = testContract.sessionFactory().newSession("access.test");
		
		TestProcess testProcess1 = session.query().entity(TestProcess.T, 1L).require();
		TestProcess testProcess2 = session.query().entity(TestProcess.T, 2L).require();

		BasicConditionProcessorContext<GenericEntity> context1 = new BasicConditionProcessorContext<>(session, session, testProcess1, testProcess1);
		BasicConditionProcessorContext<GenericEntity> context2 = new BasicConditionProcessorContext<>(session, session, testProcess2, testProcess2);
		
		Assertions.assertThat(conditionProcessor.matches(context1)).isTrue();
		Assertions.assertThat(conditionProcessor.matches(context2)).isFalse();
	}
}
