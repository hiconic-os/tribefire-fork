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
package com.braintribe.model.processing.deployment.hibernate.mapping.exception;

public class HbmXmlGeneratingServiceException extends RuntimeException {

	private static final long serialVersionUID = -820172865406092354L;

	public HbmXmlGeneratingServiceException(String message) {
		super(message);
	}

	public HbmXmlGeneratingServiceException(Throwable cause) {
		super(cause);
	}

	public HbmXmlGeneratingServiceException(String message, Throwable cause) {
		super(cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty() ? message + ": " + cause.getMessage() : message, cause);
	}

}