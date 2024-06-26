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
package edu.ycp.cs.dh.acegwt.client.ace;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Ace command's argument could be either string or string-to-string map.
 */
public class AceCommandArgs {
	private final Object value;
	
	/**
	 * Create map argument. In case <code>data</code> is null map will be empty.
	 */
	public AceCommandArgs(Map<String, String> data) {
		value = JavaScriptObject.createObject();
		if (data != null)
			for (Map.Entry<String, String> entry : data.entrySet())
				with(entry.getKey(), entry.getValue());
	}
	
	/**
	 * Create text argument.
	 */
	public AceCommandArgs(String value) {
		this.value = value;
	}
	
	/**
	 * Add key-value pair to map.
	 */
	public native AceCommandArgs with(String argKey, String argValue) /*-{
		this.@edu.ycp.cs.dh.acegwt.client.ace.AceCommandArgs::value[argKey] = argValue;
		return this;
	}-*/;
	
	/**
	 * Give inner value.
	 * @return string or map depending on used constructor
	 */
	public Object getValue() {
		return value;
	}
}
