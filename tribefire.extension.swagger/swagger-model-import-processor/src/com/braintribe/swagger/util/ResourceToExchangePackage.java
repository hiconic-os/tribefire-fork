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

import java.util.function.Supplier;

import org.apache.commons.lang.NotImplementedException;

import com.braintribe.model.exchange.ExchangePackage;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.GmSessionFactoryBuilderException;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.usersession.UserSession;

public class ResourceToExchangePackage implements Transformation<Resource, ExchangePackage> {

	private final PersistenceGmSession session;
	private final Supplier<UserSession> userSessionProvider;

	public ResourceToExchangePackage(PersistenceGmSession session, Supplier<UserSession> userSessionProvider) {
		this.session = session;
        this.userSessionProvider = userSessionProvider;
	}

    @Override
	public ExchangePackage transform(Resource resource, PersistenceGmSession session) throws GmSessionException, GmSessionFactoryBuilderException {
    	final String mimeType = resource.getMimeType();
    	switch(mimeType) {
    		case MIME_TYPE_APPLICATION_ZIP:
        		return new ExchangePackageResourceToExchangePackage().transform(resource, session);
    		case MIME_TYPE_TEXT_TROFF:
//        		final GmMetaModel gmMetaModel = new ManipulationResourceToModel(session).transform(resource);
//        		final ExchangePackage exchangePackage = new ModelToExchangePackage(userSessionProvider).transform(gmMetaModel);
//        		return exchangePackage;
        	default:
            	throw new NotImplementedException("Resource [" + resource + "] has unknown mime-type [" + mimeType + "]");
    	}
    }

}