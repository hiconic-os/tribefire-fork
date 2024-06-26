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
package com.braintribe.model.processing.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.braintribe.utils.DateTools;
import com.braintribe.utils.StringTools;

public class ShiroTools {
	public static void printUser(String source) {
		StringBuilder sb = new StringBuilder(source+" ("+DateTools.getCurrentDateString()+")\n\n");
		
		Subject user = SecurityUtils.getSubject();
		if (user != null) {
			
			Session session = user.getSession();
			sb.append("Session: "+session+"\n");
			sb.append("User authenticated: "+user.isAuthenticated()+"\n");
			
			//if (user.isAuthenticated()) {
				PrincipalCollection principals = user.getPrincipals();
				if (principals != null && !principals.isEmpty()) {
					principals.forEach(p -> {
						sb.append("Principal: "+p+" ("+p.getClass()+")\n");
					});
				}
//			} else {
//				sb.append("not authenticated\n");
//			}
			sb.append(user.toString()+"\n");
		} else {
			sb.append("no user\n");
		}
		System.out.println(StringTools.asciiBoxMessage(sb.toString(), -1));
	}
}
