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

/**
 * Instances of this class can be used to generate log events
 * with a specific category. Convenience methods for the
 * different log levels are available.
 * @author Dirk
 *
 */
public class Logger {
	private final String category;
	
	/**
	 * 
	 * @param clazz The name of this class will be used as category
	 */
	public Logger(Class<?> clazz) {
		this.category = clazz.getSimpleName();
	}
	
	/**
	 * 
	 * @param category This is used as category for log events in any logging call of this logger
	 */
	public Logger(String category) {
		this.category = category;
	}
	
	public void log(LogLevel level, String message) {
		if (LogManager.isLevelEnabled(level)) {
			LogManager.fireLogEvent(new LogEvent(level, category, message));
		}
	}
	
	public void log(LogLevel level, String message, Throwable t) {
		if (LogManager.isLevelEnabled(level)) {
			StringBuffer builder = new StringBuffer();
			builder.append(message);
			if (t != null) {
				builder.append("\n");
				builder.append(ExceptionUtil.format(t));
			}

			LogManager.fireLogEvent(new LogEvent(level, category, builder.toString()));
		}
	}
	
	public void log(LogLevel level, Throwable t) {
		if (LogManager.isLevelEnabled(level)) {
			StringBuffer builder = new StringBuffer();
			if (t != null) 
				builder.append(ExceptionUtil.format(t));

			LogManager.fireLogEvent(new LogEvent(level, category, builder.toString()));
		}
	}
	
	public void fatal(String message) {
		log(LogLevel.FATAL, message);
	}
	public void fatal(String message, Throwable t) {
		log(LogLevel.FATAL, message, t);
	}
	public void fatal(Throwable t) {
		log(LogLevel.FATAL, t);
	}
		
	public void warn(String message) {
		log(LogLevel.WARN, message);
	}	
	public void warn(String message, Throwable t) {
		log(LogLevel.WARN, message, t);
	}
	public void warn(Throwable t) {
		log(LogLevel.WARN, t);
	}
	
	
	public void error(String message) {
		log(LogLevel.ERROR, message);
	}
	public void error(String message, Throwable t) {
		log(LogLevel.ERROR, message, t);
	}	
	public void error(Throwable t) {
		log(LogLevel.ERROR, t);
	}
	
		
	public void info(String message) {
		log(LogLevel.INFO, message);
	}
	public void info(String message, Throwable t) {
		log(LogLevel.INFO, message, t);
	}
	public void info(Throwable t) {
		log(LogLevel.INFO, t);
	}
	
	public void debug(String message) {
		log(LogLevel.DEBUG, message);
	}
	public void debug(String message, Throwable t) {
		log(LogLevel.DEBUG, message, t);
	}
	public void debug(Throwable t) {
		log(LogLevel.DEBUG, t);
	}


	public void trace(String message) {
		log(LogLevel.TRACE, message);
	}
	public void trace(String message, Throwable t) {
		log(LogLevel.TRACE, message, t);
	}
	public void trace(Throwable t) {
		log(LogLevel.TRACE, t);
	}

	public boolean isTraceEnabled() {
		return LogManager.isTraceEnabled();
	}
	public boolean isDebugEnabled() {
		return LogManager.isDebugEnabled();
	}
	public boolean isInfoEnabled() {
		return LogManager.isInfoEnabled();
	}
	public boolean isWarnEnabled() {
		return LogManager.isWarnEnabled();
	}
	public boolean isErrorEnabled() {
		return LogManager.isErrorEnabled();
	}
	public boolean isLevelEnabled(final LogLevel logLevel) {
		return LogManager.isLevelEnabled(logLevel);
	}
}
