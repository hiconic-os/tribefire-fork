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
package com.braintribe.gwt.gme.websocket.client;

import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.security.client.SessionIdProvider;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

/**
 * This provider is responsible for generating WebSocketUrl with parameters
 * 
 * 
 */
public class WebSocketUrlProvider implements Supplier<String>, Function<String, String> {

	private String clientIdParameterName = "clientId";
    private String sessionIdParameterName = "sessionId";
    private String acceptParameterName = "accept";
    PersistenceGmSession gmSession;
	String basicWebSocketUrl = "";
	String accessIdProvider;
	SessionIdProvider sessionIdProvider;
	private String applicationId = null;
	
	@Required
	public void setWebSocketUrl(String webSocketUrl) {
		this.basicWebSocketUrl = webSocketUrl;
	}	
	
	@Configurable
	public void setAccessIdProvider(String accessId) {
		this.accessIdProvider = accessId;
	}
	
	@Configurable
	public void setPersistanceSession(PersistenceGmSession session) {
		this.gmSession = session;
	}
	
	@Configurable
	public void setSessionId(SessionIdProvider sessionId) {
		this.sessionIdProvider = sessionId;
	}
		
	@Configurable
    public void setSessionIdParameterName(String sessionIdParameterName) {
        this.sessionIdParameterName = sessionIdParameterName;
    }
	
	@Configurable
    public void setClientIdParameterName(String clientIdParameterName) {
        this.clientIdParameterName = clientIdParameterName;
    }	

	@Configurable
    public void setAcceptParameterName(String acceptParameterName) {
        this.acceptParameterName = acceptParameterName;
    }	

	@Required
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }		
		
	@Override
	public String get() {
		return apply(applicationId);
	}
	
	@Override
	public String apply(String clientId) {
		if (clientId == null)
			clientId = applicationId;
		StringBuilder url = new StringBuilder();
		url.append(basicWebSocketUrl);
        //add parameters
		url.append("?").append(this.sessionIdParameterName).append("=").append(sessionIdProvider.get());
		String accessId = (this.gmSession.getAccessId() != null) ? this.gmSession.getAccessId() : this.accessIdProvider;
		url.append("&").append(this.clientIdParameterName).append("=").append(clientId).append(".").append(accessId);
		url.append("&").append(this.acceptParameterName).append("=gm/jse");

		return url.toString();
	}

}
