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
package com.braintribe.thread.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.braintribe.thread.api.DeferringThreadContextScoping;
import com.braintribe.thread.api.ThreadContextScope;
import com.braintribe.thread.api.ThreadContextScoping;

public class ThreadContextScopingImpl implements DeferringThreadContextScoping {

	private List<Supplier<? extends ThreadContextScope>> scopeSuppliers;

	public void setScopeSuppliers(List<Supplier<? extends ThreadContextScope>> scopeSuppliers) {
		this.scopeSuppliers = scopeSuppliers;
	}

	@Override
	public Runnable bindContext(Runnable runnable) throws ThreadContextScopingRuntimeException {

		List<ThreadContextScope> scopes = scopeSuppliers.stream().map(s -> s.get()).collect(Collectors.toList());

		ContextBoundRunnable cbRunnable = new ContextBoundRunnable(runnable, scopes);

		return cbRunnable;
	}

	@Override
	public <U> Callable<U> bindContext(Callable<U> callable) {

		List<ThreadContextScope> scopes = scopeSuppliers.stream().map(s -> s.get()).collect(Collectors.toList());

		ContextBoundCallable<U> cbCallable = new ContextBoundCallable<>(callable, scopes);

		return cbCallable;
	}

	@Override
	public void runWithContext(Runnable runnable) {

		Runnable boundRunnable = this.bindContext(runnable);
		boundRunnable.run();

	}

	@Override
	public <U> U runWithContext(Callable<U> callable) throws Exception {

		Callable<U> boundCallable = this.bindContext(callable);
		return boundCallable.call();

	}

	@Override
	public ThreadContextScoping defer() {

		List<Supplier<? extends ThreadContextScope>> list = scopeSuppliers.stream().map(s -> s.get())
				.<Supplier<? extends ThreadContextScope>> map(t -> () -> t).collect(Collectors.toList());

		ThreadContextScopingImpl deferringInstance = new ThreadContextScopingImpl();
		deferringInstance.setScopeSuppliers(list);
		return deferringInstance;
	}

}
