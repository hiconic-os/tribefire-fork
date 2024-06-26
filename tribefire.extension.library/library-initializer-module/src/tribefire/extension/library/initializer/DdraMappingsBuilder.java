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
package tribefire.extension.library.initializer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.braintribe.model.ddra.DdraMapping;
import com.braintribe.model.ddra.DdraUrlMethod;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.library.service.dependencies.GetDependencies;
import com.braintribe.model.library.service.documentation.DocumentLibraries;
import com.braintribe.model.library.service.license.CheckLicenseAvailability;
import com.braintribe.model.library.service.license.CreateLicenseImportSpreadsheet;
import com.braintribe.model.library.service.license.ImportSpreadsheet;
import com.braintribe.model.library.service.license.LicenseImportSpreadsheet;
import com.braintribe.model.library.service.vulnerabilities.CheckVulnerabilities;
import com.braintribe.model.library.service.vulnerabilities.UpdateNvdMirror;
import com.braintribe.model.library.service.vulnerabilities.Vulnerabilities;
import com.braintribe.model.library.service.vulnerabilities.VulnerabilityDatabaseMaintenance;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.service.api.ServiceRequest;

public class DdraMappingsBuilder {

	private static final String RESOURCE = "resource";
	private static final String REACHABLE = "reachable";

	public static final String TAG_DEPENDENCIES = "Dependencies";
	public static final String TAG_VULNERABILITIES = "Vulnerabilities";
	public static final String TAG_LICENSES = "Licenses";

	protected Set<DdraMapping> mappings = new HashSet<>();
	protected Function<String, GmEntityType> typeLookup;
	protected String accessId;
	protected Function<EntityType<?>, GenericEntity> instanceFactory;

	public DdraMappingsBuilder(String domainId, Function<String, GmEntityType> typeLookup, Function<EntityType<?>, GenericEntity> instanceFactory) {
		this.typeLookup = typeLookup;
		this.accessId = domainId;
		this.instanceFactory = instanceFactory;
	}

	public Set<DdraMapping> build() {
		this.configureMappings();
		return mappings;
	}

	// ***************************************************************************************************
	// Helper
	// ***************************************************************************************************

	protected void configureMappings() {
		//@formatter:off

		mappings.add(ddraMapping(
				"dependencies",
				DdraUrlMethod.GET,
				GetDependencies.T,
				TAG_DEPENDENCIES,
				null,
				REACHABLE));

		mappings.add(ddraMapping(
				"dependencies/documentation",
				DdraUrlMethod.GET,
				DocumentLibraries.T,
				TAG_DEPENDENCIES,
				null,
				REACHABLE));

		mappings.add(ddraMapping(
				"licenses/check-availability",
				DdraUrlMethod.GET,
				CheckLicenseAvailability.T,
				TAG_LICENSES,
				null,
				REACHABLE));

		mappings.add(ddraDownloadMapping(
				"licenses/create-import-spreadsheet",
				CreateLicenseImportSpreadsheet.T,
				TAG_LICENSES,
				LicenseImportSpreadsheet.licenseImportSpreadsheet));

		mappings.add(ddraMapping(
				"licenses/import-spreadsheet",
				DdraUrlMethod.POST,
				ImportSpreadsheet.T,
				TAG_LICENSES,
				null,
				REACHABLE));

		mappings.add(ddraDownloadMapping(
				"vulnerabilities/check",
				CheckVulnerabilities.T,
				TAG_VULNERABILITIES,
				Vulnerabilities.report));

		mappings.add(ddraMapping(
				"vulnerabilities/update-nvd-mirror",
				DdraUrlMethod.POST,
				UpdateNvdMirror.T,
				TAG_VULNERABILITIES,
				null,
				REACHABLE));
		
		mappings.add(ddraMapping(
				"vulnerabilities/maintenance",
				DdraUrlMethod.POST,
				VulnerabilityDatabaseMaintenance.T,
				TAG_VULNERABILITIES,
				null,
				REACHABLE));
		
		//@formatter:on
	}

	protected DdraMapping ddraMapping(String relativePath, DdraUrlMethod method, EntityType<? extends ServiceRequest> reflectionType, String tag) {
		return ddraMapping(relativePath, method, reflectionType, tag, 1, null, false, null);
	}

	protected DdraMapping ddraMapping(String relativePath, DdraUrlMethod method, EntityType<? extends ServiceRequest> reflectionType, String tag,
			String projection) {
		return ddraMapping(relativePath, method, reflectionType, tag, projection, null);
	}

	protected DdraMapping ddraMapping(String relativePath, DdraUrlMethod method, EntityType<? extends ServiceRequest> reflectionType, String tag,
			String projection, String depth) {
		return ddraMapping(relativePath, method, reflectionType, tag, 1, projection, method == DdraUrlMethod.POST, depth);
	}

	protected DdraMapping ddraMapping(String relativePath, DdraUrlMethod method, EntityType<? extends ServiceRequest> reflectionType, String tag,
			int version, String projection, boolean announceAsMultipart, String depth) {
		GmEntityType type = this.typeLookup.apply("type:" + reflectionType.getTypeSignature());
		String path = "/" + accessId + "/v" + version + "/" + relativePath;

		DdraMapping bean = (DdraMapping) this.instanceFactory.apply(DdraMapping.T);
		bean.setGlobalId("ddra:/" + method + "/" + path + "/" + accessId);
		bean.setPath(path);
		bean.setRequestType(type);
		bean.setMethod(method);
		bean.setDefaultServiceDomain(accessId);
		bean.setDefaultMimeType("application/json");
		bean.setHideSerializedRequest(true);
		bean.setAnnounceAsMultipart(announceAsMultipart);
		if (projection != null) {
			bean.setDefaultProjection(projection);
		}

		if (tag != null) {
			bean.getTags().add(tag);
		}

		if (depth != null) {
			bean.setDefaultDepth(depth);
		}

		bean.setDefaultUseSessionEvaluation(false);
		bean.setDefaultEntityRecurrenceDepth(-1);

		return bean;
	}

	private DdraMapping ddraDownloadMapping(String relativePath, EntityType<? extends ServiceRequest> reflectionType, String tag, String projection) {
		DdraMapping downloadMapping = ddraMapping(relativePath, DdraUrlMethod.GET, reflectionType, tag);
		downloadMapping.setDefaultProjection(projection);
		downloadMapping.setDefaultDownloadResource(true);
		downloadMapping.setDefaultSaveLocally(true);
		return downloadMapping;
	}

}
