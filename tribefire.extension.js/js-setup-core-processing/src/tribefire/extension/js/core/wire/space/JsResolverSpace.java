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

import java.io.File;

import com.braintribe.build.artifacts.mc.wire.buildwalk.contract.BuildDependencyResolutionContract;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.js.core.api.JsResolver;
import tribefire.extension.js.core.impl.BasicJsResolver;
import tribefire.extension.js.core.wire.contract.JsResolverConfigurationContract;
import tribefire.extension.js.core.wire.contract.JsResolverContract;

/**
 * the space for the {@link JsResolverContract}
 * @author pit
 *
 */
@Managed
public class JsResolverSpace implements JsResolverContract {
	@Import
	BuildDependencyResolutionContract buildResolution;
	
	@Import 
	JsResolverConfigurationContract jsResolverConfiguration;
	
	@Override
	@Managed
	public BasicJsResolver jsResolver() {	
		BasicJsResolver bean = new BasicJsResolver();
		bean.setBuildDependencyResolver( buildResolution.buildDependencyResolver());
		bean.setPomReader( buildResolution.pomReader());
		bean.setLocalRepositoryPath( localRepository());
		bean.setPreferMinOverPretty( jsResolverConfiguration.preferMinOverPretty());
		bean.setUseSymbolicLink(jsResolverConfiguration.useSymbolicLink());
		bean.setEnricher( buildResolution.solutionEnricher());
		return bean;
	}


	@Override
	public File localRepository() {
		return buildResolution.localRepository();		
	}
}
