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
package com.braintribe.utils;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is a ID generator for long values. The aim of this class is to provide a long number that is almost unique (or,
 * unique enough). It cannot be guaranteed that the numbers returned by this class are unique over multiple JVMs, but it
 * tries... The Ids generated are comprised of two separate numbers: the current timestamp (see
 * {@link java.lang.System#currentTimeMillis()}) and a sequence number that is atomically incremented with each newly
 * generated Id.
 */
public class LongIdGenerator {

	// To improve the uniqueness over multiple JVMs, we use a random offset for the sequencer
	private static SecureRandom secureRandom;
	public static AtomicLong atomicLongCounter = new AtomicLong(getSecureRandom().nextLong());
	private static ReentrantLock lock = new ReentrantLock();

	public static long provideLongId() {

		lock.lock();
		try {
			// Take the next sequence number and use the 21 least significant bits
			long counter = atomicLongCounter.incrementAndGet();
			counter = counter & 0x1fffff;


			long nowMs = System.currentTimeMillis();
			// We make space for the sequence number. Thus, we shift the timestamp by 21 bits to the left
			// We have now space for approx 2 mio sequence numbers (per Millisecond) (2097151, to be exact)
			nowMs = nowMs << 21;


			long id = nowMs | counter;

			return id;
			
		} finally {
			lock.unlock();
		}
		
	}
	
	private static SecureRandom getSecureRandom() {
		if (secureRandom != null)
			return secureRandom;
		
		secureRandom = new SecureRandom();
		return secureRandom;
	}
}
