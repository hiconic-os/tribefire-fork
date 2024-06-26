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
package com.braintribe.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class ThreadLocalHolderTest {

	@Test
	public void simpleTest() {

		ThreadLocalHolder<String> holder = new ThreadLocalHolder<>();

		String value = UUID.randomUUID().toString();

		assertThat(holder.get()).isNull();

		holder.accept(value);

		assertThat(holder.get()).isEqualTo(value);

		holder.accept(null);

		assertThat(holder.get()).isNull();

		holder.accept(value);

		assertThat(holder.get()).isEqualTo(value);

		holder.release();

		assertThat(holder.get()).isNull();

	}

	@Test
	public void testMultithreaded() throws Exception {

		int workers = 10;
		int iterations = 100;
		ThreadLocalHolder<String> holder = new ThreadLocalHolder<>();

		ExecutorService service = Executors.newFixedThreadPool(workers);
		try {

			List<Future<?>> futures = new ArrayList<>();
			for (int i = 0; i < workers; ++i) {
				futures.add(service.submit(() -> {
					for (int j = 0; j < iterations; ++j) {

						String value = UUID.randomUUID().toString();
						holder.accept(value);
						try {
							Thread.sleep(1L);
						} catch (InterruptedException e) {
							return;
						}
						assertThat(holder.get()).isEqualTo(value);

					}
					holder.release();
				}));
			}

			for (Future<?> f : futures) {
				f.get();
			}

		} finally {
			service.shutdown();
		}

	}
}
