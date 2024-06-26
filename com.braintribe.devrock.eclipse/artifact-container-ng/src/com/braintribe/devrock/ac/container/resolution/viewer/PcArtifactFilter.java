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
package com.braintribe.devrock.ac.container.resolution.viewer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;

import com.braintribe.devrock.mc.api.repository.configuration.RepositoryReflection;
import com.braintribe.devrock.model.repository.MavenFileSystemRepository;
import com.braintribe.devrock.model.repository.Repository;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.devrock.plugin.DevrockPluginStatus;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.consumable.Part;
import com.braintribe.model.artifact.essential.VersionedArtifactIdentification;

public class PcArtifactFilter {

	private RepositoryReflection repositoryReflection;
	private Map<AnalysisArtifact, File> pcArtifacts = new HashMap<>();
	 

	public PcArtifactFilter() {
		repositoryReflection = DevrockPlugin.mcBridge().reflectRepositoryConfiguration().get();			
	}
		
	
	public boolean filter(AnalysisArtifact artifact) {
		String repositoryOrigin;
		Part pomPart = artifact.getParts().get(":pom");		
		if (pomPart == null) {			
			DevrockPluginStatus status = new DevrockPluginStatus("no pom found, cannot determine origin of :" + artifact.asString(), IStatus.ERROR);
			DevrockPlugin.instance().log(status);
			return false;
		}
		else {
			repositoryOrigin = pomPart.getRepositoryOrigin();
		}
		
		Repository repository = repositoryReflection.getRepository( repositoryOrigin);
		if (repository == null) {
			return false;
		}
		if (repository instanceof MavenFileSystemRepository == false) {			
			return false;
		}
		
		MavenFileSystemRepository fileRepo = (MavenFileSystemRepository) repository;
		File repoRoot = new File( fileRepo.getRootPath());
		pcArtifacts.put(artifact, repoRoot);
		return true;
	}


	public Map<AnalysisArtifact, File> getPcArtifacts() {
		return pcArtifacts;
	}
	
	public Map<VersionedArtifactIdentification, File> getPcIdentifications() {
		Map<VersionedArtifactIdentification, File> result = new HashMap<>( pcArtifacts.size());
		for (Map.Entry<AnalysisArtifact, File> entry : pcArtifacts.entrySet()) {
			result.put( entry.getKey(), entry.getValue());
		}
		return result;
	}
	
}
