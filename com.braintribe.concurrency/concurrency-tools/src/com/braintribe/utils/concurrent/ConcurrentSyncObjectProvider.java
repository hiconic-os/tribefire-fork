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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * PGA: This implementation is flawed - it only works in my code as I only care that the synchronization works the first time. Obviously,
 * after the SyncObject is released, another once could be created while some other thread out there still has the first one. I will fix
 * this at a later time.
 * 
 * {@link SyncObjectProvider} which causes blocking of two threads iff those two threads are synchronizing on two equivalent objects A and
 * B. The equivalence is considered in terms of {@link Object#equals(Object)} method.
 * 
 * Note that there is a hash map in the background, so it is necessary for objects being synchronized on to correctly implement the
 * {@link Object#hashCode()} method as well.
 */
public class ConcurrentSyncObjectProvider<T> implements SyncObjectProvider<T> {

	private final ConcurrentMap<T, SyncObject> syncObjects = new ConcurrentHashMap<T, SyncObject>();

	@Override
	public Object acquireSyncObject(T arg) {
		SyncObject newValue = new SyncObject();
		SyncObject oldValue = syncObjects.putIfAbsent(arg, newValue);

		return oldValue != null ? oldValue : newValue;
	}

	@Override
	public void releaseSyncObject(T arg) {
		SyncObject syncObject = syncObjects.get(arg);

		if (syncObject != null && syncObject.creator == Thread.currentThread()) {
			syncObjects.remove(arg);
		}
	}

	static class SyncObject {
		final Thread creator = Thread.currentThread();
	}
}
