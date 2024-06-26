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
package tribefire.extension.js.core.wire.space;

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.braintribe.build.artifacts.mc.wire.buildwalk.contract.FilterConfigurationContract;
import com.braintribe.build.artifacts.mc.wire.buildwalk.space.FilterConfigurationSpace;
import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.PartTuple;
import com.braintribe.model.artifact.Solution;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.js.core.impl.JsDependencyFiltering;
import tribefire.extension.js.core.wire.contract.JsResolverConfigurationContract;

/**
 * specific implementation of the {@link FilterConfigurationContract} that takes the relevant settings (partExpectation) for the {@link JsResolverConfigurationContract}
 * @author pit
 *
 */
@Managed
public class JsFilterConfigurationSpace extends FilterConfigurationSpace{
	@Import
	JsResolverConfigurationContract jsResolverConfiguration;
	
	@Import
	JsResolverSpace jsResolver;
	
	
	@Override
	public BiPredicate<? super Solution, ? super Dependency> solutionDependencyFilter() {
		return JsDependencyFiltering::filter;
	}
	
	@Override
	public Predicate<? super Solution> solutionFilter() {
		return jsResolver.jsResolver()::filterSolution;
	}
	
	@Override
	public Collection<PartTuple> partExpectation() {		
		if (jsResolverConfiguration.relevantPartTuples() != null) {
			return jsResolverConfiguration.relevantPartTuples();
		}
		else {
			return Collections.emptyList();
		}
		
	}
	
}
