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
package com.braintribe.gwt.security.client;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class implements some of the methods in the SecurityService interface.
 * This should be the default implementation for every kind of different implementers
 * of the SecurityService interface.
 *
 */
public abstract class AbstractSecurityService implements SecurityService {
	private List<SessionListener> listeners = new ArrayList<SessionListener>();
	protected Session session;
	
	@Override
	public void addSessionListener(SessionListener sessionListener) {
		listeners.add(sessionListener);
	}
	
	@Override
	public void removeSessionListener(SessionListener sessionListener) {
		listeners.remove(sessionListener);
	}
	
	@Override
	public Session getSession() throws SecurityServiceException {
		return session;
	}
	
	protected void fireSessionCreated() {
		List<SessionListener> listenersCopy = new ArrayList<SessionListener>(listeners);
		for (SessionListener listener: listenersCopy) {
			listener.sessionCreated(this);
		}
	}
	
	protected void fireSessionClosed() {
		List<SessionListener> listenersCopy = new ArrayList<SessionListener>(listeners);
		for (SessionListener listener: listenersCopy) {
			listener.sessionClosed(this);
		}
	}
	protected void fireSessionClosing() {
		List<SessionListener> listenersCopy = new ArrayList<SessionListener>(listeners);
		for (SessionListener listener: listenersCopy)  {
			listener.sessionClosing(this);
		}
	}
}
