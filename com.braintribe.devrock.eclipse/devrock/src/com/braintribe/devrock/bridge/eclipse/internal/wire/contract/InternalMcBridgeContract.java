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
package com.braintribe.devrock.bridge.eclipse.internal.wire.contract;

import com.braintribe.devrock.bridge.eclipse.api.McBridge;
import com.braintribe.devrock.mc.api.classpath.ClasspathDependencyResolver;
import com.braintribe.devrock.mc.api.repository.configuration.ArtifactChangesSynchronization;
import com.braintribe.devrock.mc.api.repository.configuration.RepositoryReflection;
import com.braintribe.devrock.mc.api.resolver.ArtifactResolver;
import com.braintribe.devrock.mc.api.resolver.CompiledArtifactResolver;
import com.braintribe.devrock.mc.api.resolver.DeclaredArtifactCompiler;
import com.braintribe.devrock.mc.api.resolver.DependencyResolver;
import com.braintribe.wire.api.space.WireSpace;

/**
 * internal wiring for {@link McBridge}
 * 
 * @author pit / dirk
 *
 */
public interface InternalMcBridgeContract extends WireSpace {	
	
	/**
	 * @return - the {@link ClasspathDependencyResolver}
	 */
	ClasspathDependencyResolver classpathResolver();
	
	/**
	 * @return - the basic {@link DependencyResolver} (redirect aware)
	 */
	DependencyResolver dependencyResolver();
	
	/**
	 * @return - the {@link CompiledArtifactResolver}
	 */
	CompiledArtifactResolver compiledArtifactResolver();
	
	/**
	 * @return - the {@link DeclaredArtifactCompiler} 
	 */
	DeclaredArtifactCompiler declaredArtifactCompiler();
	
	/**
	 * @return - the {@link ArtifactResolver}
	 */
	ArtifactResolver artifactResolver();
	
	/**
	 * @return - {@link RepositoryReflection}
	 */
	RepositoryReflection repositoryReflection();
		

	/**
	 * @return - a {@link ArtifactChangesSynchronization}
	 */
	ArtifactChangesSynchronization changesSynchronization();
}
