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
package com.braintribe.wire.impl.scope;

import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.scope.InstanceHolder;
import com.braintribe.wire.api.scope.WireScope;
import com.braintribe.wire.api.space.WireSpace;

public abstract class AbstractInstanceHolder implements InstanceHolder {
	protected WireScope scope;
	private InstanceConfigurationImpl beanConfiguration;
	protected WireSpace space;
	protected String name;
	
	public AbstractInstanceHolder(WireSpace space, WireScope scope, String name) {
		this.space = space;
		this.scope = scope;
		this.name = name;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public WireScope scope() {
		return scope;
	}
	
	@Override
	public WireSpace space() {
		return space;
	}
	
	@Override
	public void onPostConstruct(Object bean) {
		scope.getContext().onPostConstruct(this, bean);
	}
	
	@Override
	public void onDestroy() {
		if (beanConfiguration != null) {
			Runnable destroyCallback = beanConfiguration.destroyCallback();
			if (destroyCallback != null)
				destroyCallback.run();
		}
		scope.getContext().onPreDestroy(this, get());
	}
	
	@Override
	public InstanceConfiguration config() {
		return beanConfiguration != null? 
				beanConfiguration: 
				(beanConfiguration = new InstanceConfigurationImpl(this));
	}
}
