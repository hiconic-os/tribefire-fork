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
package com.braintribe.spring.support.factory;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;

public class StringBasedFileFactory implements FactoryBean<File> {

	private String path;
	private boolean errorIsFatal = true;
	private boolean ignoreEmptyPath = true;
	private File parent;
	
	@Configurable @Required
	public void setPath(String path) {
		this.path = path;
	}
	@Configurable
	public void setErrorIsFatal(boolean errorIsFatal) {
		this.errorIsFatal = errorIsFatal;
	}
	@Configurable
	public void setIgnoreEmptyPath(boolean ignoreEmptyPath) {
		this.ignoreEmptyPath = ignoreEmptyPath;
	}
	@Configurable
	public void setParent(File parent) {
		this.parent = parent;
	}
	
	@Override
	public File getObject() throws Exception {
		
		if (ignoreEmptyPath && StringUtils.isEmpty(path)) {
			return null;
		}
		
		try {
			File file = new File(path);
			if (file.isAbsolute()) {
				return file;
			} else {
				return new File(parent,path);
			}
			
				
		} catch (Exception e) {
			if (errorIsFatal)
				throw e;
		}
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return File.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
