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
package tribefire.extension.sse.processing.util;

public class ConnectedClient {

	private String connectionId;
	private String clientId;
	private String lastSeenId;
	private String clientIp;
	private String username;
	private String sessionId;

	public ConnectedClient(String connectionId, String clientId, String lastSeenId, String clientIp, String username, String sessionId) {
		this.connectionId = connectionId;
		this.clientId = clientId;
		this.lastSeenId = lastSeenId;
		this.clientIp = clientIp;
		this.username = username;
		this.sessionId = sessionId;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public String getClientId() {
		return clientId;
	}

	public String getLastSeenId() {
		return lastSeenId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public String getUsername() {
		return username;
	}

	public String getSessionId() {
		return sessionId;
	}

}
