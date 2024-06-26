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
package com.braintribe.gwt.genericmodel.client.proxy;

import java.util.Iterator;

import com.braintribe.codec.CodecException;
import com.braintribe.model.generic.proxy.DeferredApplier;
import com.braintribe.model.generic.proxy.ProxyContext;
import com.braintribe.processing.async.api.AsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class GwtProxyContext extends ProxyContext {
	@Override
	public void resolveProxiesAndApply(AsyncCallback<Void> callback) {
		AsyncProxyApplication application = new AsyncProxyApplication(appliers.iterator(), callback);
		application.start();
	}
	
	private class AsyncProxyApplication implements RepeatingCommand {
		private Iterator<DeferredApplier> it;
		private AsyncCallback<Void> callback;
		
		public AsyncProxyApplication(Iterator<DeferredApplier> iterator, AsyncCallback<Void> callback) {
			super();
			this.it = iterator;
			this.callback = callback;
		}

		public void start() {
			Scheduler.get().scheduleIncremental(this);
		}
		
		@Override
		public boolean execute() {
			long s = System.currentTimeMillis();
			try {
				while (it.hasNext()) {
					
						DeferredApplier applier = it.next();
						applier.apply();
					
					long d = System.currentTimeMillis() - s;
					
					if (d > 100 && it.hasNext())
						return true;
				}
			} catch (Exception e) {
				callback.onFailure(new CodecException("error while decoding js fragment", e));
			}

			return false;
		}
	}

}
