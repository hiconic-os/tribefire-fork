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
package com.braintribe.gwt.ioc.tribefirejs.client;

import java.util.function.Supplier;

import com.braintribe.gwt.gmrpc.web.client.GwtGmWebRpcEvaluator;
import com.braintribe.gwt.gmsession.client.CortexTypeEnsurer;
import com.braintribe.gwt.tribefirejs.client.remote.api.RpcSession;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.provider.Holder;
import com.braintribe.provider.SingletonBeanProvider;

public class TfJsRpc {
	private String servicesUrl;
	
	public TfJsRpc(String servicesUrl) {
		super();
		this.servicesUrl = servicesUrl;
	}

	public Supplier<GwtGmWebRpcEvaluator> serviceRequestEvaluator = new SingletonBeanProvider<GwtGmWebRpcEvaluator>() {
		@Override
		public GwtGmWebRpcEvaluator create() throws Exception {
			GwtGmWebRpcEvaluator bean = publish(new GwtGmWebRpcEvaluator());
			bean.setServerUrl(servicesUrl);
			bean.setSessionIdProvider(sessionIdProvider.get());
			bean.setTypeEnsurer(typeEnsurer.get());
			bean.setSendSessionIdExpressive(false);
			return bean;
		}
	};
	
	public Supplier<Holder<String>> sessionIdProvider = new SingletonBeanProvider<Holder<String>>() {
		@Override
		public Holder<String> create() throws Exception {
			return new Holder<String>();
		}
	};
	
	public Supplier<CortexTypeEnsurer> typeEnsurer = new SingletonBeanProvider<CortexTypeEnsurer>() {
		@Override
		public CortexTypeEnsurer create() throws Exception {
			CortexTypeEnsurer bean = publish(new CortexTypeEnsurer());
			bean.setEvaluator(serviceRequestEvaluator.get());
			return bean;
		}
	};
	
	public static RpcSession createRpcSession(String sessionId) {
		TfJsRpc rpc = new TfJsRpc(sessionId);
		
		return new RpcSession() {
			@Override
			public Evaluator<ServiceRequest> evaluator() {
				return rpc.serviceRequestEvaluator.get();
			}
			
			@Override
			public Holder<String> sessionIdHolder() {
				return rpc.sessionIdProvider.get();
			}
		};
	}
}
