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
package com.braintribe.logging;


import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.braintribe.common.lcd.Numbers;

public class ThreadRenamerTest {

	@Test
	public void testThreadRenamerSimple() {
		
		ThreadRenamer tr = new ThreadRenamer(true);
		
		final String threadName = UUID.randomUUID().toString();
		final String pushedName = UUID.randomUUID().toString();

		Thread.currentThread().setName(threadName);
		
		tr.push(() -> pushedName);

		String changedName = Thread.currentThread().getName();
		assertThat(changedName).contains(threadName);
		assertThat(changedName).contains(pushedName);
		
		tr.pop();
		
		assertThat(Thread.currentThread().getName()).isEqualTo(threadName);
		
	}
	
	@Test
	public void testThreadRenamerMultithreaded() throws Exception {
		
		final ThreadRenamer tr = new ThreadRenamer(true);

		int worker = 10;
		int iterations = Numbers.THOUSAND;
		
		ExecutorService service = Executors.newFixedThreadPool(worker);
		try {
			
			List<Future<?>> futures = new ArrayList<>(worker);
			for (int i=0; i<worker; ++i) {
				futures.add(service.submit(new Runnable() {

					@Override
					public void run() {
						final String myName = UUID.randomUUID().toString();
						Thread.currentThread().setName(myName);
						for (int j=0; j<iterations; ++j) {
							
							final String pushedName = UUID.randomUUID().toString();
							tr.push(() -> pushedName);
							String changedName = Thread.currentThread().getName();
							assertThat(changedName).contains(myName);
							assertThat(changedName).contains(pushedName);
							tr.pop();
							assertThat(Thread.currentThread().getName()).isEqualTo(myName);
						}
						
						Stack<String> stack = tr.nameStack.get();
						assertThat(stack).isEmpty();

					}
					
				}));
			}
			
			for (Future<?> f : futures) {
				f.get();
			}
			
		} finally {
			service.shutdown();
		}
		
	}
	
	@Test
	public void testThreadRenamerThreadLocal() throws Exception {
		
		ThreadRenamer tr = new ThreadRenamer(true);
		
		final String threadName = UUID.randomUUID().toString();
		final String pushedName = UUID.randomUUID().toString();

		Thread.currentThread().setName(threadName);
		
		tr.push(() -> pushedName);

		String changedName = Thread.currentThread().getName();
		assertThat(changedName).contains(threadName);
		assertThat(changedName).contains(pushedName);
		
		tr.pop();
		
		assertThat(Thread.currentThread().getName()).isEqualTo(threadName);
		
		Stack<String> stack = tr.nameStack.get();
		assertThat(stack).isEmpty();
	}
	
	@Test
	public void testExceptionOnTooManyPops() throws Exception {
		
		ThreadRenamer tr = new ThreadRenamer(true);
		
		final String pushedName = UUID.randomUUID().toString();
		
		tr.push(() -> pushedName);
		
		tr.pop();
		
		try {
			//This is one pop too much... expecting an exception
			tr.pop();
			
			fail("The additional pop() should have thrown an exception");
			
		} catch(EmptyStackException e) {
			//cool
		}
	}
}
