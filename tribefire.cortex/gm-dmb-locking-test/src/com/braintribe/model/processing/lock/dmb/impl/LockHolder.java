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
package com.braintribe.model.processing.lock.dmb.impl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class LockHolder implements Runnable {

	private Lock lock;
	private boolean release = false;
	private boolean hasLock = false;
	private CountDownLatch countdownLatch;
	
	public LockHolder(Lock lock, CountDownLatch countdownLatch) {
		this.lock = lock;
		this.countdownLatch = countdownLatch;
	}
	
	public void release() {
		release = true;
	}
	
	public boolean hasLock() {
		return hasLock;
	}
	
	@Override
	public void run() {
		
		lock.lock();
		try {
			hasLock = true;
			countdownLatch.countDown();
			
			while (!release) {
				try {
					Thread.sleep(100L);
				} catch(InterruptedException e) {
					return;
				}
			}
			
		} finally {
			lock.unlock();
		}
		
		hasLock = false;
	}

}
