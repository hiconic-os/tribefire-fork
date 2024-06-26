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
package com.braintribe.spring.loader;

import java.net.URL;

import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * a helper for internal spring configuration <br/>
 * designed to access a jar-internal spring configuration 
 * 
 * 
 * @author pit
 *
 */
public class GenericSpringLoader {

	protected GenericApplicationContext applicationContext = null;
	
	/**
	 * scans for the configuration file in the classpath and loads it 
	 * @param configName - name of the configuration file 
	 */
	public GenericSpringLoader( String configName) throws GenericSpringLoaderException{
		applicationContext = new GenericApplicationContext();
		applicationContext.setClassLoader(this.getClass().getClassLoader());		
		applicationContext.setResourceLoader(new GenericResourceLoader());
		
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(applicationContext);        
	
		Resource resource = getResource( configName);		
				
		xmlReader.loadBeanDefinitions( resource);	
		applicationContext.refresh();
							
	}
	
	
	/**
	 * loads a resource either from the classpath or from the file system (as fallback) 
	 * @param name - name of the resource 
	 * @return - the {@link Resource}
	 * @throws GenericSpringLoaderException - if resource cannot be found 
	 */
	public Resource getResource( String name) throws GenericSpringLoaderException {
		URL url = applicationContext.getClassLoader().getResource( name);
		Resource resource = null;
		if (url != null) {
			resource = new UrlResource( url);									
		} else {
			resource = new FileSystemResource( name);			
		}
		if (resource.exists() == false) {
			throw new GenericSpringLoaderException( "cannot load configuration file [" + name + "] as it doesn't exist");
		}
		return resource;
	}
	
	/**
	 * returns a bean with the passed id 
	 * @param bean - the id of the bean 
	 * @return - the bean of type T 
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean( String bean) {
		return (T) applicationContext.getBean(bean);
	}
	
	/**
	 * returns a bean of the correct type from the configuration, must be the only bean of that type in context 
	 * @param requiredType - the type of the bean to get
	 */
	public <T> T getBean( Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
	
	/**
	 * returns a {@link Scope} from the context.
	 * @param id - the id of the {@link Scope} as {@link String}
	 * @return - the instance of the class extending a {@link Scope}
	 */
	public <T extends Scope> T getScope(String id) {
		T scope = (T)applicationContext.getBeanFactory().getRegisteredScope( id);
		return scope;
	}	
	
}
