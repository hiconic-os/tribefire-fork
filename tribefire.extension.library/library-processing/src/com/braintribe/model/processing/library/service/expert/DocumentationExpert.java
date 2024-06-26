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
package com.braintribe.model.processing.library.service.expert;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.artifact.consumable.Artifact;
import com.braintribe.model.artifact.essential.ArtifactIdentification;
import com.braintribe.model.library.Library;
import com.braintribe.model.library.service.ArtifactReferences;
import com.braintribe.model.library.service.documentation.CreateSpdxSbom;
import com.braintribe.model.library.service.documentation.DocumentLibraries;
import com.braintribe.model.library.service.documentation.LibraryDocumentation;
import com.braintribe.model.library.service.documentation.SpdxSbom;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.library.LibraryConstants;
import com.braintribe.model.processing.library.service.libdoc.LibraryDocumentationGenerator;
import com.braintribe.model.processing.library.service.spdx.SpdxSupport;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.model.resource.Resource;
import com.braintribe.utils.stream.DeleteOnCloseFileInputStream;

public class DocumentationExpert {

	private final static Logger logger = Logger.getLogger(DocumentationExpert.class);

	private PersistenceGmSessionFactory sessionFactory;
	private DependencyExpert dependencyExpert;
	private LibraryDocumentationGenerator documentationGenerator;
	private SpdxSupport spdxSupport;

	public SpdxSbom createSpdxSbom(AccessRequestContext<CreateSpdxSbom> context) {

		CreateSpdxSbom request = context.getRequest();

		SpdxSbom result = SpdxSbom.T.create();

		PersistenceGmSession librarySession = sessionFactory.newSession(LibraryConstants.LIBRARY_ACCESS_ID);
		try {
			List<String> dependencyList = resolveDependencies(request);

			// Collect all libraries
			TreeSet<String> librariesFound = new TreeSet<>();
			List<Library> libraries = dependencyExpert.getLibraries(request, librarySession, dependencyList, librariesFound, request);

			Resource resource = spdxSupport.createSpdxSbom(request, result.getWarnings()::add, libraries);
			result.setSpdxDocument(resource);

		} catch (Exception e) {
			logger.error("Could not process CreateSpdxSbom request", e);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	public LibraryDocumentation documentLibraries(AccessRequestContext<DocumentLibraries> context) {

		DocumentLibraries request = context.getRequest();

		LibraryDocumentation result = LibraryDocumentation.T.create();

		PersistenceGmSession librarySession = sessionFactory.newSession(LibraryConstants.LIBRARY_ACCESS_ID);
		try {
			List<String> dependencyList = resolveDependencies(request);

			// Collect all libraries
			TreeSet<String> librariesFound = new TreeSet<>();
			List<Library> libraries = dependencyExpert.getLibraries(request, librarySession, dependencyList, librariesFound, request);

			File finalResult = documentationGenerator.generateLibraryDocumentation(librarySession, request, libraries);

			Resource callResource = Resource.createTransient(() -> new DeleteOnCloseFileInputStream(finalResult));

			callResource.setName("librariesDocumentation.pdf");
			callResource.setMimeType("application/pdf");
			callResource.setFileSize(finalResult.length());
			callResource.setCreated(new Date());

			result.setLibraryDocumentation(callResource);

			result.setSuccess(true);

		} catch (Exception e) {
			logger.error("Could not process DocumentLibraries request", e);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	private List<String> resolveDependencies(ArtifactReferences artifactReferences) {

		Map<String, DependencyResolutionResult> artifactInformationMap = dependencyExpert.resolveArtifactAndDependencies(artifactReferences);

		final Set<String> combinedDeps = new HashSet<>();
		artifactInformationMap.values().forEach(artifactInformation -> {

			if (artifactReferences.getIncludeTerminalArtifact()) {
				ArtifactIdentification resolvedArtifact = artifactInformation.getResolvedArtifact();
				combinedDeps.add(resolvedArtifact.asString());
			}
			Collection<Artifact> artifactList = artifactInformation.getDependencies();
			if (artifactList != null) {
				for (Artifact artifact : artifactList) {
					combinedDeps.add(artifact.asString());
				}
			}
		});

		return new ArrayList<>(combinedDeps);
	}

	@Required
	@Configurable
	public void setSessionFactory(PersistenceGmSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Required
	@Configurable
	public void setDependencyExpert(DependencyExpert dependencyExpert) {
		this.dependencyExpert = dependencyExpert;
	}
	@Configurable
	@Required
	public void setDocumentationGenerator(LibraryDocumentationGenerator documentationGenerator) {
		this.documentationGenerator = documentationGenerator;
	}
	@Configurable
	@Required
	public void setSpdxSupport(SpdxSupport spdxSupport) {
		this.spdxSupport = spdxSupport;
	}

}
