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

public class EmptyThreadContextScoping implements ThreadContextScoping {

	public static final EmptyThreadContextScoping INSTANCE = new EmptyThreadContextScoping();

	private EmptyThreadContextScoping() {
	}

	@Override
	public Runnable bindContext(Runnable runnable) {
		return runnable;
	}

	@Override
	public <T> Callable<T> bindContext(Callable<T> callable) {
		return callable;
	}

	@Override
	public void runWithContext(Runnable runnable) {
		runnable.run();
	}

	@Override
	public <T> T runWithContext(Callable<T> callable) throws Exception {
		return callable.call();
	}

}
