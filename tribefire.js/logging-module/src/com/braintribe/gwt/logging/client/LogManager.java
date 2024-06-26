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

import com.braintribe.gwt.ioc.client.Configurable;

/**
 * This class distributes {@link LogEvent} instances to any registered
 * {@link LogListener}. Any {@link Logger} sends its events to this
 * manager.
 * @author Dirk
 *
 */
public class LogManager {
	
	protected static LogLevel logLevel = LogLevel.INFO;
	
	private static List<LogListener> listeners = new ArrayList<>();
	
	public static void addListener(LogListener listener) {
		listeners.add(listener);
	}
	
	public static void removeListener(LogListener listener) {
		listeners.remove(listener);
	}
	
	public static void fireLogEvent(LogEvent event) {
		for (LogListener listener: listeners) {
			listener.onLogEvent(event);
		}
	}
	
	public static boolean isTraceEnabled() {
		return logLevel.ordinal() >= LogLevel.TRACE.ordinal();
	}
	public static boolean isDebugEnabled() {
		return logLevel.ordinal() >= LogLevel.DEBUG.ordinal();
	}
	public static boolean isInfoEnabled() {
		return logLevel.ordinal() >= LogLevel.INFO.ordinal();
	}
	public static boolean isWarnEnabled() {
		return logLevel.ordinal() >= LogLevel.WARN.ordinal();
	}
	public static boolean isErrorEnabled() {
		return logLevel.ordinal() >= LogLevel.ERROR.ordinal();
	}
	public static boolean isLevelEnabled(final LogLevel logLevelParam) { //this.logelevl = INFO (6)..... isTRaceEnabled(9)?
		return logLevel.ordinal() >= logLevelParam.ordinal();
	}	
	
	public static LogLevel getLogLevel() {
		return logLevel;
	}
	@Configurable
	public static void setLogLevel(LogLevel logLevel) {
		LogManager.logLevel = logLevel;
	}
	
}
