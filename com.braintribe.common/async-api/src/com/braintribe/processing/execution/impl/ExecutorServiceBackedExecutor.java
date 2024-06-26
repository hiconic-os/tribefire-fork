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
package com.braintribe.processing.execution.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.braintribe.processing.execution.api.Executor;
import com.braintribe.processing.execution.api.ExecutorCrossCuttingConcern;

public class ExecutorServiceBackedExecutor implements Executor {
	private ExecutorService delegate;
	private ExecutorCrossCuttingConcern crossCuttingConcern;
	
	public ExecutorServiceBackedExecutor(ExecutorService delegate) {
		this.delegate = delegate;
	}

	public void setCrossCuttingConcern(ExecutorCrossCuttingConcern crossCuttingConcern) {
		this.crossCuttingConcern = crossCuttingConcern;
	}
	
	@Override
	public void execute(Runnable command) {
		delegate.execute(enrich(command));
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return delegate.submit(enrich(task));
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return delegate.submit(enrich(task), result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return delegate.submit(enrich(task));
	}
	
	protected Runnable enrich(Runnable runnable) {
		if (crossCuttingConcern == null)
			return runnable;
		
		return () -> {
			crossCuttingConcern.onExecutionStart();
			try {
				runnable.run();
			}
			finally {
				crossCuttingConcern.onExecutionEnd();
			}
		};
	}
	
	protected <T> Callable<T> enrich(Callable<T> callable) {
		if (crossCuttingConcern == null)
			return callable;
		
		return () -> {
			crossCuttingConcern.onExecutionStart();
			try {
				return callable.call();
			}
			finally {
				crossCuttingConcern.onExecutionEnd();
			}
		};
	}
}