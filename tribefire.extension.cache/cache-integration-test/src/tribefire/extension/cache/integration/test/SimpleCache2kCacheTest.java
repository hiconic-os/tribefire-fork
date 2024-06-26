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
package tribefire.extension.cache.integration.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.Before;
import org.junit.Test;

import tribefire.extension.cache.model.service.admin.CacheStatusResult;
import tribefire.extension.cache.model.service.demo.CacheDemo;
import tribefire.extension.cache.model.status.ContextualizedCacheAspectStatus;
import tribefire.extension.cache.model.status.cache2k.Cache2kCacheAspectStatus;

public class SimpleCache2kCacheTest extends AbstractCacheTest {

	// -----------------------------------------------------------------------
	// TEST - SETUP / TEARDOWN
	// -----------------------------------------------------------------------

	@Before
	@Override
	public void before() throws Exception {
		super.before();

		deleteAll();
		testCacheAspect();
		testCacheAdminServiceProcessor();
		testCacheDemoProcessor();
	}

	// -----------------------------------------------------------------------
	// TESTS
	// -----------------------------------------------------------------------

	@Test
	public void testCacheStatus() {
		CacheStatusResult cacheStatus = cacheStatus();
		ContextualizedCacheAspectStatus statusBefore = cacheStatus.getStatusSet().iterator().next();
		assertThat(statusBefore).isNotNull();
		assertThat(statusBefore.getCacheAspectExternalId()).isEqualTo("cache.test.aspect");
		assertThat(statusBefore.getInstanceId()).isNotNull();
		assertThat(statusBefore.getStatus()).isNotNull();
		assertThat(Cache2kCacheAspectStatus.T.isAssignableFrom(statusBefore.getStatus().entityType()));
		assertThat(((Cache2kCacheAspectStatus) statusBefore.getStatus()).getName()).isEqualTo("TestCacheAspect_cache.test.aspect");
		assertThat(((Cache2kCacheAspectStatus) statusBefore.getStatus()).getSize()).isEqualTo(0);

		CacheDemo request = CacheDemo.T.create();
		request.eval(cortexSession).get();

		Awaitility.await().atMost(5, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).until(() -> {
			return ((Cache2kCacheAspectStatus) cacheStatus().getStatusSet().iterator().next().getStatus()).getSize() == 1;
		});

	}

	@Test
	public void testCache1() {

		Awaitility.await().atMost(5, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).until(() -> {
			return ((Cache2kCacheAspectStatus) cacheStatus().getStatusSet().iterator().next().getStatus()).getSize() == 0;
		});

		CacheDemo request = CacheDemo.T.create();
		request.eval(cortexSession).get();

		Awaitility.await().atMost(5, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).until(() -> {
			return ((Cache2kCacheAspectStatus) cacheStatus().getStatusSet().iterator().next().getStatus()).getSize() == 1;
		});

		request.eval(cortexSession).get();

		Awaitility.await().atMost(5, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).until(() -> {
			return ((Cache2kCacheAspectStatus) cacheStatus().getStatusSet().iterator().next().getStatus()).getSize() == 1;
		});
	}

}
