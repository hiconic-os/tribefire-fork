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

public class CanceledException extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	public final static CanceledException emptyInstance = new CanceledException(true);

	public CanceledException() {
		super();
	}

	public CanceledException(String message, Throwable cause) {
		super(message, cause);
	}

	public CanceledException(String message) {
		super(message);
	}

	public CanceledException(Throwable cause) {
		super(cause);
	}
	
	private CanceledException(boolean emptyStackTrace) {
		super("canceled", null, false, !emptyStackTrace);
		if (emptyStackTrace)
			setStackTrace(new StackTraceElement[0]);
	}
	
}
