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
package tribefire.extension.cache.test;

import org.junit.After;
import org.junit.Before;

import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.extension.cache.test.wire.contract.AbstractCacheTestContract;

public abstract class AbstractCacheTest<S extends AbstractCacheTestContract> {

	public static final String URLENCODED = "application/x-www-form-urlencoded";
	public static final String MULTIPART_FORM_DATA = "multipart/form-data";
	public static final String APPLICATION_JSON = "application/json";

	protected abstract WireTerminalModule<S> module();

	protected WireContext<S> wireContext;
	protected S contract;
	protected Evaluator<ServiceRequest> evaluator;

	@Before
	public void initTest() {
		wireContext = Wire.context(module());
		contract = wireContext.contract();
		evaluator = contract.evaluator();
	}

	@After
	public void closeTest() {
		wireContext.shutdown();
	}

}
