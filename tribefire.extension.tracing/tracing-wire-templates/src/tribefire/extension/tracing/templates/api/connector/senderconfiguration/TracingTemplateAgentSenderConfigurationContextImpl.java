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
package tribefire.extension.tracing.templates.api.connector.senderconfiguration;

/**
 *
 */
public class TracingTemplateAgentSenderConfigurationContextImpl extends TracingTemplateSenderConfigurationContextImpl
		implements TracingTemplateAgentSenderConfigurationContext, TracingTemplateAgentSenderConfigurationContextBuilder {

	private String host;
	private int port;

	@Override
	public TracingTemplateAgentSenderConfigurationContextBuilder setHost(String host) {
		this.host = host;
		return this;
	}

	@Override
	public TracingTemplateAgentSenderConfigurationContextBuilder setPort(int port) {
		this.port = port;
		return this;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public TracingTemplateAgentSenderConfigurationContext build() {
		return this;
	}

	@Override
	public String toString() {
		return "TracingTemplateAgentSenderConfigurationContextImpl [host=" + host + ", port=" + port + "]";
	}

}
