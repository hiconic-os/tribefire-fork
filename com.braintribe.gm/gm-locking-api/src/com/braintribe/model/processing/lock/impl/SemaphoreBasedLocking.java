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
package com.braintribe.model.processing.lock.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import com.braintribe.model.processing.lock.api.Locking;

/**
 * @author peter.gazdik
 */
public class SemaphoreBasedLocking implements Locking {

	private final Map<String, ReadWriteLock> locks = new ConcurrentHashMap<>();

	@Override
	public ReadWriteLock forIdentifier(String id) {
		return locks.computeIfAbsent(id, k -> new SemaphoreBasedRwLock());
	}

	class SemaphoreBasedRwLock implements ReadWriteLock {

		private final SemaphoreBasedLock lock = new SemaphoreBasedLock();

		@Override
		public Lock readLock() {
			return lock;
		}

		@Override
		public Lock writeLock() {
			return lock;
		}

	}

	class SemaphoreBasedLock implements Lock {

		private final Semaphore semaphore = new Semaphore(1);

		@Override
		public void lock() {
			semaphore.acquireUninterruptibly();
		}

		@Override
		public void lockInterruptibly() throws InterruptedException {
			semaphore.acquire();
		}

		@Override
		public boolean tryLock() {
			return semaphore.tryAcquire();
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			return semaphore.tryAcquire(time, unit);
		}

		@Override
		public void unlock() {
			semaphore.release();
		}

		@Override
		public Condition newCondition() {
			throw new UnsupportedOperationException("Method 'RwLockBasedLocking.SemaphoreBasedLock.newCondition' is not supported!");
		}

	}

}
