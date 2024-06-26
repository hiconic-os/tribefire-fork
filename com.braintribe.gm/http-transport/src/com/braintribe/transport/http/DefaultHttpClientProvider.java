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
package com.braintribe.transport.http;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.braintribe.cfg.Configurable;
import com.braintribe.common.lcd.Numbers;
import com.braintribe.logging.Logger;
import com.braintribe.transport.ssl.SslSocketFactoryProvider;
import com.braintribe.transport.ssl.impl.EasySslSocketFactoryProvider;
import com.braintribe.utils.Base64;
import com.braintribe.utils.lcd.StringTools;

public class DefaultHttpClientProvider implements HttpClientProvider {

	private static final Logger logger = Logger.getLogger(DefaultHttpClientProvider.class);

	protected SslSocketFactoryProvider sslSocketFactoryProvider = new EasySslSocketFactoryProvider();
	protected int maxTotal = 8192;
	protected int maxPerRoute = 8192;
	protected int socketTimeout = -1;
	protected boolean tracePooling = false;
	protected long poolTimeToLive = -1;
	protected long validateAfterInactivity = Numbers.MILLISECONDS_PER_SECOND * 10;
	protected long maxIdleConnectionTtl = Numbers.MILLISECONDS_PER_MINUTE * 5;

	@Override
	public CloseableHttpClient provideHttpClient() throws Exception {
		HttpClientBuilder builder = this.provideClientBuilder(null);
		return builder.build();
	}

	@Override
	public CloseableHttpClient provideHttpClient(ClientParameters clientParameters) throws Exception {
		HttpClientBuilder builder = this.provideClientBuilder(clientParameters);
		return builder.build();
	}

	@SuppressWarnings("resource")
	@Override
	public HttpClientBuilder provideClientBuilder(ClientParameters clientParameters) throws Exception {

		if (logger.isTraceEnabled()) {
			logger.trace("Creating a client builder with parameters: " + clientParameters);
		}

		if (clientParameters == null) {
			clientParameters = ClientParameters.emptyParameters;
		}

		PoolingHttpClientConnectionManager cxMgr = null;

		try {

			if (this.sslSocketFactoryProvider != null) {
				SSLConnectionSocketFactory sslConnectionFactory = new SocksProxyAwareSslConnectionFactory(
						this.sslSocketFactoryProvider.provideSSLContext(), new NoopHostnameVerifier());
				Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
						.register("https", sslConnectionFactory).register("http", new PlainConnectionSocketFactory()).build();

				cxMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry, null, null, null, poolTimeToLive, TimeUnit.MILLISECONDS);
			} else {
				cxMgr = new PoolingHttpClientConnectionManager(poolTimeToLive, TimeUnit.MILLISECONDS);
			}
			cxMgr.setMaxTotal(getValue(clientParameters.getMaxTotal(), this.maxTotal));
			cxMgr.setDefaultMaxPerRoute(getValue(clientParameters.getMaxPerRoute(), this.maxPerRoute));
			cxMgr.setValidateAfterInactivity(getValue(clientParameters.getValidateAfterInactivity(), validateAfterInactivity).intValue());
			cxMgr.closeIdleConnections(getValue(clientParameters.getMaxIdleConnectionTtl(), maxIdleConnectionTtl).longValue(), TimeUnit.MILLISECONDS);

			if (getValue(clientParameters.getTracePooling(), tracePooling)) {
				cxMgr = TracingPoolingHttpClientConnectionManager.wrap(cxMgr);
			}

			HttpClientBuilder builder = HttpClients.custom().setConnectionManager(cxMgr);

			String httpProxyHost = getProperty("http.proxyHost");
			String httpProxyPort = getProperty("http.proxyPort");
			if (httpProxyHost != null && httpProxyPort != null) {
				HttpHost proxy = new HttpHost(httpProxyHost, Integer.parseInt(httpProxyPort), "http");
				builder.setProxy(proxy);
			} else {
				String httpsProxyHost = getProperty("https.proxyHost");
				String httpsProxyPort = getProperty("https.proxyPort");
				if (httpsProxyHost != null && httpsProxyPort != null) {
					HttpHost proxy = new HttpHost(httpsProxyHost, Integer.parseInt(httpsProxyPort), "https");
					builder.setProxy(proxy);
				}
			}

			Integer timeout = getValue(clientParameters.getSocketTimeout(), this.socketTimeout);
			if (timeout > 0) {
				SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(timeout).build();
				builder.setDefaultSocketConfig(socketConfig);
				cxMgr.setDefaultSocketConfig(socketConfig);
			}

			// install stale workaround
			builder.setRetryHandler(new StaleAwareHttpRequestRetryHandler(cxMgr));

			builder.addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {

				if (request instanceof HttpRequestWrapper) {
					HttpRequest original = ((HttpRequestWrapper) request).getOriginal();
					if (original instanceof HttpUriRequest) {
						String userInfo = ((HttpUriRequest) original).getURI().getUserInfo();

						if (userInfo != null) {
							String encodedAuth = Base64.encodeBytes(userInfo.getBytes(StandardCharsets.ISO_8859_1));
							String authHeader = "Basic " + encodedAuth;
							request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
						}
					}
				}
			});

			return builder;

		} catch (Exception e) {
			if (cxMgr != null) {
				try {
					cxMgr.close();
				} catch (Exception ignore) {
					/* Ignore */ }
			}
			throw new Exception("Could not create HTTP client with parameters: " + clientParameters, e);
		}
	}

	private static <T> T getValue(T parameterValue, T configurationValue) {
		if (parameterValue != null) {
			return parameterValue;
		}
		return configurationValue;
	}

	@Override
	public HttpClientBuilder provideClientBuilder() throws Exception {
		return provideClientBuilder(null);
	}

	protected static String getProperty(String name) {
		String value = System.getProperty(name);
		if (!StringTools.isBlank(value)) {
			return value;
		}
		value = System.getenv(name);
		if (!StringTools.isBlank(value)) {
			return value;
		}
		return null;
	}

	@Configurable
	public void setSslSocketFactoryProvider(SslSocketFactoryProvider sslSocketFactoryProvider) {
		this.sslSocketFactoryProvider = sslSocketFactoryProvider;
	}
	@Configurable
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	@Configurable
	public void setMaxPerRoute(int maxPerRoute) {
		this.maxPerRoute = maxPerRoute;
	}
	@Configurable
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	@Configurable
	public void setTracePooling(boolean tracePooling) {
		this.tracePooling = tracePooling;
	}
	@Configurable
	public void setPoolTimeToLive(long poolTimeToLive) {
		this.poolTimeToLive = poolTimeToLive;
	}
	@Configurable
	public void setValidateAfterInactivity(long validateAfterInactivity) {
		this.validateAfterInactivity = validateAfterInactivity;
	}
	@Configurable
	public void setMaxIdleConnectionTtl(long maxIdleConnectionTtl) {
		this.maxIdleConnectionTtl = maxIdleConnectionTtl;
	}
}
