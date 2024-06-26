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
package com.braintribe.devrock.mc.core.repository.local;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.braintribe.devrock.mc.api.commons.ArtifactAddressBuilder;
import com.braintribe.devrock.mc.api.repository.local.ArtifactPartResolverPersistenceDelegate;
import com.braintribe.devrock.mc.api.repository.local.PartAvailability;
import com.braintribe.devrock.mc.api.repository.local.PartAvailabilityAccess;
import com.braintribe.devrock.mc.core.filters.ArtifactFilterExpert;
import com.braintribe.devrock.model.repository.Repository;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.compiled.CompiledPartIdentification;
import com.braintribe.model.artifact.essential.ArtifactIdentification;
import com.braintribe.model.artifact.essential.PartIdentification;
import com.braintribe.model.version.Version;

/**
 * an implementation for the {@link PartAvailabilityAccess} for the *local* repository (i.e. for 'installed' artifact)
 * @author pit/dirk
 *
 */
public class LocalReleasePartAvailabilityAccess extends AbstractPartAvailabilityAccess {
	

	private BiFunction<ArtifactIdentification, Version, Boolean> versionPredicate;

	public LocalReleasePartAvailabilityAccess(CompiledArtifactIdentification compiledArtifactIdentification,
			Function<File, ReadWriteLock> lockSupplier, ArtifactFilterExpert artifactFilter,
			File localRepository, Repository repository, ArtifactPartResolverPersistenceDelegate repoDelegate, BiFunction<ArtifactIdentification, Version, Boolean> versionPredicate) {
		super(compiledArtifactIdentification, lockSupplier, artifactFilter, localRepository, repository, repoDelegate);
		this.versionPredicate = versionPredicate;
	
	}

	@Override
	public void setAvailablity(PartIdentification partIdentification, PartAvailability availablity) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Version getActualVersion() {		
		return compiledArtifactIdentification.getVersion();
	}

	@Override
	protected PartAvailability getAvailability(CompiledPartIdentification cpi) {
		File part = ArtifactAddressBuilder.build().root(localRepository.getAbsolutePath()).compiledArtifact(cpi).part(cpi).toPath().toFile();
		if (!part.exists()) {
			return PartAvailability.unavailable;
		}
				
		if (versionPredicate.apply( cpi, cpi.getVersion())) {
			return PartAvailability.available;
		}
		
		return PartAvailability.unavailable;
	}

	@Override
	public Set<CompiledPartIdentification> getAvailableParts() {	
		File artifactDirectory = ArtifactAddressBuilder.build().root(localRepository.getAbsolutePath()).compiledArtifact(compiledArtifactIdentification).toPath().toFile();
		if (!artifactDirectory.exists()) {
			return Collections.emptySet();
		}
		Set<CompiledPartIdentification> result = new HashSet<>();
		File [] files = artifactDirectory.listFiles();
		for (File file : files) {
			String name = file.getName();
			CompiledPartIdentification cpi = CompiledPartIdentification.fromFile(compiledArtifactIdentification, name);
			if (cpi != null) {
				result.add(cpi);
			}
		}
		return result;
	}
	
	

}
