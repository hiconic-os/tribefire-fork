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
package com.braintribe.model.processing.license.glf.processor;

import java.io.InputStream;

import com.auxilii.glf.client.exception.SystemException;
import com.auxilii.glf.client.loader.XMLLoader;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.license.glf.LicenseResourceLoader;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.resource.ResourceAccess;
import com.braintribe.model.resource.Resource;

public class ProvidedLicenseResourceLoader extends XMLLoader {

	protected static Logger logger = Logger.getLogger(LicenseResourceLoader.class);
	
	protected Resource licenseResource = null;
	protected PersistenceGmSession session = null;
	
	public ProvidedLicenseResourceLoader(PersistenceGmSession session, Resource licenseResource) throws SystemException {
		super();
		this.session = session;
		this.licenseResource = licenseResource;
	}

	@Override
	protected InputStream openLicenseStream() throws Exception {
		
		if (this.licenseResource == null)
			throw new Exception("No license resource available.");
		
		if (logger.isTraceEnabled())
			logger.trace("Opening license resource "+this.licenseResource.getId());
		
		ResourceAccess resourceAccess = this.session.resources();
		InputStream is = resourceAccess.retrieve(this.licenseResource).stream();
		
		return is;
	}

	@Override
	protected void saveState() {
		//Nothing to do here
	}
	
	@Override
	protected boolean stateChanged() {
		return false;
	}
}
