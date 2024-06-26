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

import com.braintribe.gwt.gmview.client.GmContentViewContext;
import com.braintribe.gwt.gmview.client.ViewSituationResolver;
import com.braintribe.gwt.gmview.client.ViewSituationSelector;
import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.provider.PrototypeBeanProvider;

public class ViewSituationResolution {
	
	protected static Supplier<ViewSituationResolver<GmContentViewContext>> viewSituationResolver = new SessionScopedBeanProvider<ViewSituationResolver<GmContentViewContext>>() {
		@Override
		public ViewSituationResolver<GmContentViewContext> create() throws Exception {
			ViewSituationResolver<GmContentViewContext> bean = publish(new ViewSituationResolver<>());
			bean.setGmExpertRegistry(Runtime.gmExpertRegistry.get());
			bean.setViewSituationSelectorMap(standardViewSituationSelectorMap.get());
			bean.setPriorityReverse(false);
			return bean;
		}
	};
	
	private static Supplier<Map<ViewSituationSelector, GmContentViewContext>> standardViewSituationSelectorMap = new PrototypeBeanProvider<Map<ViewSituationSelector, GmContentViewContext>>() {
		@Override
		public Map<ViewSituationSelector, GmContentViewContext> create() throws Exception {
			Map<ViewSituationSelector, GmContentViewContext> bean = new HashMap<>();
			return bean;
		}
	};

}
