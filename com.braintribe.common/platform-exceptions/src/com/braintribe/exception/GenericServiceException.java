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

public class GenericServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String serviceExceptionType;

	public GenericServiceException() {
		super();
	}

	public GenericServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericServiceException(String message) {
		super(message);
	}

	public GenericServiceException(String message, String serviceExceptionType) {
		super(message);
		this.serviceExceptionType = serviceExceptionType;
	}

	public GenericServiceException(Throwable cause) {
		super(cause);
	}

	public String getServiceExceptionType() {
		return serviceExceptionType;
	}

}
