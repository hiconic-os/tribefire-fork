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

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

/**
 * extends the {@link DefaultResourceLoader} and can load a {@link Resource}
 * @author pit
 *
 */
public class GenericResourceLoader extends DefaultResourceLoader implements ResourceLoader{

	@Override
	protected Resource getResourceByPath(String name) {
		URL url = this.getClassLoader().getResource( name);
		Resource resource = null;
		if (url != null) {
			resource = new UrlResource( url);									
		} else {
			resource = new FileSystemResource( name);			
		}
		if (resource.exists() == false) {		
			return null;
		}
		return resource;
	}

	
}
