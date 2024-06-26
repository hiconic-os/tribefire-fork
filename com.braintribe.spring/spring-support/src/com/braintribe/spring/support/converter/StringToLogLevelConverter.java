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
package com.braintribe.spring.support.converter;

import org.springframework.core.convert.converter.Converter;

import com.braintribe.logging.Logger.LogLevel;

public class StringToLogLevelConverter implements Converter<String, LogLevel>{
	
	@Override
	public LogLevel convert(String source) {
		if (source == null) {
			return null;
		} else {
			if (source.equalsIgnoreCase("TRACE")) {
				return LogLevel.TRACE;
			} else if (source.equalsIgnoreCase("DEBUG")) {
				return LogLevel.DEBUG;
			} else if (source.equalsIgnoreCase("INFO")) {
				return LogLevel.INFO;
			} else if (source.equalsIgnoreCase("WARN")) {
				return LogLevel.WARN;
			} else if (source.equalsIgnoreCase("ERROR")) {
				return LogLevel.ERROR;
			} else {
				LogLevel level = LogLevel.valueOf(source);
				return level;
			}
		}		
	}
}
