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

import java.util.List;

import com.braintribe.model.license.License;
import com.braintribe.model.processing.license.exception.LicenseLoadException;
import com.braintribe.model.processing.license.exception.LicenseViolatedException;
import com.braintribe.model.processing.license.exception.NoLicenseConfiguredException;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.OrderingDirection;

public class LicenseLoaderUtil {

	public static License getLicenseResource(PersistenceGmSession session) throws LicenseViolatedException {
		
		EntityQuery agreementQuery = EntityQueryBuilder.from(License.class)
				.where()
					.property(License.active).eq(true)
				.orderBy(License.uploadDate, OrderingDirection.descending)
				.done();
		
		License license = null;
		try {
			license = session.query().entities(agreementQuery).first();
		} catch(Exception e) {
			throw new LicenseLoadException("Could not query for License in the provided session.", e);
		}

		if (license == null)
			throw new NoLicenseConfiguredException("Could not find any license.");

		return license;
		
	}


	public static List<License> getLicenses(PersistenceGmSession session, License excludedLicense) throws Exception {
		
		EntityQuery agreementQuery = EntityQueryBuilder.from(License.class)
				.where()
					.property(License.id).ne(excludedLicense.getId())
				.done();
		List<License> licenseList = session.query().entities(agreementQuery).list();
		return licenseList;
		
	}
}
