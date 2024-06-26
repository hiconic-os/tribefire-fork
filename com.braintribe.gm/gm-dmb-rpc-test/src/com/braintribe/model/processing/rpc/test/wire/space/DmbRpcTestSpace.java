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
package com.braintribe.model.processing.rpc.test.wire.space;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.model.processing.dmbrpc.client.BasicGmDmbRpcClientConfig;
import com.braintribe.model.processing.dmbrpc.client.GmDmbRpcClientConfig;
import com.braintribe.model.processing.dmbrpc.server.GmDmbRpcServer;
import com.braintribe.model.processing.rpc.commons.api.config.GmRpcClientConfig;
import com.braintribe.model.processing.rpc.test.commons.TestAuthenticatingUserSessionProvider;
import com.braintribe.model.processing.rpc.test.commons.TestRpcClientAuthorizationContext;
import com.braintribe.model.processing.rpc.test.service.iface.basic.BasicTestService;
import com.braintribe.model.processing.rpc.test.service.iface.streaming.StreamingTestService;
import com.braintribe.model.processing.rpc.test.wire.contract.DmbRpcTestContract;
import com.braintribe.model.usersession.UserSession;
import com.braintribe.utils.stream.api.StreamPipes;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.context.WireContextConfiguration;

@Managed
public class DmbRpcTestSpace implements DmbRpcTestContract {

	@Import
	private MetaSpace meta;

	@Import
	private ClientCommonsSpace clientCommons;

	@Import
	private ServerCommonsSpace serverCommons;

	@Import
	private MarshallingSpace marshalling;

	@Override
	public void onLoaded(WireContextConfiguration configuration) {
		server(); // start MBean RPC server
	}

	@Managed
	@Override
	public GmRpcClientConfig basic() {
		GmDmbRpcClientConfig bean = new GmDmbRpcClientConfig();
		bean.setServiceId(BasicTestService.ID);
		bean.setServiceInterface(BasicTestService.class);
		clientCommons.config(bean, false);
		return bean;
	}

	@Managed
	@Override
	public GmRpcClientConfig basicReauthorizable() {
		GmDmbRpcClientConfig bean = new GmDmbRpcClientConfig();
		bean.setServiceId(BasicTestService.ID);
		bean.setServiceInterface(BasicTestService.class);
		clientCommons.config(bean, true);
		return bean;
	}

	@Managed
	@Override
	public GmRpcClientConfig streaming() {
		GmDmbRpcClientConfig bean = new GmDmbRpcClientConfig();
		bean.setServiceId(StreamingTestService.ID);
		bean.setServiceInterface(StreamingTestService.class);
		clientCommons.config(bean, false);
		return bean;
	}

	@Managed
	@Override
	public GmRpcClientConfig streamingReauthorizable() {
		GmDmbRpcClientConfig bean = new GmDmbRpcClientConfig();
		bean.setServiceId(StreamingTestService.ID);
		bean.setServiceInterface(StreamingTestService.class);
		clientCommons.config(bean, true);
		return bean;
	}

	@Managed
	@Override
	public GmRpcClientConfig denotationDriven() {
		BasicGmDmbRpcClientConfig bean = new BasicGmDmbRpcClientConfig();
		clientCommons.config(bean, false);
		return bean;
	}

	@Managed
	@Override
	public GmRpcClientConfig denotationDrivenReauthorizable() {
		BasicGmDmbRpcClientConfig bean = new BasicGmDmbRpcClientConfig();
		clientCommons.config(bean, true);
		return bean;
	}

	@Override
	public Supplier<UserSession> currentUserSessionInvalidator() {
		TestAuthenticatingUserSessionProvider userSessionProvider = clientCommons.userSessionProvider();
		return userSessionProvider::invalidateCurrentUserSession;
	}

	@Override
	public Consumer<Throwable> authorizationFailureConsumer() {
		TestAuthenticatingUserSessionProvider userSessionProvider = clientCommons.userSessionProvider();
		return userSessionProvider::accept;
	}

	@Override
	public Set<Throwable> currentAuthorizationFailures() {
		TestRpcClientAuthorizationContext authContext = clientCommons.authContext();
		return authContext.getNotifiedFailures();
	}

	@Managed
	private GmDmbRpcServer server() {
		GmDmbRpcServer bean = new GmDmbRpcServer();
		bean.setEvaluator(serverCommons.serviceRequestEvaluator());
		bean.setMarshaller(marshalling.binMarshaller());
		bean.setStreamPipeFactory(StreamPipes.simpleFactory());
		return bean;
	}

}
