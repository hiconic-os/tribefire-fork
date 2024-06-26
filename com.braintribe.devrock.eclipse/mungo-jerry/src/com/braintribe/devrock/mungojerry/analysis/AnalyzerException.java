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
package com.braintribe.devrock.mungojerry.analysis;

public class AnalyzerException extends Exception {
	private static final long serialVersionUID = -2664177611056306015L;

	public AnalyzerException() {	
	}

	public AnalyzerException(String message) {
		super(message);	
	}

	public AnalyzerException(Throwable cause) {
		super(cause);
	}

	public AnalyzerException(String message, Throwable cause) {
		super(message, cause);
	}
}
