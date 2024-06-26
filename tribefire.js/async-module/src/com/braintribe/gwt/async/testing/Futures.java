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
package com.braintribe.gwt.async.testing;

import java.util.concurrent.Executors;

import com.braintribe.gwt.async.client.DeferredExecutor;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.async.client.Future.State;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author peter.gazdik
 */
public class Futures {

	public static final DeferredExecutor syncExecutor = Runnable::run;
	public static final DeferredExecutor singleThreadedDeferredExecutor = Executors.newSingleThreadExecutor()::execute;

	public static <T> T waitForValue(Future<T> future) {
		waitFor(future);

		return future.getResult();
	}

	public static void waitFor(Future<?> f) {
		if (f.getState() == State.outstanding) {
			MonitorCallback monitor = new MonitorCallback();

			f.get(monitor);

			synchronized (monitor) {
				if (f.getState() == State.outstanding) {
					try {
						monitor.wait(0);
					} catch (InterruptedException e) {
						// noop
					}
				}
			}
		}
	}

	// @formatter:off
	private static class MonitorCallback implements AsyncCallback<Object> {
		@Override public synchronized void onSuccess(Object value) { 
			notify(); 
			}
		@Override public synchronized void onFailure(Throwable t) { notify(); }
	}
	// @formatter:on

}
