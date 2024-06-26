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
import java.util.function.Supplier;

import org.springframework.beans.factory.FactoryBean;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;

public class ProviderBasedFileFactory implements FactoryBean<File> {

	private Supplier<File> provider;
	
	
	@Configurable @Required
	public void setProvider(Supplier<File> provider) {
		this.provider = provider;
	}
	
	@Override
	public File getObject() throws Exception {
		return provider.get();
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
