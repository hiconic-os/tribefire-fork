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
package com.braintribe.collections;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.collections.EvictingConcurrentHashMap.KeyWithTimestamp;
import com.braintribe.testing.category.Slow;

public class EvictingConcurrentHashMapTest {

	@Test
	public void simpleTest() {

		EvictingConcurrentHashMap<String, String> map = new EvictingConcurrentHashMap<>(k -> false, true);

		map.put("hello", "world");

		assertThat(map.size()).isEqualTo(1);
		assertThat(map.get("hello")).isEqualTo("world");

		map.remove("hello");

		assertThat(map.size()).isEqualTo(0);
		assertThat(map.get("hello")).isNull();
	}

	@Test
	public void sizeThresholdTest() {

		EvictingConcurrentHashMap<String, String> map = new EvictingConcurrentHashMap<>(k -> true, true);
		map.setEvictionThreshold(5);

		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");

		assertThat(map.size()).isEqualTo(5);

		map.put("6", "6");

		assertThat(map.size()).isEqualTo(1);
	}

	@Test
	@Category(Slow.class)
	public void sizeEvictionIntervalTest() throws Exception {

		EvictingConcurrentHashMap<String, String> map = new EvictingConcurrentHashMap<>(k -> true, true);
		map.setEvictionInterval(2000L);

		for (int i = 0; i < 100; ++i) {
			map.put("" + i, "" + i);
		}
		assertThat(map.size()).isEqualTo(100);

		Thread.sleep(10_000L);

		map.put("hello", "world");

		assertThat(map.size()).isEqualTo(1);
	}

	@Test
	@Category(Slow.class)
	public void keyWithTimestampEvictionTest() throws Exception {

		// Evict everything older than a second.
		EvictingConcurrentHashMap<KeyWithTimestamp<String>, String> map = new EvictingConcurrentHashMap<>(
				k -> (System.currentTimeMillis() - k.getKey().getTimestamp()) > 2000L, true);
		map.setEvictionThreshold(1); // Try to evict every time

		for (int i = 0; i < 100; ++i) {
			map.put(new KeyWithTimestamp<>("" + i), "" + i);
		}
		assertThat(map.size()).isEqualTo(100);

		Thread.sleep(10_000L);

		map.put(new KeyWithTimestamp<>("hello"), "world");

		assertThat(map.size()).isEqualTo(1);
	}
}
