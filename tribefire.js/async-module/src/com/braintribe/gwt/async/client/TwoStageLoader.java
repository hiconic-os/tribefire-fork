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

import com.google.gwt.user.client.rpc.AsyncCallback;

public class TwoStageLoader<T> {
	private static class QueueEntry<T> {
		private Loader<T> loader;
		private AsyncCallback<T> asyncCallback;
		public QueueEntry(Loader<T> loader, AsyncCallback<T> asyncCallback) {
			super();
			this.loader = loader;
			this.asyncCallback = asyncCallback;
		}
		
		public AsyncCallback<T> getAsyncCallback() {
			return asyncCallback;
		}
	}
	
	private QueueEntry<T> loadingEntry;
	private QueueEntry<T> waitingEntry;

	
	public void loadFrom(Loader<T> loader, AsyncCallback<T> asyncCallback) {
		load(new QueueEntry<T>(loader, asyncCallback));
	}
	
	private void load(QueueEntry<T> entry) {
		if (loadingEntry == null) {
			loadingEntry = entry;
			loadingEntry.loader.load(asyncCallback);
		}
		else {
			if (waitingEntry instanceof CancelListener) {
				CancelListener cancelListener = (CancelListener)waitingEntry;
				cancelListener.onCanceled();
			}
			waitingEntry = entry;
		}
	}
	
	private AsyncCallback<T> asyncCallback = new AsyncCallback<T>() {
		@Override
		public void onSuccess(T result) {
			loadingEntry.getAsyncCallback().onSuccess(result);
			next();
		}
		
		@Override
		public void onFailure(Throwable caught) {
			loadingEntry.getAsyncCallback().onFailure(caught);
			next();
		}
	};
	
	private void next() {
		if (waitingEntry != null) {
			loadingEntry = waitingEntry;
			waitingEntry = null;
			loadingEntry.loader.load(asyncCallback);
		} else
			loadingEntry = null;
	}
}
