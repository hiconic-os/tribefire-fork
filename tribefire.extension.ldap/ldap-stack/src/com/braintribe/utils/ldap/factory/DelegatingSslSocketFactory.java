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

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import com.braintribe.utils.ldap.LdapConnectionStack;

/**
 * Implementation of a {@link SSLSocketFactory} that has a static delegate. This class is necessary because
 * the LDAP implementation of Java only allows to provide the classname of an SSL Context Factory
 * and not a specific instance. This class bridges this gap and is used in {@link LdapConnectionStack}.
 */
public class DelegatingSslSocketFactory extends SSLSocketFactory {

	private static SSLSocketFactory delegate;

	public static void setDelegate(SSLSocketFactory delegate) {
		DelegatingSslSocketFactory.delegate = delegate;
	}
	
	public static SocketFactory getDefault() {
		return new DelegatingSslSocketFactory();
	}


	@Override
	public Socket createSocket() throws IOException {
		return delegate.createSocket();
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException {
		return delegate.createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		return delegate.createSocket(arg0, arg1);
	}

	@Override
	public Socket createSocket(Socket s, InputStream consumed, boolean autoClose) throws IOException {
		return delegate.createSocket(s, consumed, autoClose);
	}

	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		return delegate.createSocket(s, host, port, autoClose);
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3) throws IOException, UnknownHostException {
		return delegate.createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {
		return delegate.createSocket(arg0, arg1);
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return delegate.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return delegate.getSupportedCipherSuites();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public String toString() {
		return "Delegating: "+delegate.toString();
	}
	
}
