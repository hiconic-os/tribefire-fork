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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LockRegistry {

	private LockRegistry() {
		//Singleton
	}
	private static class LazyHolder {
        public static final LockRegistry _instance = new LockRegistry();
    }	
	public static LockRegistry getInstance() {
		return LazyHolder._instance;
	}

	protected Map<String,Integer> lockCount = new HashMap<String,Integer>();
	protected Map<String,ReentrantLock> lockMap = new HashMap<String,ReentrantLock>();
	protected ReentrantLock masterLock = new ReentrantLock();
	
	public ReentrantLock acquireLock(String uid) {
		
		ReentrantLock lock = null;
		try {
			masterLock.lock();
			
			lock = this.lockMap.get(uid);
			if (lock == null) {
				lock = new ReentrantLock();
				this.lockMap.put(uid, lock);
				this.lockCount.put(uid, 1);
			} else {
				Integer count = this.lockCount.get(uid);
				if (count == null) {
					this.lockCount.put(uid, 1);
				} else {
					this.lockCount.put(uid, count+1);
				}
			}
			
		} finally {
			masterLock.unlock();
		}
		
		return lock;
	}
	
	public void releaseLock(String uid) {
		try {
			masterLock.lock();
			
			Integer count = this.lockCount.get(uid);
			if (count != null) {
				if (count == 1) {
					//Final release
					this.lockCount.remove(uid);
					this.lockMap.remove(uid);
				} else {
					this.lockCount.put(uid, count-1);
				}
			}
			
		} finally {
			masterLock.unlock();
		}
	}
}
