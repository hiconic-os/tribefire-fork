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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.extension.cache.model.service.demo.CacheDemo;
import tribefire.extension.cache.model.service.demo.CacheDemoResult;
import tribefire.extension.cache.test.wire.CacheTestWireModule;
import tribefire.extension.cache.test.wire.contract.CacheTestContract;

//TODO: maybe delete it?
public class CacheTest extends AbstractCacheTest<CacheTestContract> {

	private static final String DEFAULT_TEST_DOMAIN = "test.domain2";

	@Override
	protected WireTerminalModule<CacheTestContract> module() {
		return CacheTestWireModule.INSTANCE;
	}

	@Before
	public void init() {
		// nothing so far
	}

	@Test
	@Ignore
	public void todoTest() {

		CacheDemo request = CacheDemo.T.create();

		Property property = CacheDemo.T.getProperty(CacheDemo.resultValue);
		property.setAbsenceInformation(request, GMF.absenceInformation());

		CacheDemoResult cacheDemoResult = request.eval(evaluator).get();

		System.out.println(cacheDemoResult);
	}

	@Test
	@Ignore
	public void test() {

	}

	// TODO: request with object
	// TODO: request empty
	// TODO: request with absence information

}
