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
package com.braintribe.model.processing.library.service;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.InitializationAware;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.library.service.LibraryBaseRequest;
import com.braintribe.model.library.service.LibraryBaseResult;
import com.braintribe.model.library.service.dependencies.GetDependencies;
import com.braintribe.model.library.service.documentation.CreateSpdxSbom;
import com.braintribe.model.library.service.documentation.DocumentLibraries;
import com.braintribe.model.library.service.license.CheckLicenseAvailability;
import com.braintribe.model.library.service.license.CreateLicenseImportSpreadsheet;
import com.braintribe.model.library.service.license.ExportLicenseSpreadsheet;
import com.braintribe.model.library.service.license.ImportSpreadsheet;
import com.braintribe.model.library.service.vulnerabilities.CheckVulnerabilities;
import com.braintribe.model.library.service.vulnerabilities.UpdateNvdMirror;
import com.braintribe.model.library.service.vulnerabilities.VulnerabilityDatabaseMaintenance;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessors;
import com.braintribe.model.processing.library.service.expert.DependencyExpert;
import com.braintribe.model.processing.library.service.expert.DocumentationExpert;
import com.braintribe.model.processing.library.service.expert.LicenseExpert;
import com.braintribe.model.processing.library.service.expert.VulnerabilitiesExpert;

public class LibraryServiceProcessor implements AccessRequestProcessor<LibraryBaseRequest, LibraryBaseResult>, InitializationAware {

	private final static Logger logger = Logger.getLogger(LibraryServiceProcessor.class);

	private LicenseExpert licenseExpert;
	private DependencyExpert dependencyExpert;
	private VulnerabilitiesExpert vulnerabilitiesExpert;
	private DocumentationExpert documentationExpert;

	private AccessRequestProcessor<LibraryBaseRequest, LibraryBaseResult> delegate;

	@Override
	public LibraryBaseResult process(AccessRequestContext<LibraryBaseRequest> context) {
		return delegate.process(context);
	}

	// com.microsoft.sqlserver.jdbc.SQLServerDriver
	// jdbc:sqlserver://db-vm-sql2008.braintribe:1433;databaseName=INF_Licenses;sendStringParametersAsUnicode=false;integratedSecurity=false;encrypt=false;trustServerCertificate=true

	@Configurable
	@Required
	public void setLicenseExpert(LicenseExpert licenseExpert) {
		this.licenseExpert = licenseExpert;
	}
	@Required
	@Configurable
	public void setDependencyExpert(DependencyExpert dependencyExpert) {
		this.dependencyExpert = dependencyExpert;
	}
	@Required
	@Configurable
	public void setVulnerabilitiesExpert(VulnerabilitiesExpert vulnerabilitiesExpert) {
		this.vulnerabilitiesExpert = vulnerabilitiesExpert;
	}
	@Required
	@Configurable
	public void setDocumentationExpert(DocumentationExpert documentationExpert) {
		this.documentationExpert = documentationExpert;
	}

	@Override
	public void postConstruct() {
		delegate = AccessRequestProcessors.dispatcher(dispatching -> {
			dispatching.register(GetDependencies.T, dependencyExpert::getDependencies);

			dispatching.register(DocumentLibraries.T, documentationExpert::documentLibraries);
			dispatching.register(CreateSpdxSbom.T, documentationExpert::createSpdxSbom);

			dispatching.register(CheckLicenseAvailability.T, licenseExpert::checkLicenseAvailability);
			dispatching.register(CreateLicenseImportSpreadsheet.T, licenseExpert::createLicenseImportSpreadsheet);
			dispatching.register(ImportSpreadsheet.T, licenseExpert::importSpreadsheet);
			dispatching.register(ExportLicenseSpreadsheet.T, licenseExpert::exportLicenseSpreadsheet);

			dispatching.register(CheckVulnerabilities.T, vulnerabilitiesExpert::checkVulnerabilities);
			dispatching.register(VulnerabilityDatabaseMaintenance.T, vulnerabilitiesExpert::vulnerabilityDatabaseMaintenance);
			dispatching.register(UpdateNvdMirror.T, vulnerabilitiesExpert::updateNvdMirror);
		});
	}

}
