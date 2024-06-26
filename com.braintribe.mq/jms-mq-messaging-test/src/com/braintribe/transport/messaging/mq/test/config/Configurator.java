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
package com.braintribe.transport.messaging.mq.test.config;

import java.io.File;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.braintribe.logging.Logger;

public class Configurator {

	private static final Logger logger = Logger.getLogger(Configurator.class);

	protected GenericApplicationContext applicationContext;

	public Configurator() throws Exception {
		this.initialize();
	}
	
	public void initialize() throws Exception {
		try {
			
			logger.info("Initializing context");

			applicationContext = new GenericApplicationContext();
			applicationContext.setClassLoader(this.getClass().getClassLoader());


			XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(applicationContext);

			File cfgFile = new File("res/main.xml");
			Resource resource = new FileSystemResource(cfgFile.getAbsolutePath());
			xmlReader.loadBeanDefinitions(resource);
			applicationContext.refresh();

			logger.info("Done initializing context");
			
			

		} catch(Throwable t) {
			logger.info("Error while initializing context", t);
			throw new RuntimeException("Error while initializing context", t);
		}
	}
	
	public void close() {
		this.applicationContext.close();
	}
	
	public GenericApplicationContext getContext() {
		return this.applicationContext;
	}
}
