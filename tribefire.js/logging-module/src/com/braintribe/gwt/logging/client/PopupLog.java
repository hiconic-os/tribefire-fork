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

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;

public class PopupLog implements LogListener {
	private LogEventBuffer logEventBuffer;
	private LogPopup logPopup;
	private String title = "Braintribe GWT Log";
	private Formatter<String> formatter = new StandardStringFormatter();
	
	/**
	 * 
	 * @param logEventBuffer this buffer is used when getting {@link LogEvent} instances
	 * for the plain text display
	 */
	@Configurable @Required
	public void setLogEventBuffer(LogEventBuffer logEventBuffer) {
		this.logEventBuffer = logEventBuffer;
	}
	
	@Configurable
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void onLogEvent(LogEvent event) {
		if (isPopupOpen()) {
			appendLogEvent(event);
		}
	}

	protected void appendLogEvent(LogEvent event) {
		String color = "black";

		switch (event.getLevel()) {
		case DEBUG: color = "gray"; break;
		case ERROR: color = "red"; break;
		case FATAL: color = "magenta"; break;
		case INFO: color = "black"; break;
		case PROFILING: color = "blue"; break;
		case PROFILINGDEBUG: color = "lightblue"; break;
		case WARN: color = "#774400"; break;
		case TRACE: color = "orange"; break;
		}
		
		String line = formatter.format(event);
		
		logPopup.appendLine(line, color);
	}
	
	protected boolean isPopupOpen() {
		return logPopup != null && !logPopup.isClosed();
	}

	public void showPopup() {
		if (logPopup == null || logPopup.isClosed()) {
			logPopup = LogPopup.open(title);
			for (LogEvent event: logEventBuffer.getEvents()) {
				appendLogEvent(event);
			}
		}
		else {
			logPopup.focus();
		}
	}
}
