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
package com.braintribe.gwt.async.client;

@SuppressWarnings("serial")
public class StatusCodeException extends Exception {
	private int statusCode;
	private String statusText;
	private String responseText;
	
	public StatusCodeException(int statusCode, String statusText, String responseText) {
		super(statusCode + ": " + statusText + "\n" + responseText);
		this.statusCode = statusCode;
		this.responseText = responseText;
		this.statusText = statusText;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public String getStatusText() {
		return statusText;
	}
	
	public String getResponseText() {
		return responseText;
	}
}
