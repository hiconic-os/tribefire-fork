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
package com.braintribe.utils.ldap;

import java.util.concurrent.Callable;

import javax.naming.Context;
import javax.naming.ldap.LdapContext;

import org.junit.Ignore;

@Ignore
public class AuthenticationWorker implements Callable<Boolean> {

	protected LdapConnectionStack ldapStack = null;
	protected int iterations = 20;
	protected int workerId = -1;

	public AuthenticationWorker(LdapConnectionStack ldapStack, int workerId, int iterations) {
		this.ldapStack = ldapStack;
		this.workerId = workerId;
		this.iterations = iterations;
	}

	@Override
	public Boolean call() throws Exception {
		try {
			for (int i=0; i<this.iterations; ++i) {
				LdapContext dirContext = this.ldapStack.pop();
				try {
					dirContext.addToEnvironment(Context.SECURITY_AUTHENTICATION, "simple");
					dirContext.addToEnvironment(Context.SECURITY_PRINCIPAL, LdapConnectionStackTest.AUTH_USER);
					dirContext.addToEnvironment(Context.SECURITY_CREDENTIALS, LdapConnectionStackTest.AUTH_PASS);

					dirContext.getAttributes(LdapConnectionStackTest.AUTH_USER, null);
					
					System.out.println("Successfully authenticated in thread "+this.workerId+": "+i);
				} finally {
					dirContext.removeFromEnvironment(Context.SECURITY_AUTHENTICATION);
					dirContext.removeFromEnvironment(Context.SECURITY_PRINCIPAL);
					dirContext.removeFromEnvironment(Context.SECURITY_CREDENTIALS);
				}
				this.ldapStack.push(dirContext);
				dirContext = null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
