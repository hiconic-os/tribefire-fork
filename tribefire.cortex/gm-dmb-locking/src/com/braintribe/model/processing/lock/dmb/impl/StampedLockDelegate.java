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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Wrapper around a {@link Lock} object that registers every lock/unlock invocations
 * to keep a state of whether the lock is actually locked or not.
 * 
 * @author roman.kurmanowytsch
 *
 */
public class StampedLockDelegate implements Lock {

	private Lock delegate;
	private StampedLockEntry lockEntry;

	public StampedLockDelegate(Lock delegate, StampedLockEntry lockEntry) {
		this.delegate = delegate;
		this.lockEntry = lockEntry;
		
	}

	@Override
	public void lock() {
		delegate.lock();
		lockEntry.registerLock();
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		delegate.lockInterruptibly();
		lockEntry.registerLock();
	}

	@Override
	public boolean tryLock() {
		boolean locked = delegate.tryLock();
		if (locked) {
			lockEntry.registerLock();
		}
		return locked;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		boolean locked = delegate.tryLock(time, unit);
		if (locked) {
			lockEntry.registerLock();
		}
		return locked;
	}

	@Override
	public void unlock() {
		delegate.unlock();
		lockEntry.registerUnlock();
	}

	@Override
	public Condition newCondition() {
		return delegate.newCondition();
	}

}
