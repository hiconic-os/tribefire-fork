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

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HttpContext;

import com.braintribe.logging.Logger;
import com.braintribe.transport.http.util.AccessStatisticsOnConnections;

/**
 * A pooling manager that delegates all calls to the provided {@link PoolingHttpClientConnectionManager}.
 * In addition, it logs crucial method invocations at TRACE level to the logging framework.
 * 
 * Note that this wrapper has some performance and memory consumption impact as it keeps track of
 * the stack traces that requested and released connections.
 * 
 * This should help to identify where connections might not be returned to the pool.
 */
public class TracingPoolingHttpClientConnectionManager extends PoolingHttpClientConnectionManager {

	private static final Logger logger = Logger.getLogger(TracingPoolingHttpClientConnectionManager.class);
	
	private PoolingHttpClientConnectionManager delegate;
	private static AccessStatisticsOnConnections statistics = new AccessStatisticsOnConnections(logger, true, "Http Connections");

	public static TracingPoolingHttpClientConnectionManager wrap(PoolingHttpClientConnectionManager delegate) {
		TracingPoolingHttpClientConnectionManager wrapper = new TracingPoolingHttpClientConnectionManager();
		wrapper.delegate = delegate;
		return wrapper;
	}
	
	private TracingPoolingHttpClientConnectionManager() {
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public void close() {
		delegate.close();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public ConnectionRequest requestConnection(HttpRoute route, Object state) {
		logger.trace(() -> "Request connection to route: "+route);
		statistics.printConnectionStats(false);
		final ConnectionRequest request = delegate.requestConnection(route, state);
		
		return new ConnectionRequest() {
			@Override
			public boolean cancel() {
				return request.cancel();
			}

			@Override
			public HttpClientConnection get(long timeout, TimeUnit tunit) throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException {
				HttpClientConnection connection = request.get(timeout, tunit);
				statistics.registerAccess(connection);
				return connection;
			}
			
		};
	}

	@Override
	public void releaseConnection(HttpClientConnection managedConn, Object state, long keepalive, TimeUnit tunit) {
		logger.trace(() -> "Release connection: "+managedConn);
		statistics.registerDispose(managedConn);
		statistics.printConnectionStats(false);
		delegate.releaseConnection(managedConn, state, keepalive, tunit);
	}

	@Override
	public void connect(HttpClientConnection managedConn, HttpRoute route, int connectTimeout, HttpContext context) throws IOException {
		logger.trace(() -> "Connect using connection: "+managedConn+" to route "+route);
		delegate.connect(managedConn, route, connectTimeout, context);
	}

	@Override
	public void upgrade(HttpClientConnection managedConn, HttpRoute route, HttpContext context) throws IOException {
		logger.trace(() -> "Upgrade connection: "+managedConn+" to route "+route+" with context "+context);
		delegate.upgrade(managedConn, route, context);
	}

	@Override
	public void routeComplete(HttpClientConnection managedConn, HttpRoute route, HttpContext context) throws IOException {
		logger.trace(() -> "Route complete: "+managedConn);
		delegate.routeComplete(managedConn, route, context);
	}

	@Override
	public void shutdown() {
		delegate.shutdown();
	}

	@Override
	public void closeIdleConnections(long idleTimeout, TimeUnit tunit) {
		delegate.closeIdleConnections(idleTimeout, tunit);
	}

	@Override
	public void closeExpiredConnections() {
		delegate.closeExpiredConnections();
	}

	@Override
	public int getMaxTotal() {
		return delegate.getMaxTotal();
	}

	@Override
	public void setMaxTotal(int max) {
		delegate.setMaxTotal(max);
	}

	@Override
	public int getDefaultMaxPerRoute() {
		return delegate.getDefaultMaxPerRoute();
	}

	@Override
	public void setDefaultMaxPerRoute(int max) {
		delegate.setDefaultMaxPerRoute(max);
	}

	@Override
	public int getMaxPerRoute(HttpRoute route) {
		return delegate.getMaxPerRoute(route);
	}

	@Override
	public void setMaxPerRoute(HttpRoute route, int max) {
		delegate.setMaxPerRoute(route, max);
	}

	@Override
	public PoolStats getTotalStats() {
		return delegate.getTotalStats();
	}

	@Override
	public PoolStats getStats(HttpRoute route) {
		return delegate.getStats(route);
	}

	@Override
	public Set<HttpRoute> getRoutes() {
		return delegate.getRoutes();
	}

	@Override
	public SocketConfig getDefaultSocketConfig() {
		return delegate.getDefaultSocketConfig();
	}

	@Override
	public void setDefaultSocketConfig(SocketConfig defaultSocketConfig) {
		delegate.setDefaultSocketConfig(defaultSocketConfig);
	}

	@Override
	public ConnectionConfig getDefaultConnectionConfig() {
		return delegate.getDefaultConnectionConfig();
	}

	@Override
	public void setDefaultConnectionConfig(ConnectionConfig defaultConnectionConfig) {
		delegate.setDefaultConnectionConfig(defaultConnectionConfig);
	}

	@Override
	public SocketConfig getSocketConfig(HttpHost host) {
		return delegate.getSocketConfig(host);
	}

	@Override
	public void setSocketConfig(HttpHost host, SocketConfig socketConfig) {
		delegate.setSocketConfig(host, socketConfig);
	}

	@Override
	public ConnectionConfig getConnectionConfig(HttpHost host) {
		return delegate.getConnectionConfig(host);
	}

	@Override
	public void setConnectionConfig(HttpHost host, ConnectionConfig connectionConfig) {
		delegate.setConnectionConfig(host, connectionConfig);
	}

	@Override
	public int getValidateAfterInactivity() {
		return delegate.getValidateAfterInactivity();
	}

	@Override
	public void setValidateAfterInactivity(int ms) {
		delegate.setValidateAfterInactivity(ms);
	}
	
}
