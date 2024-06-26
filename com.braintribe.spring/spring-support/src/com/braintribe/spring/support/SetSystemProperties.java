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
package com.braintribe.spring.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;

import com.braintribe.cfg.Configurable;
import com.braintribe.logging.Logger;

public class SetSystemProperties implements InitializingBean {

	private static final Logger logger = Logger.getLogger(SetSystemProperties.class);

	protected Map<String,String> systemProperties = new HashMap<String,String>();

	@Override
	public void afterPropertiesSet() throws Exception {

		for (Map.Entry<String,String> entry : this.systemProperties.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			if (logger.isDebugEnabled()) {
				logger.debug("Setting system property: '"+key+"'='"+value+"'");
			}
			try {
				System.setProperty(key, value);
			} catch(Exception e) {
				throw new BeanInitializationException("Error while trying to set system property: "+key+"="+value, e);
			}
		}

	}

	@Configurable
	public void setSystemProperties(Map<String, String> systemProperties) {
		this.systemProperties = systemProperties;
	}

}
