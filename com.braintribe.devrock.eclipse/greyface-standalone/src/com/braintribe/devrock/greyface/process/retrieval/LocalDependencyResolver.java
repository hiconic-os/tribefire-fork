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
package com.braintribe.devrock.greyface.process.retrieval;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.braintribe.build.artifact.name.NameParser;
import com.braintribe.build.artifact.retrieval.multi.resolving.ResolvingException;
import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.devrock.greyface.GreyfacePlugin;
import com.braintribe.devrock.greyface.GreyfaceStatus;
import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Part;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.processing.artifact.ArtifactProcessor;
import com.braintribe.model.artifact.processing.part.PartTupleProcessor;
import com.braintribe.model.artifact.processing.version.VersionProcessingException;
import com.braintribe.model.artifact.processing.version.VersionProcessor;
import com.braintribe.model.artifact.processing.version.VersionRangeProcessor;
import com.braintribe.model.artifact.version.Version;

public class LocalDependencyResolver extends AbstractDependencyResolver {
	private File localDirectoryRoot;
	
	@Required @Configurable
	public void setLocalDirectoryRoot(String localDirectoryRoot) {
		this.localDirectoryRoot = new File(localDirectoryRoot);
	}

	@Override
	public Part resolvePomPart(String conextId, Part part) throws ResolvingException {
		try {
			Part pomPart = ArtifactProcessor.createPartFromIdentification(part, part.getVersion(), PartTupleProcessor.createPomPartTuple());
		
			String path = part.getGroupId().replace(".", "/") + "/" + part.getArtifactId() + "/" + VersionProcessor.toString( part.getVersion());			
			File file = new File( localDirectoryRoot, path + "/" + NameParser.buildFileName(pomPart));
			if (file.exists()) {
				pomPart.setLocation( file.getAbsolutePath());
				return pomPart;
			}
		} catch (Exception e) {
			String msg="cannot resolve local pom file";
			throw new ResolvingException( msg, e);
		}
		return null;
	}

	@Override
	public Set<Solution> resolveTopDependency(String contextId, Dependency dependency) throws ResolvingException {
		String path = dependency.getGroupId().replace(".", File.separator) + "/" + dependency.getArtifactId();
		File scanDirectory = new File( localDirectoryRoot, path);
		Set<Solution> result = new HashSet<Solution>();
		File[] listedFiles = scanDirectory.listFiles();
		if (listedFiles == null) {
			return result;
		}
		for (File file : listedFiles) {
			if (file.isDirectory()) {
				String versionAsString = file.getName();
				try {
					Version version = VersionProcessor.createFromString( versionAsString);
					if (VersionRangeProcessor.matches( dependency.getVersionRange(), version)) { 
						Solution solution = Solution.T.create();
						ArtifactProcessor.transferIdentification(solution, dependency);
						solution.setVersion(version);
						Part part = Part.T.create();
						ArtifactProcessor.transferIdentification(part, solution);
						Part resolvedPart = resolvePomPart(contextId, part);
						if (resolvedPart != null) {
							solution.getParts().add( resolvedPart);						
							result.add(solution);
						}
					}
				} catch (VersionProcessingException e) {
					String msg = "cannot process local directory [" + versionAsString + "] for dependency resolving";
					GreyfaceStatus status = new GreyfaceStatus( msg, e);
					GreyfacePlugin.getInstance().getLog().log(status);
					continue;
				}
			}
		}
		if (result.size() == 0)
			return null;
		return result;
	}

	
}
