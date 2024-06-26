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

import com.braintribe.build.artifact.retrieval.multi.resolving.DependencyResolver;
import com.braintribe.build.artifacts.mc.wire.buildwalk.contract.IntransitiveResolutionContract;
import com.braintribe.build.artifacts.mc.wire.buildwalk.space.BuildDependencyResolutionSpace;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.js.core.impl.BasicDependencyResolver;
import tribefire.extension.js.core.wire.contract.JsResolverConfigurationContract;

/**
 * specific implementation of the {@link IntransitiveResolutionContract} that injects the {@link BasicDependencyResolver}
 * @author pit
 *
 */
@Managed
public class JsIntransitiveResolutionSpace implements IntransitiveResolutionContract {
	@Import
	private BuildDependencyResolutionSpace buildDependencyResolutionSpace;
	
	@Import
	private JsResolverConfigurationContract jsResolverConfigurationSpace;

	
	@Override
	@Managed
	public DependencyResolver intransitiveDependencyResolver() {		
		boolean supportLocalProjects = jsResolverConfigurationSpace.supportLocalProjects();
		if (supportLocalProjects) {
			BasicDependencyResolver bean = new BasicDependencyResolver();
			bean.setWorkingDirectory( jsResolverConfigurationSpace.resolutionDirectory());
			bean.setPomReader( buildDependencyResolutionSpace.pomReader());
			bean.setDelegate( buildDependencyResolutionSpace.standardDependencyResolver());
			return bean;
		}
		else {
			return buildDependencyResolutionSpace.standardDependencyResolver();
		}
	}
	
}
