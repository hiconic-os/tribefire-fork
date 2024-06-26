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
package com.braintribe.utils.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.braintribe.utils.junit.core.rules.ConcurrentRule;

/**
 * 
 */
public class ConcurrentSyncObjectProviderDemo {

	@Rule
	public ConcurrentRule concurrentRule = new ConcurrentRule(2);

	private SyncObjectProvider<List<?>> syncProvider = new ConcurrentSyncObjectProvider<List<?>>();

	private boolean waitFlag = true;
	private Object LOCK = new Object();

	// ##################################
	// ## . . . . DEMO TESTS . . . . . ##
	// ##################################

	@Test
	public void works() throws Exception {
		List<?> emptyList = new ArrayList<Object>();
		try {
			Object syncObject = syncProvider.acquireSyncObject(emptyList);
			synchronized (syncObject) {
				/* first thread waits a little, so second thread has enough time to get the same sync object */
				Thread.sleep(100);
				System.out.println("[SAME SYNC OBJECT]: " + syncObject);
			}
		} finally {
			syncProvider.releaseSyncObject(emptyList);
		}
	}

	@Test
	public void works2() throws Exception {
		List<?> emptyList = new ArrayList<Object>();
		waitIfFirstThread();

		/*
		 * the first thread that enters the try-block has to wake up the other after he releases the object, thus there
		 * will be two different sync objects
		 */
		try {
			Object syncObject = syncProvider.acquireSyncObject(emptyList);
			synchronized (syncObject) {
				System.out.println("[DIFFERENT SYNC OBJECT]: " + syncObject);
			}
		} finally {
			syncProvider.releaseSyncObject(emptyList);
		}

		wakeUpWaitingThread();
	}

	private void waitIfFirstThread() throws InterruptedException {
		synchronized (LOCK) {
			if (waitFlag) {
				waitFlag = false;
				LOCK.wait();
			}
		}
	}

	private void wakeUpWaitingThread() {
		synchronized (LOCK) {
			LOCK.notifyAll();
		}
	}

}
