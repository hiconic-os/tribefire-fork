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
package com.braintribe.transport.ssl.impl;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.braintribe.transport.ssl.SslSocketFactoryProvider;

public class EasySslSocketFactoryProvider implements SslSocketFactoryProvider {
	protected String securityProtocol = Constants.DEFAULT_SECURITY_PROTOCOL;

	private SecureRandom secureRandom = new SecureRandom();
	
	@Override
	public SSLSocketFactory provideSSLSocketFactory() throws Exception {
		SSLContext sslContext = this.provideSSLContext();
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();		
		return sslSocketFactory;
	}
	
	@Override
	public SSLContext provideSSLContext() throws Exception {
		SSLContext sslContext = SSLContext.getInstance(this.securityProtocol);

		// set up a TrustManager that trusts everything
		sslContext.init(null, new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				return;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				return;
			}
		} }, secureRandom);
		
		return sslContext;
	}

	public String getSecurityProtocol() {
		return securityProtocol;
	}
	public void setSecurityProtocol(String securityProtocol) {
		this.securityProtocol = securityProtocol;
	}
}
