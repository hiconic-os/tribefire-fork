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

public class ThrowableNormalizer {

	private final Throwable throwable;
	
	private boolean checked = false;
	private boolean isException = false;
	private boolean isError = false;

	public ThrowableNormalizer(Throwable throwable) {
		this.throwable = throwable;
		if (throwable instanceof Error) {
			checked = false;
			isException = false;
			isError = true;
		} else if (throwable instanceof RuntimeException) {
			checked = false;
			isException = true;
			isError = false;
		} else if (throwable instanceof Exception) {
			checked = true;
			isException = true;
			isError = false;
		} else {
			checked = true;
			isException = false;
			isError = false;
		}
	}
	
	public RuntimeException asRuntimeException() {
		if (isException && !checked) {
			return (RuntimeException) throwable;
		} else {
			return new RuntimeException(throwable);
		}
	}
	
	public Exception asException() {
		if (isException) {
			return (Exception) throwable;
		} else {
			return new Exception(throwable);
		}
	}
	
	public Exception asExceptionOrThrowUnchecked() throws RuntimeException, Error {
		if (isException && !checked) {
			throw (RuntimeException) throwable;
		} else if (isError) {
			throw (Error) throwable;
		} else {
			return asException();
		}
	}
	
	public Throwable asThrowableOrThrowUnchecked() throws RuntimeException, Error {
		if (isException && !checked) {
			throw (RuntimeException) throwable;
		} else if (isError) {
			throw (Error) throwable;
		} else {
			return throwable;
		}
	}
	
	public boolean isError() {
		return isError;
	}
	public boolean isChecked() {
		return checked;
	}
	public boolean isException() {
		return isException;
	}

}
