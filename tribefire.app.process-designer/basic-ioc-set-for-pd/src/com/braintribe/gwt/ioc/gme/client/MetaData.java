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
package com.braintribe.gwt.ioc.gme.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.model.processing.meta.cmd.context.SelectorContextAspect;
import com.braintribe.model.processing.meta.cmd.context.aspects.RoleAspect;

public class MetaData {
	
	protected static Supplier<Map<Class<? extends SelectorContextAspect<?>>, Supplier<?>>> dynamicAspectValueProviders =
			new SessionScopedBeanProvider<Map<Class<? extends SelectorContextAspect<?>>,Supplier<?>>>() {
		@Override
		public Map<Class<? extends SelectorContextAspect<?>>, Supplier<?>> create() throws Exception {
			Map<Class<? extends SelectorContextAspect<?>>, Supplier<?>> bean = publish(new HashMap<>());
			bean.put(RoleAspect.class, Providers.rolesProvider.get());
			return bean;
		}
	};

}
