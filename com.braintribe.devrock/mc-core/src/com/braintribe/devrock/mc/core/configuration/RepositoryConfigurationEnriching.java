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
package com.braintribe.devrock.mc.core.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.devrock.model.repository.RepositoryConfiguration;

public class RepositoryConfigurationEnriching {
	private List<Supplier<Consumer<RepositoryConfiguration>>> enrichers = new ArrayList<>();
	
	public void addEnricher(Supplier<Consumer<RepositoryConfiguration>> enricher) {
		enrichers.add(enricher);
	}
	
	public RepositoryConfiguration enrich(RepositoryConfiguration repositoryConfiguration) {
		
		for (Supplier<Consumer<RepositoryConfiguration>> enricher: enrichers) {
			enricher.get().accept(repositoryConfiguration);
		}
		return repositoryConfiguration;
	}
}
