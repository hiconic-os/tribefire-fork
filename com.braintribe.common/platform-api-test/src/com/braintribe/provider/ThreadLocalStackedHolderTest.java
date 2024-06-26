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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class ThreadLocalStackedHolderTest {

	private ThreadLocalStackedHolder<String> holder = null;

	@Before
	public void initializeHolder() {
		holder = new ThreadLocalStackedHolder<>();
	}

	@Test
	public void testSingleValue() throws Exception {

		String v1 = UUID.randomUUID().toString();
		holder.accept(v1);

		assertThat(holder.get()).isEqualTo(v1);

		holder.accept(null);

		assertThat(holder.get()).isNull();
	}

	@Test
	public void testSingleValueInParallel() throws Exception {

		runInParallel(new Tester() {

			@Override
			protected void test() throws Exception {
				testSingleValue();
			}

		}, 50);

	}

	@Test
	public void testMultipleValues() throws Exception {

		String v1 = UUID.randomUUID().toString();
		String v2 = UUID.randomUUID().toString();
		String v3 = UUID.randomUUID().toString();

		holder.accept(v1);
		assertThat(holder.get()).isEqualTo(v1);
		holder.accept(v2);
		assertThat(holder.get()).isEqualTo(v2);
		holder.accept(v3);
		assertThat(holder.get()).isEqualTo(v3);

		holder.accept(null);
		assertThat(holder.get()).isEqualTo(v2);
		holder.accept(null);
		assertThat(holder.get()).isEqualTo(v1);
		holder.accept(null);
		assertThat(holder.get()).isNull();
	}

	@Test
	public void testMultipleValuesInParallel() throws Exception {

		runInParallel(new Tester() {

			@Override
			protected void test() throws Exception {
				testMultipleValues();
			}

		}, 50);

	}

	@Test
	public void testIdempotence() throws Exception {

		String v1 = UUID.randomUUID().toString();
		String v2 = UUID.randomUUID().toString();
		String v3 = UUID.randomUUID().toString();

		holder.accept(v1);
		assertThat(holder.get()).isEqualTo(v1);
		assertThat(holder.get()).isEqualTo(v1);
		assertThat(holder.get()).isEqualTo(v1);

		holder.accept(v2);
		assertThat(holder.get()).isEqualTo(v2);
		assertThat(holder.get()).isEqualTo(v2);
		assertThat(holder.get()).isEqualTo(v2);

		holder.accept(v3);
		assertThat(holder.get()).isEqualTo(v3);
		assertThat(holder.get()).isEqualTo(v3);
		assertThat(holder.get()).isEqualTo(v3);

		holder.accept(null);
		assertThat(holder.get()).isEqualTo(v2);
		assertThat(holder.get()).isEqualTo(v2);
		assertThat(holder.get()).isEqualTo(v2);

		holder.accept(null);
		assertThat(holder.get()).isEqualTo(v1);
		assertThat(holder.get()).isEqualTo(v1);
		assertThat(holder.get()).isEqualTo(v1);

		holder.accept(null);
		assertThat(holder.get()).isNull();
		assertThat(holder.get()).isNull();
		assertThat(holder.get()).isNull();
	}

	@Test
	public void testIdempotenceInParallel() throws Exception {

		runInParallel(new Tester() {

			@Override
			protected void test() throws Exception {
				testIdempotence();
			}

		}, 50);

	}

	@Test
	public void testLargeStack() throws Exception {

		for (int i = 0; i <= 100_000; i++) {
			holder.accept(String.valueOf(i));
			assertThat(holder.get()).isEqualTo(String.valueOf(i));
		}

		for (int i = 100_000; i >= 0; i--) {
			assertThat(holder.get()).isEqualTo(String.valueOf(i));
			holder.accept(null);
		}

		assertThat(holder.get()).isNull();
	}

	@Test
	public void testLargeStackInParallel() throws Exception {

		runInParallel(new Tester() {

			@Override
			protected void test() throws Exception {
				testLargeStack();
			}

		});

	}

	@Test
	public void testLargeStackIdempotence() throws Exception {

		for (int i = 0; i <= 100_000; i++) {
			holder.accept(String.valueOf(i));
			for (int j = 0; j <= 10; j++) {
				assertThat(holder.get()).isEqualTo(String.valueOf(i));
			}
		}

		for (int i = 100_000; i >= 0; i--) {
			for (int j = 0; j <= 10; j++) {
				assertThat(holder.get()).isEqualTo(String.valueOf(i));
			}
			holder.accept(null);
		}

		for (int j = 0; j <= 10; j++) {
			assertThat(holder.get()).isNull();
		}
	}

	@Test
	public void testLargeStackIdempotenceInParallel() throws Exception {

		runInParallel(new Tester() {

			@Override
			protected void test() throws Exception {
				testLargeStackIdempotence();
			}

		}, 5);

	}

	protected void runInParallel(Tester tester) throws Exception {
		runInParallel(tester, 10);
	}

	protected void runInParallel(Tester tester, int threads) throws Exception {

		List<Tester> tests = new ArrayList<>(threads);
		for (int i = threads; i-- > 0;) {
			tests.add(tester);
		}

		runInParallel(tests, threads);

	}

	protected void runInParallel(List<Tester> tests, int threads) throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(threads);

		try {
			List<Future<Throwable>> results = executorService.invokeAll(tests, 1, TimeUnit.MINUTES);
			List<Throwable> errors = new ArrayList<>();

			for (Future<Throwable> result : results) {
				Throwable error = null;

				try {
					error = result.get();
				} catch (CancellationException e) {
					System.out.println("Test cancelled as it didn't complete in time");
					continue;
				}

				if (error != null) {
					errors.add(error);
				}
			}

			if (errors.isEmpty()) {
				System.out.println(tests.size() + " concurrent tests completed successfully.");
			} else {
				AssertionError error = new AssertionError("From " + tests.size() + " concurrent tests, " + errors.size() + " failed.");
				for (Throwable cause : errors) {
					error.addSuppressed(cause);
				}
				throw error;
			}
		} finally {
			executorService.shutdownNow();
		}

	}

	protected abstract class Tester implements Callable<Throwable> {

		abstract void test() throws Exception;

		@Override
		public Throwable call() throws Exception {
			try {
				test();
				return null;
			} catch (Throwable e) {
				return e;
			}
		}

	}

}
