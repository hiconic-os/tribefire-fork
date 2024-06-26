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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.logging.Logger;
import com.braintribe.logging.ndc.mbean.NestedDiagnosticContext;

public class MdcTests {

	@Test
	public void testMdcSingleThreadSingleElement() {

		Logger logger = Logger.getLogger(MdcTests.class);
		logger.clearMdc();

		logger.put("test", "helloworld");

		Map<String,String> mdc = NestedDiagnosticContext.getMdc();
		Assert.assertNotNull(mdc);
		Assert.assertEquals(1, mdc.size());
		Assert.assertEquals("helloworld", mdc.get("test"));
	}

	@Test
	public void testMdcSingleThreadMultipleElements() {

		Logger logger = Logger.getLogger(MdcTests.class);
		logger.clearMdc();

		logger.put("test1", "1");
		logger.put("test2", "2");
		logger.put("test3", "3");
		
		Map<String,String> mdc = NestedDiagnosticContext.getMdc();
		Assert.assertNotNull(mdc);
		Assert.assertEquals(3, mdc.size());

		for (int i=1; i<=3; ++i) {
			Assert.assertEquals(""+i, mdc.get("test"+i));			
		}

		logger.remove("test3");
		
		for (int i=1; i<=2; ++i) {
			Assert.assertEquals(""+i, mdc.get("test"+i));			
		}
	}

	@Test
	public void testMdcSingleThreadRemoveContext() {

		Logger logger = Logger.getLogger(MdcTests.class);
		logger.clearMdc();

		logger.put("test", "helloworld");

		Map<String,String> mdc = NestedDiagnosticContext.getMdc();
		Assert.assertNotNull(mdc);
		Assert.assertEquals(1, mdc.size());
		Assert.assertEquals("helloworld", mdc.get("test"));

		logger.clearMdc();

		mdc = NestedDiagnosticContext.getMdc();
		Assert.assertNull(mdc);
	}


	@Test
	public void testMdcSingleThreadRemoveByPop() {

		Logger logger = Logger.getLogger(MdcTests.class);
		logger.clearMdc();

		logger.put("test", "helloworld");

		Map<String,String> mdc = NestedDiagnosticContext.getMdc();
		Assert.assertNotNull(mdc);
		Assert.assertEquals(1, mdc.size());
		Assert.assertEquals("helloworld", mdc.get("test"));

		logger.remove("test");

		mdc = NestedDiagnosticContext.getMdc();
		Assert.assertNull(mdc);
	}

	@Test
	public void testMdcMultipleThreadsMultipleElements() throws Exception {

		Callable<Boolean> c = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {

				Logger logger = Logger.getLogger(MdcTests.class);
				logger.clearMdc();

				SecureRandom rnd = new SecureRandom();
				
				try {
					for (int i=0; i<10000; ++i) {
						
						int r = rnd.nextInt();
						
						logger.put("test", ""+r);
						Map<String,String> mdc = NestedDiagnosticContext.getMdc();
						Assert.assertNotNull(mdc);
						Assert.assertEquals(1, mdc.size());
						Assert.assertEquals(""+r, mdc.get("test"));
						logger.remove("test");

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
