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
package com.braintribe.utils.logging;

import com.braintribe.utils.CommonTools;

/**
 * Utility class for operations related to {@link com.braintribe.logging.Logger.LogLevel} and
 * {@link com.braintribe.model.logging.LogLevel}
 * 
 *
 */
public class LogLevels {

	/**
	 * Convert LogLevel described by {@link com.braintribe.model.logging.LogLevel} to the corresponding
	 * {@link com.braintribe.logging.Logger.LogLevel} of the logger. If logLevel is null then null will be returned.
	 * 
	 * @param logLevel
	 *            {@link com.braintribe.model.logging.LogLevel}
	 * @return {@link com.braintribe.logging.Logger.LogLevel}
	 */
	public static com.braintribe.logging.Logger.LogLevel convert(com.braintribe.model.logging.LogLevel logLevel) {
		if (logLevel == null) {
			return null;
		}
		switch (logLevel) {
			case TRACE:
				return com.braintribe.logging.Logger.LogLevel.TRACE;
			case DEBUG:
				return com.braintribe.logging.Logger.LogLevel.DEBUG;
			case INFO:
				return com.braintribe.logging.Logger.LogLevel.INFO;
			case WARN:
				return com.braintribe.logging.Logger.LogLevel.WARN;
			case ERROR:
				return com.braintribe.logging.Logger.LogLevel.ERROR;
			default:
				throw new IllegalArgumentException(
						"LogLevel value: '" + logLevel + "' of type: '" + com.braintribe.model.logging.LogLevel.class.getName()
								+ "'  not supported from '" + com.braintribe.logging.Logger.LogLevel.class.getName() + "'");
		}
	}

	/**
	 * Convert a LogLevel as {@link String} based on {@link com.braintribe.model.logging.LogLevel} to the corresponding
	 * {@link com.braintribe.logging.Logger.LogLevel} of the logger.
	 * 
	 * @param logLevelAsString
	 *            {@link com.braintribe.model.logging.LogLevel} as {@link String}
	 * @return {@link com.braintribe.logging.Logger.LogLevel}
	 */
	public static com.braintribe.logging.Logger.LogLevel convert(String logLevelAsString) {
		if (CommonTools.isEmpty(logLevelAsString)) {
			throw new IllegalArgumentException("LogLevel needs to be set but is '" + logLevelAsString + "'");
		}
		com.braintribe.model.logging.LogLevel logLevel = com.braintribe.model.logging.LogLevel.valueOf(logLevelAsString);
		return convert(logLevel);
	}

}
