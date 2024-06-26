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
 * This enum is used to define different levels
 * of log levels. Any {@link LogEvent} has
 * one level and could by some filter mechanism
 * filtered using that level.
 * @author Dirk
 *
 */
public enum LogLevel implements Comparable<LogLevel>{
	FATAL(0), ERROR(3), WARN(4), INFO(6), DEBUG(7), PROFILING(8), PROFILINGDEBUG(9), TRACE(10);
	
	private int level;
	
	private LogLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
}
