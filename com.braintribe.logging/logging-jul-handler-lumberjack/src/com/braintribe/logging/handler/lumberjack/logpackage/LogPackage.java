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
package com.braintribe.logging.handler.lumberjack.logpackage;

import java.util.Map;

public class LogPackage {

	protected int sequenceNumber = 1;
	protected String line = null;
	protected Map<String, String> properties = null;

	public LogPackage(int sequenceNumber, String line, Map<String, String> properties) {
		this.sequenceNumber = sequenceNumber;
		this.line = line;
		this.properties = properties;
	}

	public String getLine() {
		return line;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void addProperty(String key, String value) {
		if (key != null && value != null) {
			this.properties.put(key, value);
		}
	}
}
