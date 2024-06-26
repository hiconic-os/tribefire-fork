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
package com.braintribe.devrock.mc.core.wirings.codebase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.mc.core.wirings.backend.ArtifactDataBackendModule;
import com.braintribe.devrock.mc.core.wirings.backend.contract.ArtifactDataBackendContract;
import com.braintribe.devrock.mc.core.wirings.codebase.contract.CodebaseEnrichingConfigurationContract;
import com.braintribe.devrock.mc.core.wirings.codebase.space.CodebaseEnrichingSpace;
import com.braintribe.devrock.model.repository.CodebaseRepository;
import com.braintribe.devrock.model.repository.filters.AllMatchingArtifactFilter;
import com.braintribe.wire.api.context.WireContextBuilder;
import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;

/**
 * the {@link WireTerminalModule} for the {@link ArtifactDataBackendContract}
 * @author pit/dirk
 *
 */
public class CodebaseRepositoryModule implements WireModule {

	
	private static class CodebaseEnrichingConfiguration implements CodebaseEnrichingConfigurationContract {
		List<CodebaseRepository> codebaseRepositories;
		
		@Override
		public List<CodebaseRepository> codebaseRepositories() {
			return codebaseRepositories;
		}
	}
	
	private CodebaseEnrichingConfiguration configuration = new CodebaseEnrichingConfiguration();

	/**
	 * creates a module with one single codebase repository 
	 * @param rootPath - the {@link File} pointing to the root of the codebase repository
	 * @param template - the template as a {@link String} that declares the structure of the codebase repository
	 */
	public CodebaseRepositoryModule(File rootPath, String template) {
		this(rootPath, template, Collections.emptySet(), Collections.emptySet());
	}
	
	/**
	 * creates a module with one single codebase repository 
	 * @param rootPath - the {@link File} pointing to the root of the codebase repository
	 * @param template - the template as a {@link String} that declares the structure of the codebase repository
	 * @param archetypesIncludes configures archetypes that are explicitly included (other archetypes will be excluded)
	 * @param archetypesExcludes configures archetypes that are explicitly excluded (other archetypes will be included)
	 */
	public CodebaseRepositoryModule(File rootPath, String template, Set<String> archetypesIncludes, Set<String> archetypesExcludes) {
		super();
		CodebaseRepository codebaseRepository = CodebaseRepository.T.create();
		codebaseRepository.setName("codebase");
		codebaseRepository.setCachable(false);
		codebaseRepository.setRootPath(rootPath.getAbsolutePath());
		codebaseRepository.setTemplate(template);
		codebaseRepository.setDominanceFilter(AllMatchingArtifactFilter.T.create());
		codebaseRepository.setArchetypesIncludes(archetypesIncludes);
		codebaseRepository.setArchetypesExcludes(archetypesExcludes);
		configuration.codebaseRepositories = Collections.singletonList(codebaseRepository);
	}
			
	
	/**
	 * creates a module with the passed {@link List} of {@link CodebaseRepository}
	 * @param codebaseRepositories - a {@link List} of preconfigured {@link CodebaseRepository}
	 */
	public CodebaseRepositoryModule(List<CodebaseRepository> codebaseRepositories) {
		super();
		configuration.codebaseRepositories = codebaseRepositories;
	}
	
	/**
	 * creates a module while internally creating {@link CodebaseRepository} from the passed {@link Pair}
	 * @param pairs - an Array of {@link Pair} of the codebase root as {@link File} and its declaring template as {@link String}
	 */
	@SuppressWarnings("unchecked")
	public CodebaseRepositoryModule(Pair<File,String> ... pairs) {
		super();
		if (pairs == null) {
			throw new IllegalStateException("at least one codebase pair must be given");
		}
		configuration.codebaseRepositories = new ArrayList<>( pairs.length);
		int i = 0;
		for (Pair<File,String> pair : pairs) {
			CodebaseRepository codebaseRepository = CodebaseRepository.T.create();
			codebaseRepository.setName("codebase-" + i++);
			codebaseRepository.setCachable(false);
			codebaseRepository.setRootPath( pair.first.getAbsolutePath());
			codebaseRepository.setTemplate( pair.second);
			codebaseRepository.setDominanceFilter(AllMatchingArtifactFilter.T.create());
			configuration.codebaseRepositories.add( codebaseRepository);
		}
	}

	@Override
	public void configureContext(WireContextBuilder<?> contextBuilder) {
		WireModule.super.configureContext(contextBuilder);
		contextBuilder.bindContract(CodebaseEnrichingConfigurationContract.class, configuration);
		contextBuilder.autoLoad(CodebaseEnrichingSpace.class);
	}
	
	@Override
	public List<WireModule> dependencies() {
		return Collections.singletonList(ArtifactDataBackendModule.INSTANCE);
	}
	
}
