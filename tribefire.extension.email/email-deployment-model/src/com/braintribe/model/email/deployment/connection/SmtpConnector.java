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
package com.braintribe.model.email.deployment.connection;

import com.braintribe.model.descriptive.HasCredentials;
import com.braintribe.model.email.deployment.service.HasProxy;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.ToStringInformation;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 *
 */
@ToStringInformation("${#type_short} - (${id}) ${smtpHostName} - ${smtpPort} - ${protocol} - ${user}")
@SelectiveInformation("SMTP Connector ${smtpHostName}")
public interface SmtpConnector extends SendConnector, HasCredentials, HasProxy {

	final EntityType<SmtpConnector> T = EntityTypes.T(SmtpConnector.class);

	@Name("Login User")
	@Description("The user that should be used for authentication. If this user is not set then the 'user' is also used for login.")
	String getLoginUser();
	void setLoginUser(String loginUser);

	@Name("SMTP Host")
	@Description("The hostname or IP address of the SMTP server. Example: smtp.gmail.com")
	@Mandatory
	String getSmtpHostName();
	void setSmtpHostName(String smtpHostName);

	@Name("SMTP Port")
	@Description("The port of the SMTP server.")
	@Mandatory
	@Initializer("587")
	int getSmtpPort();
	void setSmtpPort(int smtpPort);

	@Name("Security Protocol")
	@Description("The security protocol that should be used for connecting to the server. At the moment, only TLS is supported.")
	@Initializer("enum(com.braintribe.model.email.deployment.connection.TransportStrategy,SMTP_TLS)")
	TransportStrategy getTransportStrategy();
	void setTransportStrategy(TransportStrategy protocol);

	@Name("Proxy Bridge Port")
	@Description("The port of the SOCKS5 proxy on localhost. When this is not set, 0 or negative, no proxy will be used.")
	Integer getProxyBridgePort();
	void setProxyBridgePort(Integer proxyBridgePort);

	@Name("Connection Timeout")
	@Description("General connection timeout (applied for socket connect-, read- and write timeouts). If not set, the default of 1 min will be used.")
	Integer getConnectionTimeoutInMs();
	void setConnectionTimeoutInMs(Integer connectionTimeoutInMs);

	@Name("Thread Pool Size")
	@Description("The number of threads to be used when sending emails. The default size is 4.")
	Integer getThreadPoolSize();
	void setThreadPoolSize(Integer threadPoolSize);

	@Name("Connection Pool Core Size")
	@Description("The minimal number of connections to be kept open at all times to the mail server. The default is 0.")
	Integer getConnectionPoolCoreSize();
	void setConnectionPoolCoreSize(Integer connectionPoolCoreSize);

	@Name("Connection Pool Max Size")
	@Description("The maximum number of parallel open connections to the mail server. The default is 4.")
	Integer getConnectionPoolMaxSize();
	void setConnectionPoolMaxSize(Integer connectionPoolMaxSize);

	@Name("Send Asynchronously")
	@Description("Send emails asynchronously.")
	Boolean getSendAsync();
	void setSendAsync(Boolean sendAsync);
}
