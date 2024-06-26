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
package com.braintribe.execution.priority;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PriorityFuture<T> implements RunnableFuture<T>, Comparable<RunnableFuture<T>>, HasPriority {

    private RunnableFuture<T> delegate;
    private int insertionIndex;
    private double priority;

    public PriorityFuture(RunnableFuture<T> other, double priority, int insertionIndex) {
        this.delegate = other;
        this.priority = priority;
        this.insertionIndex = insertionIndex;
    }

    @Override
    public double getPriority() {
        return priority;
    }
    
    @Override
    public int getInsertionIndex() {
    	return insertionIndex;
    }

    @Override
	public boolean cancel(boolean mayInterruptIfRunning) {
        return delegate.cancel(mayInterruptIfRunning);
    }

    @Override
	public boolean isCancelled() {
        return delegate.isCancelled();
    }

    @Override
	public boolean isDone() {
        return delegate.isDone();
    }

    @Override
	public T get() throws InterruptedException, ExecutionException {
        return delegate.get();
    }

    @Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.get(timeout, unit);
    }

    @Override
	public void run() {
        delegate.run();
    }

	@Override
	public int compareTo(RunnableFuture<T> or) {
		PriorityFuture<T> o = (PriorityFuture<T>) or;
		if (o.priority > this.priority) {
			return 1;
		} else if (o.priority < this.priority) {
			return -1;
		}
		if (o.insertionIndex < this.insertionIndex) {
			return 1;
		} else {
			return -1;
		}
	}
	
}