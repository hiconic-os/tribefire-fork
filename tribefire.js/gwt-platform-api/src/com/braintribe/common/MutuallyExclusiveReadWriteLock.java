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
package com.braintribe.common;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link ReadWriteLock} implementation which uses a single {@link ReentrantLock} as a delegate, i.e. both Lock methods
 * return the same {@link Lock} instance.
 *
 * @author peter.gazdik
 */
public class MutuallyExclusiveReadWriteLock implements ReadWriteLock {

	private final Lock lock = new ReentrantLock();

	@Override
	public Lock readLock() {
		return lock;
	}

	@Override
	public Lock writeLock() {
		return lock;
	}

}
