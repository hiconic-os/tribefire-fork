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
package com.braintribe.common.lcd;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * {@link Lock} implementation that does nothing at all.
 *
 * @author peter.gazdik
 */
public class EmptyLock implements Lock {

	public static final EmptyLock INSTANCE = new EmptyLock();

	private EmptyLock() {
	}

	@Override
	public void unlock() {
		// nothing to do
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) {
		return true;
	}

	@Override
	public boolean tryLock() {
		return true;
	}

	@Override
	public Condition newCondition() {
		throw new UnsupportedOperationException("Method 'EmptyLock.newCondition' is not supported!");
	}

	@Override
	public void lockInterruptibly() {
		// nothing to do
	}

	@Override
	public void lock() {
		// nothing to do
	}

}
