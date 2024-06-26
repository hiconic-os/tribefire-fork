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
package com.braintribe.model.processing.cmd.index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.braintribe.model.processing.index.ConcurrentCachedIndex;
import com.braintribe.utils.junit.core.rules.Concurrent;
import com.braintribe.utils.junit.core.rules.ConcurrentRule;

/**
 * Tests the cached index works correctly even if shared by multiple threads. The concurrency, however, is not tested
 * directly.
 */
public class ConcurrentCachedIndexTests {

	@Rule
	public ConcurrentRule concurrentRule = new ConcurrentRule(1);

	/**
	 * This test just shows that working with empty lists in the next test makes sense, since they are considered equal
	 * even if they are all different instances.
	 */
	@Test
	public void showListsAreEqual() {
		List<?> list = new ArrayList<Object>();
		List<?> list2 = new ArrayList<Object>();

		ConcurrentHashMap<List<?>, List<?>> chm = new ConcurrentHashMap<List<?>, List<?>>();
		chm.put(list, list);

		Assert.assertTrue(list == chm.putIfAbsent(list2, list2));
	}

	private final CountDownLatch cdl = new CountDownLatch(50);
	private final TestCache cache = new TestCache();
	private final Map<Thread, String> map = new ConcurrentHashMap<Thread, String>();

	/**
	 * This tests whether the same value is cached for 50 different (but equivalent) instances. If everything works as
	 * it should, then the {@link TestCache#provideValueFor(List)} method should only be called once.
	 */
	@Test
	@Concurrent(50)
	public void happyPath() {
		cacheValueForCurrentThread();

		cdl.countDown();
		if (cdl.getCount() == 0) {
			/* this may be called more than once, but that is not a problem as long as the test should passes (In case
			 * there is a bug we might see the same error being printed multiple times) */
			checkCacheContainsOnlyOneUniqueValue();
		}
	}

	private void cacheValueForCurrentThread() {
		map.put(Thread.currentThread(), cache.acquireFor(new ArrayList<Object>()));
	}

	private void checkCacheContainsOnlyOneUniqueValue() {
		Assert.assertEquals("Wrong number of map entries", 50, map.size());

		Set<String> values = new HashSet<String>(map.values());
		Assert.assertEquals("Wrong number of set values", 1, values.size());
	}

	private static class TestCache extends ConcurrentCachedIndex<List<?>, String> {

		@Override
		protected String provideValueFor(List<?> key) {
			return Thread.currentThread().getName();
		}

	}
}
