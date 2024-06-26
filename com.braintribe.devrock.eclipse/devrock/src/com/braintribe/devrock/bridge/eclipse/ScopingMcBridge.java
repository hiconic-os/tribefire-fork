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
package com.braintribe.devrock.bridge.eclipse;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.devrock.bridge.eclipse.api.McBridge;
import com.braintribe.devrock.eclipse.model.identification.RemoteCompiledDependencyIdentification;
import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionScope;
import com.braintribe.devrock.mc.api.repository.configuration.RepositoryReflection;
import com.braintribe.devrock.model.repository.RepositoryConfiguration;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.model.artifact.compiled.CompiledArtifact;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.compiled.CompiledDependencyIdentification;
import com.braintribe.model.artifact.compiled.CompiledPartIdentification;
import com.braintribe.model.artifact.compiled.CompiledTerminal;

/**
 * a {@link McBridge} implementation that checks whether the currently used scope (i.e. configuration)
 * is stale (left idling for more than a certain time) and pulls a new one. Otherwise, the existing 
 * scope (which includes the caches of mc-core) is reused.
 * 
 * @author pit / dirk
 *
 */
public class ScopingMcBridge implements McBridge {

	private Supplier<McBridge> bridgeFactory;

	private long lastTransitIntoIdling;
	private AtomicInteger workerCount = new AtomicInteger();
	private McBridge currentBridge;
	private long maxIdlingInMillis = 1000;
	
	private Object bridgeMonitor = new Object();
	
	/**
	 * @param factory - the supplier for the {@link McBridge} instance
	 */
	public ScopingMcBridge( Supplier<McBridge> factory) {
		bridgeFactory = factory;
	}
	
	/**
	 * smart worker method : will pull a new bridge if gotten state
	 * @param <T> - the {@link McBridge}'s function's return type 
	 * @param worker - the function of {@link McBridge} to be called 
	 * @return - T 
	 */
	protected <T> T doWork(Function<McBridge,T> worker) {
		// 
		synchronized (bridgeMonitor) {
			if (currentBridge == null || 
				(
						System.currentTimeMillis() - lastTransitIntoIdling > maxIdlingInMillis && 
						workerCount.get() == 0)
				) {
				// close old existing bridge
				if (currentBridge != null)
					currentBridge.close();
				// get a new bridge
				currentBridge = bridgeFactory.get();
			}
		}
		
						
		workerCount.incrementAndGet();
		
		try {
			return worker.apply(currentBridge);
		} finally {
			int workers = workerCount.decrementAndGet();
			if (workers == 0) {
				synchronized (bridgeMonitor) {
					lastTransitIntoIdling = System.currentTimeMillis();
				}
			}
		}
	}
	
	@Override
	public void close() {
		synchronized ( bridgeMonitor) {		
			if (currentBridge != null) {
				currentBridge.close();
				currentBridge = null;
			}		
		}
	}	
	
	

	@Override
	public McBridge customBridge(RepositoryConfiguration repositoryConfiguration) {	
		return doWork( b -> b.customBridge(repositoryConfiguration));
	}

	@Override
	public Maybe<AnalysisArtifactResolution> resolveClasspath(CompiledTerminal ct, ClasspathResolutionScope resolutionScope) {
		return doWork( b -> b.resolveClasspath(ct, resolutionScope));
	}
	
	@Override
	public Maybe<AnalysisArtifactResolution> resolveClasspath(Collection<CompiledTerminal> cts, ClasspathResolutionScope resolutionScope) {
		return doWork( b -> b.resolveClasspath(cts, resolutionScope));
	}

	@Override
	public Maybe<CompiledArtifact> readPomFile(File pomFile) {	
		return doWork( b -> b.readPomFile(pomFile));
	}

	@Override
	public Maybe<CompiledArtifact> resolve(CompiledArtifactIdentification cai) {
		return doWork( b -> b.resolve( cai));
	}

	@Override
	public Maybe<CompiledArtifactIdentification> resolve(CompiledDependencyIdentification cdi) {
		return doWork( b -> b.resolve( cdi));
	}

	@Override
	public Maybe<File> resolve(CompiledPartIdentification cpi) {
		return doWork( b -> b.resolve( cpi));
	}

	@Override
	public Maybe<RepositoryReflection> reflectRepositoryConfiguration() {	
		return doWork( b -> b.reflectRepositoryConfiguration());
	}

	@Override
	public List<CompiledArtifactIdentification> matchesFor(CompiledDependencyIdentification cdi) {	
		return doWork( b -> b.matchesFor(cdi));
	}

	@Override
	public List<RemoteCompiledDependencyIdentification> retrieveCurrentRemoteArtifactPopulation() {	
		return doWork( b -> b.retrieveCurrentRemoteArtifactPopulation());
	}
	
	@Override
	public List<RemoteCompiledDependencyIdentification> retrieveCurrentLocalArtifactPopulation() {	
		return doWork( b -> b.retrieveCurrentLocalArtifactPopulation());
	}
	
}
