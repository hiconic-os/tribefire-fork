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
package com.braintribe.model.processing.bootstrapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.braintribe.config.configurator.ClasspathConfigurator;
import com.braintribe.config.configurator.ConfiguratorContext;
import com.braintribe.logging.Logger;

public class ConfiguratorsInitializer implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ConfiguratorsInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		runConfigurators(contextEvent.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// intentionally left empty
	}

	protected void runConfigurators(ServletContext context) {
		try {
			(new ClasspathConfigurator(new ConfiguratorContext(context.getContextPath()))).configure();
		} catch (Throwable e) {
			logger.error("Failed to execute configurators: " + e.getMessage(), e);
		}
	}

}
