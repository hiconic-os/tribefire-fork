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

public class FragmentChainType<T> implements ChainType<T>, Loader<T> {
	private AsyncCallback<T> asyncCallback;
	
	public FragmentChainType() {
	}
	
	@Override
	public void load(AsyncCallback<T> asyncCallback) {
		this.asyncCallback = asyncCallback;
	}
	
	public AsyncCallback<T> getAsyncCallback() {
		return asyncCallback;
	}
}

