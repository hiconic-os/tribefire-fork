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
package com.braintribe.swagger.util;

import org.apache.commons.lang.NotImplementedException;

import com.braintribe.model.exchange.ExchangePackage;
import com.braintribe.model.exchangeapi.ReadFromResource;
import com.braintribe.model.exchangeapi.ReadFromResourceResponse;
import com.braintribe.model.processing.exchange.service.FromResourceReader;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;

public class ExchangePackageResourceToExchangePackage implements Transformation<Resource, ExchangePackage> {

	@Override
    public ExchangePackage transform(Resource resource, PersistenceGmSession session) {
		final ReadFromResource rfr = ReadFromResource.T.create();
		rfr.setResource(resource);

		final FromResourceReader frr = new FromResourceReader(rfr);
		final ReadFromResourceResponse rfrr = frr.run();
		final Object assembly = rfrr.getAssembly();

		if (!(assembly instanceof ExchangePackage)) {
			throw new NotImplementedException("Decoded assembly is not an exchange package.");
		}
		
		return (ExchangePackage)assembly;
    }


}