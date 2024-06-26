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
package com.braintribe.utils.ldap.invocation;

import java.lang.reflect.Method;

/**
 * This class is used to invoke LDAP-related method calls
 * in a separate thread.
 * 
 * @author roman.kurmanowytsch
 */
public class InvocationThread extends Thread {
	protected Object subject = null;
	protected Method m = null;
	protected Object[] args = null;
	protected Object result = null;
	protected boolean resultReady = false;
	protected boolean exceptionThrown = false;
	protected Exception exception = null;

	public InvocationThread(Object subject, Method m, Object[] args) {
		this.subject = subject;
		this.m = m;
		this.args = args;
	}

	@Override
	public void run() {
		try {
			// run the command and set the result...
			this.result = this.m.invoke(this.subject, this.args);
			this.resultReady = true;
		} catch (Exception e) {
			this.exceptionThrown = true;
			this.exception = e;
		}
	}

	public Exception getException() {
		return exception;
	}

	public Object getResult() {
		return result;
	}

	public boolean isResultReady() {
		return resultReady;
	}

	public boolean isExceptionThrown() {
		return exceptionThrown;
	}
}
