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
package com.braintribe.thread.api;

import java.util.concurrent.Callable;

/**
 * This interface defines convenience methods for binding threads to a user session. When you want to run code in a separate thread, you typically
 * loose the thread's user session. An implementation of this interface wraps Runnable and Callable objects and inject the user session before
 * executing the actual code.
 *
 * You can either use the bind methods to wrap your Runnable/Callable and run it yourself or you can use the convenience methods to run the
 * Runnable/Callable immediately.
 */
public interface ThreadContextScoping {

	Runnable bindContext(Runnable runnable);

	<T> Callable<T> bindContext(Callable<T> callable);

	void runWithContext(Runnable runnable);

	<T> T runWithContext(Callable<T> callable) throws Exception;

}
