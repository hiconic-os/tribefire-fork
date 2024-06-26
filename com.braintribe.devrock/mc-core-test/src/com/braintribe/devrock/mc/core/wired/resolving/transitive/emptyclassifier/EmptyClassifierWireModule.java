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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.emptyclassifier;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.braintribe.devrock.mc.core.commons.utils.TestUtils;
import com.braintribe.devrock.mc.core.wirings.classpath.ClasspathResolverWireModule;
import com.braintribe.devrock.mc.core.wirings.classpath.contract.ClasspathResolverContract;
import com.braintribe.devrock.mc.core.wirings.configuration.contract.RepositoryConfigurationContract;
import com.braintribe.devrock.model.repository.MavenFileSystemRepository;
import com.braintribe.devrock.model.repository.RepositoryConfiguration;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.wire.api.context.WireContextBuilder;
import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;

public enum EmptyClassifierWireModule implements WireTerminalModule<ClasspathResolverContract> {
	
	INSTANCE;
	
	@Override
	public void configureContext(WireContextBuilder<?> contextBuilder) {
		RepositoryConfiguration config = RepositoryConfiguration.T.create();
		
		File localRepo = new File("res/output/wired/transitive/emptyclassifier/repo");
		
		TestUtils.ensure(localRepo);
		
		File rootPath = new File("res/input/wired/transitive/emptyclassifier/repo");
		
		MavenFileSystemRepository repo = MavenFileSystemRepository.T.create();
		repo.setRootPath(rootPath.getAbsolutePath());
		repo.setName("test");
		
		config.setLocalRepositoryPath(localRepo.getAbsolutePath());
		config.getRepositories().add(repo);
		
		contextBuilder.bindContract(RepositoryConfigurationContract.class, () -> Maybe.complete(config));
	}
	
	@Override
	public List<WireModule> dependencies() {
		return Collections.singletonList(ClasspathResolverWireModule.INSTANCE);
	}

}
