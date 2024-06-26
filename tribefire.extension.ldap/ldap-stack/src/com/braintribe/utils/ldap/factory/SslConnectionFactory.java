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
package com.braintribe.utils.ldap.factory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SslConnectionFactory extends SSLSocketFactory {
	
	private SSLSocketFactory socketFactory;

	@Override
	public Socket createSocket() throws IOException {
		return socketFactory.createSocket();
	}

	@Override
	public Socket createSocket(Socket s, InputStream consumed, boolean autoClose) throws IOException {
		 throw new UnsupportedOperationException();
	}

	public SslConnectionFactory() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { new SslConnectionFactoryTrustManager() }, new SecureRandom());
			socketFactory = ctx.getSocketFactory();
		} catch (Exception ex) {
			throw new RuntimeException("Error while trying to initialize default SslConnectionFactory.", ex);
		}
	}

	public static SocketFactory getDefault() {
		return new SslConnectionFactory();
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return socketFactory.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return socketFactory.getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		return socketFactory.createSocket(s, host, port, autoClose);
	}

	@Override
	public Socket createSocket(String string, int i) throws IOException, UnknownHostException {
		return socketFactory.createSocket(string, i);
	}

	@Override
	public Socket createSocket(String string, int i, InetAddress ia, int i1) throws IOException, UnknownHostException {
		return socketFactory.createSocket(string, i, ia, i1);
	}

	@Override
	public Socket createSocket(InetAddress ia, int i) throws IOException {
		return socketFactory.createSocket(ia, i);
	}

	@Override
	public Socket createSocket(InetAddress ia, int i, InetAddress ia1, int i1) throws IOException {
		return socketFactory.createSocket(ia, i, ia1, i1);
	}

	static class SslConnectionFactoryTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			//Accept all
		}
		@Override
		public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			//Accept all
		}
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[0];
		}
	}
}
