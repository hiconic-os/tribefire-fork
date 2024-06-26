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
package com.braintribe.gwt.async.client;

import java.util.Iterator;

import com.google.gwt.user.client.Timer;

public class ForEach<T> {
	private long synchedTimeSpan = 1000;
	private int asyncDelay = 100;
	private Iterator<T> iterator;
	private Processor<T> processor;
	
	public ForEach(Iterable<T> iterable) {
		this.iterator = iterable.iterator();
	}
	
	public ForEach<T> setSynchedTimeSpan(long synchedTimeSpan) {
		this.synchedTimeSpan = synchedTimeSpan;
		return this;
	}
	
	public ForEach<T> setAsyncDelay(int asyncDelay) {
		this.asyncDelay = asyncDelay;
		return this;
	}
	
	public Future<Void> process(Processor<T> processor) {
		this.processor = processor;
		Future<Void> future = new Future<Void>();
		processSynched(future);
		return future;
	}
	
	protected void processSynched(final Future<Void> future) {
		long start = System.currentTimeMillis();
		
		while (iterator.hasNext()) {
			T element = iterator.next();
			try {
				processor.process(element);
			}
			catch (CanceledException e) {
				future.onSuccess(null);
			}
			catch (Throwable e) {
				future.onFailure(e);
			}
			long end = System.currentTimeMillis();
			if (end - start > synchedTimeSpan) {
				new Timer() {
					@Override
					public void run() {
						processSynched(future);
					}
				}.schedule(asyncDelay);
				break;
			}
		}
	}
}
