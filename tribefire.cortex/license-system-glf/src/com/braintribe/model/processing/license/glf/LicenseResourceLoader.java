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
package com.braintribe.model.processing.license.glf;

import java.io.InputStream;
import java.util.function.Supplier;

import com.auxilii.glf.client.exception.SystemException;
import com.auxilii.glf.client.loader.XMLLoader;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.license.License;
import com.braintribe.model.processing.license.exception.LicenseLoadException;
import com.braintribe.model.processing.license.exception.LicenseViolatedException;
import com.braintribe.model.processing.license.exception.NoLicenseConfiguredException;
import com.braintribe.model.processing.license.exception.SessionUnavailableException;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.resource.ResourceAccess;
import com.braintribe.model.resource.Resource;

public class LicenseResourceLoader extends XMLLoader {

	protected static Logger logger = Logger.getLogger(LicenseResourceLoader.class);
	
	protected Supplier<PersistenceGmSession> sessionProvider = null;
	protected long nextLicenseRefresh = -1L;
	protected long refreshEveryMs = 10000L;
	
	public LicenseResourceLoader() throws SystemException {
		super();
	}

	@Override
	protected InputStream openLicenseStream() throws LicenseViolatedException {
		
		if (this.sessionProvider == null) {
			throw new SessionUnavailableException("No session provider is available.");
		}
		PersistenceGmSession session = null;
		try {
			session = this.sessionProvider.get();
		} catch(Exception e) {
			throw new SessionUnavailableException("Error while acquiring a session from the session provider.");
		}
		if (session == null) {
			throw new SessionUnavailableException("The session provider returned no session.");
		}
		
		License license = LicenseLoaderUtil.getLicenseResource(session);

		Resource licenseResource = license.getLicenseResource();
		if (licenseResource == null) {
			throw new NoLicenseConfiguredException("The license does not reference a resource.");
		}

		String resourceId = licenseResource.getId();
		
		if (logger.isTraceEnabled())
			logger.trace("Opening license resource "+resourceId);

		InputStream is = null;

		try {
			ResourceAccess resourceAccess = session.resources();
			is = resourceAccess.retrieve(licenseResource).stream();

			if (logger.isTraceEnabled())
				logger.trace("Successfully opened InputStream from resource "+resourceId);
			
			this.nextLicenseRefresh = System.currentTimeMillis() + this.refreshEveryMs;
			
		} catch(Throwable t) {
			throw new LicenseLoadException("Could not load license resource "+resourceId, t);
		}

		return is;
	}

	@Override
	protected void saveState() {
		//Nothing to do here
	}
	
	@Override
	protected boolean stateChanged() {
		long now = System.currentTimeMillis();
		if (now > this.nextLicenseRefresh) {
			this.nextLicenseRefresh = now + this.refreshEveryMs;
			return true;
		}
		return false;
	}

	@Required
	public void setSessionProvider(Supplier<PersistenceGmSession> sessionProvider) {
		this.sessionProvider = sessionProvider;
	}


}
