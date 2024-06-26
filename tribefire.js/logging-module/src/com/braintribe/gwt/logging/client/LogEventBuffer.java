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
package com.braintribe.gwt.logging.client;

import java.util.ArrayList;
import java.util.List;

/** 
 * A simple implementation of {@link LogListener}
 * that is able to hold a defined number of events.
 * If this number would be exceeded old ones become
 * removed.
 * @author Dirk
 *
 */
public class LogEventBuffer implements LogListener {
	
	private List<LogEvent> events = new ArrayList<>();
	private int maxEvents = 200;
	private LogEventBufferListener listener;
	
	public void setMaxEvents(int maxEvents) {
		this.maxEvents = maxEvents;
	}
	
	public void appendLogEvent(LogEvent event) {
		events.add(event);
		//events.addLast(event);
		if (events.size() > maxEvents) events.remove(0);
		//if (events.size() > maxEvents) events.removeFirst();
		
		if (listener != null)
			listener.onLogEventAdded();
	}
	
	public void setListener(LogEventBufferListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onLogEvent(LogEvent event) {
		appendLogEvent(event);
	}
	
	/**
	 * 
	 * @return The list of events in the order they were received
	 */
	public List<LogEvent> getEvents() {
		return events;
	}
	
	public interface LogEventBufferListener {
		
		public void onLogEventAdded();
		
	}
}
