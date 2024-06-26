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
package com.braintribe.devrock.zarathud.runner.wire.contract;

import com.braintribe.devrock.mc.api.classpath.ClasspathDependencyResolver;
import com.braintribe.devrock.zarathud.model.ClassesProcessingRunnerContext;
import com.braintribe.devrock.zarathud.model.JarProcessingRunnerContext;
import com.braintribe.devrock.zarathud.model.ResolvingRunnerContext;
import com.braintribe.devrock.zarathud.runner.api.ZedWireRunner;
import com.braintribe.devrock.zarathud.wirings.core.context.CoreContext;
import com.braintribe.wire.api.space.WireSpace;

/**
 * the different setups to run zed with 
 * @author pit
 *
 */
public interface ZedRunnerContract extends WireSpace {

	/**
	 * uses mc-core-wirings to get a {@link ClasspathDependencyResolver} via environment sensitive configuration 
	 * @param context - {@link ResolvingRunnerContext}
	 * @return - a fully configured {@link ZedWireRunner}
	 */	
	ZedWireRunner resolvingRunner(ResolvingRunnerContext context);
	
	/**
	 * uses the pre-configured resolver to build the classpath 
	 * @param context - the {@link ResolvingRunnerContext}
	 * @param resolver - a {@link ClasspathDependencyResolver} preconfigured
	 * @return - a fully configured {@link ZedWireRunner}
	 */
	ZedWireRunner preconfiguredResolvingRunner(ResolvingRunnerContext context, ClasspathDependencyResolver resolver);
	
	/**
	 * based on the ant-integration's way to do things 
	 * @param context - the {@link JarProcessingRunnerContext}
	 * @return - a fully configured {@link ZedWireRunner}
	 */
	ZedWireRunner jarRunner(JarProcessingRunnerContext context);

	/**
	 * based on the Eclipse integration's way to do things:
	 * may have additional folders for classes of projects rather than jars 
	 * @param context - the {@link ClassesProcessingRunnerContext}
	 * @return - a fully configured {@link ZedWireRunner}
	 */
	ZedWireRunner classesRunner(ClassesProcessingRunnerContext context);
	
	/**
	 * a generic {@link ZedWireRunner} with the base {@link CoreContext}
	 * @param coreContext - the basic {@link CoreContext}
	 * @return - a fully configured {@link ZedWireRunner}
	 */
	ZedWireRunner coreRunner( CoreContext coreContext);
		
}
