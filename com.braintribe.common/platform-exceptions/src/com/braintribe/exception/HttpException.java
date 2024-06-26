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

public class HttpException extends RuntimeException implements HasLogPreferences {

	private static final long serialVersionUID = 1L;

	private final int statusCode;
	private Object payload;
	private LogPreferences logPreferences;

	public HttpException(int status) {
		super();
		this.statusCode = status;
	}

	public HttpException(int status, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = status;
	}

	public HttpException(int status, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = status;
	}

	public HttpException(int status, String message) {
		super(message);
		this.statusCode = status;
	}

	public HttpException(int status, Throwable cause) {
		super(cause);
		this.statusCode = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public HttpException withPayload(Object payload) {
		this.payload = payload;
		return this;
	}

	public Object getPayload() {
		return payload;
	}

	public void setLogPreferences(LogPreferences logPreferences) {
		this.logPreferences = logPreferences;
	}

	@Override
	public LogPreferences getLogPreferences() {
		return logPreferences;
	}
}
