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
package com.braintribe.model.ldapconnectiondeployment;

import java.util.Map;

import com.braintribe.model.deployment.connector.Connector;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


public interface LdapConnection extends Connector {

	final EntityType<LdapConnection> T = EntityTypes.T(LdapConnection.class);
	
	String connectionUrl = "connectionUrl";
	String username = "username";
	String password = "password";
	String initialContextFactory = "initialContextFactory";
	String referralFollow = "referralFollow";
	String useTLSExtension = "useTLSExtension";
	String connectTimeout = "connectTimeout";
	String dnsTimeoutInitial = "dnsTimeoutInitial";
	String dnsTimeoutRetries = "dnsTimeoutRetries";
	String environmentSettings = "environmentSettings";
	
	void setConnectionUrl(String connectionUrl);
	@Initializer("'ldap://<host>:389'")
	String getConnectionUrl();

	void setUsername(String username);
	String getUsername();

	void setPassword(String password);
	String getPassword();

	void setInitialContextFactory(String initialContextFactory);
	@Initializer("'com.sun.jndi.ldap.LdapCtxFactory'")
	String getInitialContextFactory();

	void setReferralFollow(boolean referralFollow);
	@Initializer("false")
	boolean getReferralFollow();

	void setUseTLSExtension(boolean useTLSExtension);
	boolean getUseTLSExtension();

	void setConnectTimeout(long connectTimeout);
	@Initializer("30000L")
	long getConnectTimeout();

	void setDnsTimeoutInitial(long dnsTimeoutInitial);
	@Initializer("10000L")
	long getDnsTimeoutInitial();

	void setDnsTimeoutRetries(int dnsTimeoutRetries);
	@Initializer("3")
	int getDnsTimeoutRetries();

	void setEnvironmentSettings(Map<String,String> environmentSettings);
	Map<String,String> getEnvironmentSettings();

}
