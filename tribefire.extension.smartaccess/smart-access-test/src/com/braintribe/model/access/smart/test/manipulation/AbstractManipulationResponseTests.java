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
package com.braintribe.model.access.smart.test.manipulation;

import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.model.access.ModelAccessException;
import com.braintribe.model.access.smart.test.base.AccessSetup;
import com.braintribe.model.access.smood.basic.SmoodAccess;
import com.braintribe.model.accessapi.ManipulationRequest;
import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup;
import com.braintribe.model.processing.test.tools.meta.ManipulationDriver;
import com.braintribe.model.processing.test.tools.meta.ManipulationDriver.SessionRunnable;

/**
 * 
 * @author peter.gazdik
 */
public class AbstractManipulationResponseTests extends AbstractManipulationsTests {

	protected ManipulationResponse configuredResponseA;
	protected ManipulationResponse configuredResponseB;

	@Override
	protected AccessSetup newAccessSetup() {
		return new SpecialAccessSetup(getSmartSetupProvider().setup());
	}

	class SpecialAccessSetup extends AccessSetup {

		public SpecialAccessSetup(SmartMappingSetup setup) {
			super(setup);
		}

		@Override
		protected SmoodAccess newSmoodAccess(final String... _partitions) {
			return new SmoodAccess() {

				@Override
				public synchronized ManipulationResponse applyManipulation(ManipulationRequest manipulationRequest)
						throws ModelAccessException {

					ManipulationResponse response = super.applyManipulation(manipulationRequest);
					ManipulationResponse configuredResponse = _partitions[0].endsWith("A") ? configuredResponseA : configuredResponseB;

					return configuredResponse == null ? response : configuredResponse;
				}

			};
		}
	}

	protected void recordResponseA(SessionRunnable r) {
		configuredResponseA = recordResponse(setup.getAccessA(), r);
	}

	protected void recordResponseB(SessionRunnable r) {
		configuredResponseB = recordResponse(setup.getAccessB(), r);
	}

	private ManipulationResponse recordResponse(IncrementalAccess access, SessionRunnable r) {
		ManipulationDriver driver = new ManipulationDriver(access);
		Manipulation m = driver.dryRun(r);

		ManipulationResponse result = ManipulationResponse.T.create();
		result.setInducedManipulation(m);

		return result;
	}

}
