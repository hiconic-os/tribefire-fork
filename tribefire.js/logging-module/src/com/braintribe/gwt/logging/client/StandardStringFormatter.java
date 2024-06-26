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
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Builds a single from a {@link LogEvent} by
 * putting any information of the LogEvent to it.
 * @author Dirk
 *
 */
public class StandardStringFormatter implements Formatter<String> {
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");

	/**
	 * 
	 * @param dateFormat is used to format the date of a {@link LogEvent} when formatting
	 * that event.
	 */
	@Configurable
	public void setDateFormat(DateTimeFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@Override
	public String format(LogEvent e) {
		String date = dateFormat.format(e.getDate());
		return date +" [" + e.getLevel() + "] " + e.getCategory() + ": " + e.getMessage();
	}
}
