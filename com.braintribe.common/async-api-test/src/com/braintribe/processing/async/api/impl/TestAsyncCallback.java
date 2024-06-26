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
package com.braintribe.processing.async.api.impl;

import java.util.concurrent.LinkedBlockingQueue;

import com.braintribe.processing.async.api.AsyncCallback;

public class TestAsyncCallback implements AsyncCallback<String> {

	private String value;
	private Throwable error;
	private LinkedBlockingQueue<String> resultQueue;

	public TestAsyncCallback(LinkedBlockingQueue<String> resultQueue) {
		this.resultQueue = resultQueue;
	}
	
	@Override
	public void onSuccess(String value) {
		this.value = value;
		resultQueue.add(value);
	}

	@Override
	public void onFailure(Throwable error) {
		this.error = error;
	}


	public String getValue() {
		return value;
	}

	public Throwable getError() {
		return error;
	}
}
