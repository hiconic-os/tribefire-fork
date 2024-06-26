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
package com.braintribe.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractBeanProvider<T> implements Supplier<T> {
	private List<Supplier<?>> attachedProviders = null;
	private List<Supplier<?>> dependsOnProviders = null;

	public abstract T create() throws Exception;

	protected void attach(Supplier<?> provider) {
		if (attachedProviders == null) {
			attachedProviders = new ArrayList<>();
		}
		attachedProviders.add(provider);
	}

	protected void attachTo(Supplier<?> provider) {
		AbstractBeanProvider<?> abstractBeanProvider = (AbstractBeanProvider<?>) provider;
		abstractBeanProvider.attach(this);
	}

	protected void dependsOn(Supplier<?> provider) {
		if (dependsOnProviders == null) {
			dependsOnProviders = new ArrayList<>();
		}
		dependsOnProviders.add(provider);
	}

	protected void ensurePreconditions() throws RuntimeException {
		if (dependsOnProviders != null) {
			for (Supplier<?> provider : dependsOnProviders) {
				provider.get();
			}
		}
	}

	protected void ensureAttachmentsInstantiated() throws RuntimeException {
		if (attachedProviders != null) {
			for (Supplier<?> provider : attachedProviders) {
				provider.get();
			}
		}
	}
}
