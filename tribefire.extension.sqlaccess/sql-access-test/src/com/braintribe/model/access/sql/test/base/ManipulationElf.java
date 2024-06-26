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
package com.braintribe.model.access.sql.test.base;

import static com.braintribe.model.processing.manipulation.basic.tools.ManipulationTools.asManipulation;
import static com.braintribe.model.processing.manipulation.basic.tools.ManipulationTools.asManipulationRequest;

import java.util.List;
import java.util.function.Consumer;

import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.model.accessapi.ManipulationRequest;
import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.generic.manipulation.AtomicManipulation;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.testing.tools.gm.meta.ManipulationRecorder;

/**
 * TODO extract as common tool once it's mature enough.
 * 
 * @author peter.gazdik
 */
public class ManipulationElf {

	protected final IncrementalAccess access;
	protected final ManipulationRecorder manipulationRecorder;

	protected List<AtomicManipulation> recordedManipulations;
	protected ManipulationResponse response;

	public ManipulationElf(IncrementalAccess access) {
		this.access = access;
		this.manipulationRecorder = new ManipulationRecorder().withAccess(access).persistent();
	}

	public ManipulationRecorder getManipulationRecorder() {
		return manipulationRecorder;
	}

	public List<AtomicManipulation> getRecordedManipulations() {
		return recordedManipulations;
	}

	public ManipulationResponse getResponse() {
		return response;
	}

	// ###########################################
	// ## . . . . . . . Record/Apply . . . . . .##
	// ###########################################

	public void record(Consumer<PersistenceGmSession> r) {
		recordedManipulations = manipulationRecorder.record(r).asAtomicManipulations();
	}

	public ManipulationResponse apply(Consumer<PersistenceGmSession> r) {
		record(r);
		return apply();
	}

	public ManipulationResponse apply() {
		ManipulationRequest manipulationRequest = asManipulationRequest(asManipulation(recordedManipulations));
		return response = access.applyManipulation(manipulationRequest);
	}

}
