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

import java.util.function.Supplier;

import com.braintribe.gwt.gme.actionmenubuilder.client.DefaultActionMenuBuilder;
import com.braintribe.provider.SingletonBeanProvider;

public class UiElements {
	
	protected static Supplier<DefaultActionMenuBuilder> defaultActionMenuBuilder = new SingletonBeanProvider<DefaultActionMenuBuilder>() {
		@Override
		public DefaultActionMenuBuilder create() throws Exception {
			DefaultActionMenuBuilder bean = new DefaultActionMenuBuilder();
			bean.setWorkbenchActionHandlerRegistry(Actions.workbenchActionHandlerRegistry.get());
			bean.setSeparatorActionProvider(Actions.separatorAction);
			return bean;
		}
	};
	
}
