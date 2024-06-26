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
package com.braintribe.collections.dispatchcollector;

import java.util.Collection;

public class HoldingDispatchReceiver<T> implements DispatchReceiver<T> {

	protected Collection<T> holdingCollection = null;

	@Override
	public void receive(Collection<T> collection) {
		this.holdingCollection = collection;
	}

	public Collection<T> getCollection() {
		return this.holdingCollection;
	}

	public void clear() {
		if (this.holdingCollection != null) {
			this.holdingCollection.clear();
		}
	}
}
