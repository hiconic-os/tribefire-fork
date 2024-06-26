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

import java.util.Set;
import java.util.function.Supplier;

import com.braintribe.gm.model.persistence.reflection.api.GetModelAndWorkbenchEnvironment;
import com.braintribe.gwt.ioc.gme.client.expert.bootstrapping.BootstrappingRequest;
import com.braintribe.gwt.security.client.SessionScopedBeanProvider;
import com.braintribe.gwt.utils.client.FastSet;
import com.braintribe.model.workbench.KnownWorkenchPerspective;
import com.braintribe.provider.SingletonBeanProvider;

public class Requests {
	
	protected static Supplier<BootstrappingRequest> bootstrappingRequest = new SessionScopedBeanProvider<BootstrappingRequest>() {
		@Override
		public BootstrappingRequest create() throws Exception {
			BootstrappingRequest bean = publish(new BootstrappingRequest());
			bean.setRpcEvaluator(GmRpc.serviceRequestEvaluator.get());
			bean.setModelEnvironmentRequest(modelEnvironmentRequest.get());
			return bean;
		}
	};
	
	private static Supplier<GetModelAndWorkbenchEnvironment> modelEnvironmentRequest = new SessionScopedBeanProvider<GetModelAndWorkbenchEnvironment>() {
		@Override
		public GetModelAndWorkbenchEnvironment create() throws Exception {
			GetModelAndWorkbenchEnvironment bean = GetModelAndWorkbenchEnvironment.T.create();
			bean.setFoldersByPerspective(perspectivesSet.get());
			return bean;
		}
	};
	
	private static Supplier<Set<String>> perspectivesSet = new SingletonBeanProvider<Set<String>>() {
		@Override
		public Set<String> create() throws Exception {
			Set<String> bean = new FastSet();
			for (KnownWorkenchPerspective known : KnownWorkenchPerspective.values())
				bean.add(known.toString());
			return bean;
		}
	};

}
