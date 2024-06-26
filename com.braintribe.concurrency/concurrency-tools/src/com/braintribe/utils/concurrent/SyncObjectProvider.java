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

/**
 * Provider for object that should be used as lock for synchronization. The java {@code synchronized()} block is 100%
 * exclusive, which in some cases is not enough. We may want some degree of concurrency, which is what classes
 * implementing this interface provide. The method {@link #acquireSyncObject(Object)} decides, for which objects the
 * block may run concurrently. For example one may need a restriction, that only objects which are equal need to be
 * synchronized, but non-equal objects may be processed concurrently.
 * <p>
 * The method {@link #releaseSyncObject(Object)} serves only for memory cleanup purposes.
 * <p>
 * 
 * The recommended usage of this provider is something like this:
 * <p>
 * <code>
 * try {
 * 	 	synchronized(syncObjectProvider.acquireSyncObject(object)) {
 * 			// some code
 * 	 	}
 * } finally {
 * 		syncObjectProvider.releaseSyncObject(object);
 * }
 * </code>
 * 
 * @see ConcurrentSyncObjectProvider
 * @see ExclusiveSyncObjectProvider
 */
public interface SyncObjectProvider<T> {

	/**
	 * This method controls for which object the synchronized code may be running concurrently. As long, as a the code
	 * may be run at once for a given set of objects, this method should always return a different object (which serves
	 * as a lock), which means that synchronizing on the result of this method doesn't make a {@link Thread} wait for
	 * another thread. If, however, processing some two objects A and B concurrently should not be possible, then this
	 * method should return the same object twice for arguments A and B (assuming there was no relevant invocation of
	 * {@link #releaseSyncObject(Object)} in the meantime).
	 */
	Object acquireSyncObject(T arg);

	/**
	 * Notify the provider, that the synchronization part for some object is over, so it may do some internal cleanup.
	 */
	void releaseSyncObject(T arg);

}
