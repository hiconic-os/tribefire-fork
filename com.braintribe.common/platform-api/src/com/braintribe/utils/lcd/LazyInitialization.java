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
package com.braintribe.utils.lcd;

/**
 * Convenient wrapper for code that should only be executed once.
 * <p>
 * This implementation is thread safe.
 *
 * @see #run()
 * @see LazyInitialized
 */
public class LazyInitialization {

	private LazyInitialized<Void> lazyInitialized;

	public LazyInitialization(Runnable runnable) {
		this.lazyInitialized = new LazyInitialized<>(() -> {
			runnable.run();
			return null;
		});
	}

	/**
	 * Runs the actual {@link Runnable} provided via constructor, but only on first invocation of this method. All subsequent invocations have no
	 * effect.
	 */
	public void run() {
		lazyInitialized.get();
	}

}
