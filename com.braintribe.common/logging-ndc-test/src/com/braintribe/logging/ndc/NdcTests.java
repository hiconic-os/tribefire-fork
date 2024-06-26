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
package com.braintribe.logging.ndc;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.logging.Logger;
import com.braintribe.logging.ndc.mbean.NestedDiagnosticContext;

public class NdcTests {

	@Test
	public void testNdcSingleThreadSingleElement() {

		Logger logger = Logger.getLogger(NdcTests.class);
		logger.removeContext();

		logger.pushContext("test");

		Deque<String> ndc = NestedDiagnosticContext.getNdc();
		Assert.assertNotNull(ndc);
		Assert.assertEquals(1, ndc.size());
		Assert.assertEquals("test", ndc.getFirst());
	}

	@Test
	public void testNdcSingleThreadMultipleElements() {

		Logger logger = Logger.getLogger(NdcTests.class);
		logger.removeContext();

		logger.pushContext("test1");
		logger.pushContext("test2");
		logger.pushContext("test3");

		Deque<String> ndc = NestedDiagnosticContext.getNdc();
		Assert.assertNotNull(ndc);
		Assert.assertEquals(3, ndc.size());

		Iterator<String> it = ndc.descendingIterator();
		for (int i=1; i<=3; ++i) {
			Assert.assertEquals("test"+i, it.next());			
		}

		logger.popContext();
		it = ndc.descendingIterator();
		for (int i=1; i<=2; ++i) {
			Assert.assertEquals("test"+i, it.next());			
		}
	}

	@Test
	public void testNdcSingleThreadRemoveContext() {

		Logger logger = Logger.getLogger(NdcTests.class);
		logger.removeContext();

		logger.pushContext("test");

		Deque<String> ndc = NestedDiagnosticContext.getNdc();
		Assert.assertNotNull(ndc);
		Assert.assertEquals(1, ndc.size());
		Assert.assertEquals("test", ndc.getFirst());

		logger.removeContext();

		ndc = NestedDiagnosticContext.getNdc();
		Assert.assertNull(ndc);
	}


	@Test
	public void testNdcSingleThreadRemoveByPop() {

		Logger logger = Logger.getLogger(NdcTests.class);
		logger.removeContext();

		logger.pushContext("test");

		Deque<String> ndc = NestedDiagnosticContext.getNdc();
		Assert.assertNotNull(ndc);
		Assert.assertEquals(1, ndc.size());
		Assert.assertEquals("test", ndc.getFirst());

		logger.popContext();

		ndc = NestedDiagnosticContext.getNdc();
		Assert.assertNull(ndc);
	}

	@Test
	public void testNdcMultipleThreadsMultipleElements() throws Exception {

		Callable<Boolean> c = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {

				Logger logger = Logger.getLogger(NdcTests.class);
				logger.removeContext();

				SecureRandom rnd = new SecureRandom();
				
				try {
					for (int i=0; i<10000; ++i) {
						
						int r = rnd.nextInt();
						
						logger.pushContext(""+r);
						Deque<String> ndc = NestedDiagnosticContext.getNdc();
						Assert.assertNotNull(ndc);
						Assert.assertEquals(1, ndc.size());
						Assert.assertEquals(""+r, ndc.getFirst());
						logger.popContext();

					}
				} catch(Throwable t) {
					t.printStackTrace();
					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}
		};
		
		int workerCount = 50;
		ExecutorService service = Executors.newFixedThreadPool(workerCount);
		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		
		for (int i=0; i<workerCount; ++i) {
			futures.add(service.submit(c));
		}
		boolean allDone = false;
		while (!allDone) {
			allDone = true;
			for (Future<Boolean> f : futures) {
				if (!f.isDone()) {
					allDone = false;
					break;
				} else if (f.get() == false) {
					Assert.fail("One of the threads failed.");
				}
			}			
		}
		
		service.shutdown();

	}
}
