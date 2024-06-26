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

import java.util.Date;

/**
 * Instances of this class will be sent as events by any logger
 * via the {@link LogManager}.
 * The event automatically gets the date of its creation.
 * @author Dirk
 *
 */
public class LogEvent {
	private static Long ID_COUNTER = 0l;
	private LogLevel level;
	private String message;
	private String category;
	private Date date;
	private Long id;
	
	public LogEvent(LogLevel level, String category, String message) {
		super();
		this.id = ID_COUNTER++;
		this.date = new Date();
		this.level = level;
		this.message = message;
		this.category = category;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public Date getDate() {
		return date;
	}
	
	public LogLevel getLevel() {
		return level;
	}
	
	public String getMessage() {
		return message;
	}
	
}
