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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

/**
 * Wrapper around a {@link StampedLock} object that wraps the actual {@link Lock}s returned
 * by {@link StampedLock#asReadLock()} and {@link StampedLock#asWriteLock()} with a
 * {@link StampedLockDelegate} object.
 * <p>
 * The point of the exercise is that we need to track whether a {@link Lock} is locked or not.
 * This is necessary for the eviction policy used in {@link DmbLockManager}.
 * <p>
 * The lock state is stored in the {@link #lockCount} variable. With each {@link Lock#lock()}, 
 * {@link Lock#tryLock()}, or {@link Lock#tryLock(long, java.util.concurrent.TimeUnit)} (if successful) 
 * invocation, this number will be increased, with every {@link Lock#unlock()} decreased.
 * The entry will only be allowed to be evicted when the counter is 0.   
 * 
 * 
 * @author roman.kurmanowytsch
 *
 */
public class StampedLockEntry {

	private long timestamp;
	private StampedLock delegate;

	private AtomicInteger lockCount = new AtomicInteger();

	private StampedLockDelegate readLock = null;
	private StampedLockDelegate writeLock = null;

	public StampedLockEntry(StampedLock delegate) {
		this.delegate = delegate;
		this.timestamp = System.currentTimeMillis();
	}
	public long getTimestamp() {
		return this.timestamp;
	}
	

	public boolean isLocked() {
		return lockCount.get() > 0;
	}
	
	public void registerLock() {
		lockCount.incrementAndGet();
	}
	public void registerUnlock() {
		lockCount.decrementAndGet();
	}
	
	/**
	 * Creates an single instance of a {@link StampedLockDelegate} entry and returns it.
	 * 
	 * @return The {@link StampedLockDelegate} object that is held by this instance.
	 */
	public Lock asReadLock() {
		if (readLock == null) {
			synchronized(this) {
				if (readLock == null) {
					readLock = new StampedLockDelegate(delegate.asReadLock(), this);
				}
			}
		}
		return readLock;
	}
	/**
	 * Creates an single instance of a {@link StampedLockDelegate} entry and returns it.
	 * 
	 * @return The {@link StampedLockDelegate} object that is held by this instance.
	 */
	public Lock asWriteLock() {
		if (writeLock == null) {
			synchronized(this) {
				if (writeLock == null) {
					writeLock = new StampedLockDelegate(delegate.asWriteLock(), this);
				}
			}
		}
		return writeLock;
	}

}
