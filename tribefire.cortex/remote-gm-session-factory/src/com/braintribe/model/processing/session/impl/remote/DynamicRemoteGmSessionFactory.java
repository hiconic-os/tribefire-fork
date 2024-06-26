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
package com.braintribe.model.processing.session.impl.remote;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.codec.marshaller.api.MarshallerRegistry;
import com.braintribe.codec.marshaller.bin.Bin2Marshaller;
import com.braintribe.codec.marshaller.common.BasicConfigurableMarshallerRegistry;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.bootstrapping.TribefireRuntime;
import com.braintribe.model.processing.rpc.commons.api.authorization.RpcClientAuthorizationContext;
import com.braintribe.model.processing.session.api.persistence.auth.SessionAuthorization;
import com.braintribe.model.processing.session.impl.remote.copied.GmWebRpcClientMetaDataProvider_Copied;
import com.braintribe.model.processing.webrpc.client.BasicGmWebRpcClientConfig;
import com.braintribe.model.processing.webrpc.client.GmWebRpcClientAuthorizationContext;
import com.braintribe.model.processing.webrpc.client.GmWebRpcEvaluator;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * <p>
 * A {@link com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory} which relies on a user
 * session id provider, and has no (re-)authentication capabilities.
 */
public class DynamicRemoteGmSessionFactory extends AbstractRemoteGmSessionFactory {

	private static Logger logger = Logger.getLogger(DynamicRemoteGmSessionFactory.class);

	private Supplier<String> baseUrlProvider = TribefireRuntime::getServicesUrl;
	private Marshaller binMarshaller;
	private Supplier<Map<String, Object>> metaDataProvider;

	@Configurable
	public void setBaseUrlProvider(Supplier<String> baseUrlProvider) {
		this.baseUrlProvider = baseUrlProvider;
	}

	@Override
	protected URL getStreamingUrl() throws GmSessionException {
		try {
			String baseUrl = this.baseUrlProvider.get();
			if (!baseUrl.endsWith("/")) {
				baseUrl += "/";
			}
			String url = baseUrl + "streaming";
			return new URL(url);
		} catch (Exception e) {
			throw new GmSessionException(e);
		}
	}

	/* @see com.braintribe.model.processing.session.impl.remote.AbstractRemoteGmSessionFactory#getMarshallerRegistry()
	 * 
	 * <entry key="application/xml" value-ref="rpc.marshaller.xml" /> <entry key="text/xml" value-ref="rpc.marshaller.xml"
	 * /> <entry key="gm/xml" value-ref="rpc.marshaller.xml" />
	 * 
	 * <entry key="application/stream+xml" value-ref="rpc.marshaller.xmlStream" /> <entry key="text/stream+xml"
	 * value-ref="rpc.marshaller.xmlStream" /> <entry key="gm/stream+xml" value-ref="rpc.marshaller.xmlStream" />
	 * 
	 * <entry key="application/gm" value-ref="rpc.marshaller.bin" /> <entry key="gm/bin" value-ref="rpc.marshaller.bin" />
	 * 
	 * <entry key="application/json" value-ref="rpc.marshaller.json" /> <entry key="text/x-json"
	 * value-ref="rpc.marshaller.json" /> <entry key="gm/json" value-ref="rpc.marshaller.json" /> */
	@Override
	protected MarshallerRegistry getMarshallerRegistry() {

		if (marshallerRegistry == null) {
			BasicConfigurableMarshallerRegistry newMarshallerRegistry = new BasicConfigurableMarshallerRegistry();
			newMarshallerRegistry.registerMarshaller("application/gm", getBinMarshaller());
			newMarshallerRegistry.registerMarshaller("gm/bin", getBinMarshaller());
			super.marshallerRegistry = newMarshallerRegistry;
		}

		return marshallerRegistry;

	}

	private Marshaller getBinMarshaller() {
		if (binMarshaller == null) {
			binMarshaller = new Bin2Marshaller();
		}
		return binMarshaller;
	}

	@Configurable
	public void setBinMarshaller(Marshaller binMarshaller) {
		this.binMarshaller = binMarshaller;
	}

	private class DefaultMetaDataProvider implements Supplier<Map<String, Object>> {
		@Override
		public Map<String, Object> get() throws RuntimeException {
			Map<String, Object> metaData = new HashMap<>();
			metaData.put("sessionId", getSessionIdProvider().get());
			return metaData;
		}
	}

	public Supplier<Map<String, Object>> getMetaDataProvider() {
		if (metaDataProvider == null) {
			metaDataProvider = new DefaultMetaDataProvider();
		}
		return metaDataProvider;
	}
	@Configurable
	public void setMetaDataProvider(Supplier<Map<String, Object>> metaDataProvider) {
		this.metaDataProvider = metaDataProvider;
	}

	@Override
	protected SessionAuthorization getSessionAuthorization() {
		return new UserSessionSupplierBasedSessionAuthorization(this::getUserSession);
	}

	@Override
	protected Evaluator<ServiceRequest> createRequestEvaluator() {

		BasicGmWebRpcClientConfig config = new BasicGmWebRpcClientConfig();
		config.setUrl(getRpcUrl().toExternalForm());
		config.setMarshaller(getBinMarshaller());
		config.setContentType("gm/bin");
		config.setExecutorService(executorService);
		config.setHttpClientProvider(httpClientProvider);
		config.setAuthorizationContext(createRpcClientAuthorizationContext());
		config.setMetaDataProvider(clientMetaDataProvider(super::getSessionId));
		config.setStreamPipeFactory(streamPipeFactory);

		return GmWebRpcEvaluator.create(config);
	}

	private RpcClientAuthorizationContext<Throwable> createRpcClientAuthorizationContext() {

		if (authorizationFailureListener == null) {
			return null;
		}

		GmWebRpcClientAuthorizationContext authorizationContext = new GmWebRpcClientAuthorizationContext();
		authorizationContext.setAuthorizationFailureListener(authorizationFailureListener);
		authorizationContext.setMaxRetries(2);

		return authorizationContext;

	}

	protected URL getRpcUrl() {
		try {
			String baseUrl = this.baseUrlProvider.get();
			if (!baseUrl.endsWith("/")) {
				baseUrl += "/";
			}
			String url = baseUrl + "rpc";
			return new URL(url);
		} catch (Exception e) {
			throw new GmSessionException(e);
		}
	}

	protected Supplier<Map<String, Object>> clientMetaDataProvider(Supplier<String> sessionIdProvider) {
		GmWebRpcClientMetaDataProvider_Copied bean = new GmWebRpcClientMetaDataProvider_Copied();
		bean.setSessionIdProvider(sessionIdProvider);
		bean.setIncludeNdc(true);
		bean.setIncludeNodeId(true);
		bean.setIncludeThreadName(true);
		return bean;
	}
}
