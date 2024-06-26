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

import com.braintribe.gwt.gmrpc.web.client.GwtGmWebRpcEvaluator;
import com.braintribe.gwt.gmsession.client.CortexTypeEnsurer;
import com.braintribe.provider.SingletonBeanProvider;

public class GmRpc {
	
	protected static Supplier<CortexTypeEnsurer> typeEnsurer = new SingletonBeanProvider<CortexTypeEnsurer>() {
		@Override
		public CortexTypeEnsurer create() throws Exception {
			CortexTypeEnsurer bean = publish(new CortexTypeEnsurer());
			bean.setEvaluator(serviceRequestEvaluator.get());
			return bean;
		}
	};
	
	protected static Supplier<GwtGmWebRpcEvaluator> serviceRequestEvaluator = new SingletonBeanProvider<GwtGmWebRpcEvaluator>() {
		@Override
		public GwtGmWebRpcEvaluator create() throws Exception {
			GwtGmWebRpcEvaluator bean = publish(new GwtGmWebRpcEvaluator());
			bean.setServerUrl(serverUrl.get());
			bean.setTypeEnsurer(typeEnsurer.get());
			bean.setSessionIdProvider(Providers.sessionIdProvider.get());
			return bean;
		}
	};
	
	private static Supplier<String> serverUrl = new SingletonBeanProvider<String>() {
		@Override
		public String create() throws Exception {
			return Runtime.tribefireServicesUrl.get() + "rpc";
		}
	};

}
