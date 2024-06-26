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
package com.braintribe.devrock.mc.core.selectors;

import java.util.List;
import java.util.stream.Collectors;

import com.braintribe.devrock.model.repository.Repository;
import com.braintribe.devrock.model.repositoryview.RepositorySelector;
import com.braintribe.devrock.model.repositoryview.selectors.ByNameRegexRepositorySelector;
import com.braintribe.devrock.model.repositoryview.selectors.ByNameRepositorySelector;
import com.braintribe.devrock.model.repositoryview.selectors.ByTypeRepositorySelector;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.utils.lcd.CommonTools;

/**
 * Abstract super class for {@link RepositorySelectorExpert} tests.
 *
 * @author ioannis.paraskevopoulos
 * @author michael.lafite
 */
public abstract class AbstractRepositorySelectorExpertTest {
	
	protected RepositorySelector nameSelector(String name) {
		ByNameRepositorySelector repositorySelector = ByNameRepositorySelector.T.create();
		repositorySelector.setName(name);
		return repositorySelector;
	}

	protected RepositorySelector regexNameSelector(String regex) {
		ByNameRegexRepositorySelector repositorySelector = ByNameRegexRepositorySelector.T.create();
		repositorySelector.setRegex(regex);
		return repositorySelector;
	}
	
	protected <R extends Repository> RepositorySelector typeSelector(EntityType<R> type, boolean includeSubTypes) {
		ByTypeRepositorySelector byTypeRepositorySelector = ByTypeRepositorySelector.T.create();
		byTypeRepositorySelector.setType(type.getShortName());
		byTypeRepositorySelector.setIncludeSubtypes(includeSubTypes);
		return byTypeRepositorySelector;
	}

	protected Repository createRepository(EntityType<? extends Repository> type, String name) {
		Repository repo = type.create();
		repo.setName(name);
		return repo;
	}

	protected List<Repository> createRepositories(EntityType<? extends Repository> type, String... names) {
		return CommonTools.getList(names).stream().map(name -> this.createRepository(type, name)).collect(Collectors.toList());
	}
	
	protected List<Repository> createRepositories(String... names) {
		return CommonTools.getList(names).stream().map(name -> {
			Repository repository = Repository.T.create();
			repository.setName(name);
			return repository;
		}).collect(Collectors.toList());
	}
}
