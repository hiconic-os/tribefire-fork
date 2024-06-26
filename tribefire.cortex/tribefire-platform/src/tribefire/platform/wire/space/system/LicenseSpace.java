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
package tribefire.platform.wire.space.system;

import com.braintribe.exception.Exceptions;
import com.braintribe.model.processing.license.LicenseManager;
import com.braintribe.model.processing.license.glf.GlfLicenseManager;
import com.braintribe.model.processing.license.glf.LicenseResourceLoader;
import com.braintribe.model.processing.license.glf.processor.LicenseResourceProcessor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.platform.wire.space.cortex.accesses.CortexAccessSpace;

@Managed
public class LicenseSpace implements WireSpace {

	@Import
	private CortexAccessSpace cortexAccess;

	@Managed
	public LicenseManager manager() {
		GlfLicenseManager bean = new GlfLicenseManager();
		bean.setLicenseLoader(resourceLoader());
		bean.setSessionProvider(cortexAccess.sessionProvider());
		return bean;
	}

	@Managed
	public LicenseResourceProcessor licenseResourceProcessor() {
		LicenseResourceProcessor bean = new LicenseResourceProcessor();
		bean.setCortexSessionSupplier(cortexAccess.sessionProvider());
		return bean;
	}

	@Managed
	public LicenseResourceLoader resourceLoader() {
		try {
			LicenseResourceLoader bean = new LicenseResourceLoader();
			bean.setSessionProvider(cortexAccess.sessionProvider());
			return bean;
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "Failed to create a " + LicenseResourceLoader.class.getName());
		}
	}

}
