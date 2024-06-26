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
package com.braintribe.model.access.smood.distributed.test.concurrent.tester;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.braintribe.model.access.smood.distributed.test.concurrent.worker.Worker;

public class SingleJvmSmoodDbAccessTest extends AbstractSmoodDbAccessTest {

	@Override
	protected void executeWorkers(int workerCountParam, int iterations) throws Exception {

		ExecutorService service = Executors.newFixedThreadPool(workerCountParam);
		List<Future<Void>> futures = new ArrayList<Future<Void>>();

		for (int i=0; i<workerCountParam; ++i) {
			Worker worker = super.factory.provideWorker();
			worker.setId(""+i);
			futures.add(service.submit(worker));
		}

		boolean allDone = false;
		while (!allDone) {
			allDone = true;
			for (Future<Void> f : futures) {
				if (!f.isDone()) {
					allDone = false;
					break;
				}
			}			
		}
		
		service.shutdown();
	}

}
