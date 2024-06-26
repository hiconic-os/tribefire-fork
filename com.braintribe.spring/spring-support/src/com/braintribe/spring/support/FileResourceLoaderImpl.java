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

import java.io.File;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class FileResourceLoaderImpl extends DefaultResourceLoader implements ResourceLoader {

	protected File basePath = null;
	
	public FileResourceLoaderImpl(File basePath) {
		this.basePath = basePath;
	}
	
	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(new File(this.basePath, path));
	}
	
	public Resource getResource(String location) {
		return super.getResource(location);
	}

}
