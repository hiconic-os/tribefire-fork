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
package com.braintribe.gwt.genericmodel.client.codec.dom4;

import com.braintribe.codec.CodecException;
import com.braintribe.gwt.async.client.Future;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class AbstractCodingContext {
	private DeferredProcessor anchorProcessor = new DeferredProcessor();
	protected DeferredProcessor lastProcessor = anchorProcessor;

	public void appendDeferredProcessor(DeferredProcessor processor) {
		lastProcessor = lastProcessor.next = processor;
	}

	
	protected void runDeferredProcessors() throws CodecException {
		DeferredProcessor deferredProcessor = anchorProcessor.next;
		
		while (deferredProcessor != null) {
			while (deferredProcessor.continueProcessing()) { /* Nothing to do here */ }
			deferredProcessor = deferredProcessor.next;
		}
	}

	protected Future<Void> runDeferredProcessorsAsync() {
		final Future<Void> future = new Future<Void>();
		
		Scheduler.get().scheduleIncremental(new RepeatingCommand() {
			
			private DeferredProcessor deferredProcessor = anchorProcessor.next;
			
			@Override
			public boolean execute() {
				DeferredProcessor deferredProcessor = this.deferredProcessor;
				
				try {
					long s = System.currentTimeMillis();
					while (deferredProcessor != null) {
						while (deferredProcessor.continueProcessing()) { /* Nothing to do here */ }
						
						long e = System.currentTimeMillis();
						long d = e - s;
						deferredProcessor = deferredProcessor.next;
						
						if (d > 15 && deferredProcessor != null) {
							this.deferredProcessor = deferredProcessor;
							return true;
						}
					}
					
					future.onSuccess(null);
					
				} catch (Exception e) {
					future.onFailure(e);
				}
				
				return false;
			}
		});
		
		return future;
	}

}
