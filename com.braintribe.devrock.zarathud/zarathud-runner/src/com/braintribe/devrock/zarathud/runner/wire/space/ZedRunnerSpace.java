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
package com.braintribe.devrock.zarathud.runner.wire.space;

import com.braintribe.devrock.mc.api.classpath.ClasspathDependencyResolver;
import com.braintribe.devrock.zarathud.model.ClassesProcessingRunnerContext;
import com.braintribe.devrock.zarathud.model.JarProcessingRunnerContext;
import com.braintribe.devrock.zarathud.model.ResolvingRunnerContext;
import com.braintribe.devrock.zarathud.runner.api.ZedWireRunner;
import com.braintribe.devrock.zarathud.runner.impl.BasicZedWireRunner;
import com.braintribe.devrock.zarathud.runner.impl.builders.ClassesCoreContextBuilder;
import com.braintribe.devrock.zarathud.runner.impl.builders.JarCoreContextBuilder;
import com.braintribe.devrock.zarathud.runner.impl.builders.PreconfiguredResolvingCoreContextBuilder;
import com.braintribe.devrock.zarathud.runner.impl.builders.ResolvingCoreContextBuilder;
import com.braintribe.devrock.zarathud.runner.wire.contract.ZedRunnerContract;
import com.braintribe.devrock.zarathud.wirings.core.context.CoreContext;
import com.braintribe.provider.Holder;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.annotation.Scope;

@Managed
public class ZedRunnerSpace implements ZedRunnerContract {
			
	
	
	@Override
	@Managed(Scope.prototype)
	public ZedWireRunner resolvingRunner(ResolvingRunnerContext context) {
		ZedWireRunner bean = new BasicZedWireRunner( new ResolvingCoreContextBuilder(context));
		return bean;
	}
	
		
	@Override
	@Managed(Scope.prototype)
	public ZedWireRunner preconfiguredResolvingRunner(ResolvingRunnerContext context, ClasspathDependencyResolver resolver) {
		ZedWireRunner bean = new BasicZedWireRunner( new PreconfiguredResolvingCoreContextBuilder(context, resolver));
		return bean;
	}


	@Override
	@Managed(Scope.prototype)
	public ZedWireRunner jarRunner(JarProcessingRunnerContext context) {
		ZedWireRunner bean = new BasicZedWireRunner( new JarCoreContextBuilder(context));		
		return bean;
	}
	
	@Override
	@Managed(Scope.prototype)
	public ZedWireRunner classesRunner(ClassesProcessingRunnerContext context) {
		ZedWireRunner bean = new BasicZedWireRunner( new ClassesCoreContextBuilder(context));		
		return bean;
	}

	@Override
	@Managed(Scope.prototype)
	public ZedWireRunner coreRunner(CoreContext coreContext) {		
		Holder<CoreContext> holder = new Holder<CoreContext>( coreContext);
		ZedWireRunner wireRunner = new BasicZedWireRunner( holder);			
		return wireRunner;
	}
	

	
}
