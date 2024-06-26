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
package com.braintribe.exception;

import com.braintribe.logging.Logger.LogLevel;

public class LogPreferences {

	private LogLevel logLevel = null;
	private boolean includeThrowable = true;
	private LogLevel fullLogLevel = null;

	public LogPreferences() {
		// do nothing
	}

	public LogPreferences(LogLevel logLevel, boolean includeThrowable, LogLevel fullLogLevel) {
		super();
		this.logLevel = logLevel;
		this.includeThrowable = includeThrowable;
		this.fullLogLevel = fullLogLevel;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}
	public boolean isIncludeThrowable() {
		return includeThrowable;
	}
	public void setIncludeThrowable(boolean includeThrowable) {
		this.includeThrowable = includeThrowable;
	}
	public LogLevel getFullLogLevel() {
		return fullLogLevel;
	}
	public void setFullLogLevel(LogLevel fullLogLevel) {
		this.fullLogLevel = fullLogLevel;
	}

}
