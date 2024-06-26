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
package com.braintribe.logging.juli.filters.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class EnabledLogLevels {

	private int from = Level.FINEST.intValue();
	private int to = Level.SEVERE.intValue();

	public void setFrom(int from) {
		this.from = from;
	}
	public void setTo(int to) {
		this.to = to;
	}

	public void setFrom(String from) {
		if (from == null || from.trim().length() == 0) {
			return;
		}
		Level parsedLevel = Level.parse(from);
		this.from = parsedLevel.intValue();
	}
	public void setTo(String to) {
		if (to == null || to.trim().length() == 0) {
			return;
		}
		Level parsedLevel = Level.parse(to);
		this.to = parsedLevel.intValue();
	}

	public boolean enabled(LogRecord logRecord) {
		int level = logRecord.getLevel().intValue();
		return (this.from <= level && level <= this.to);
	}
}
