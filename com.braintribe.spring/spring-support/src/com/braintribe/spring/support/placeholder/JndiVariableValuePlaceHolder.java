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
package com.braintribe.spring.support.placeholder;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.braintribe.cfg.Configurable;

public class JndiVariableValuePlaceHolder extends PropertyPlaceholderConfigurer {

	protected String propertyPrefix = "myjndi:";
	protected Context envContext = null; 

	public JndiVariableValuePlaceHolder() throws NamingException {
		Context ctx = new InitialContext();
		this.envContext = (Context) ctx.lookup("java:comp/env");
	}

	@Override
	protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {

		if (placeholder.startsWith(this.propertyPrefix)) {
			placeholder = placeholder.substring(this.propertyPrefix.length());
			String value = null;
			try {
				value = (String) this.envContext.lookup(placeholder);
			} catch (Exception e) {
				throw new RuntimeException("Error while trying to look for "+placeholder+" in JNDI directory.", e);
			}
			return value;
		} 

		return super.resolvePlaceholder(placeholder, props, systemPropertiesMode);    

	}

	@Configurable
	public void setPropertyPrefix(String propertyPrefix) {
		this.propertyPrefix = propertyPrefix;
	}


}
