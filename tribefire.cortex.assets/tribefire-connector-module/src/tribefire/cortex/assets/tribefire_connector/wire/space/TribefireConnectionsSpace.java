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
package tribefire.cortex.assets.tribefire_connector.wire.space;

import com.braintribe.exception.Exceptions;
import com.braintribe.logging.Logger;
import com.braintribe.model.deployment.tribefire.connector.LocalTribefireConnection;
import com.braintribe.model.deployment.tribefire.connector.RemoteTribefireConnection;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.session.GmSessionFactories;
import com.braintribe.model.processing.session.GmSessionFactoryBuilderException;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.model.securityservice.credentials.Credentials;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.module.wire.contract.RequestUserRelatedContract;
import tribefire.module.wire.contract.SystemUserRelatedContract;

@Managed
public class TribefireConnectionsSpace implements WireSpace {

	private static final Logger logger = Logger.getLogger(TribefireConnectionsSpace.class);

	@Import
	private SystemUserRelatedContract systemUserRelated;
	
	@Import
	private RequestUserRelatedContract requestUserRelated; 

	@Managed
	public PersistenceGmSessionFactory localTribefireConnection(ExpertContext<LocalTribefireConnection> context) {
		LocalTribefireConnection deployable = context.getDeployable();
		return localTribefireConnection(deployable);
	}
	
	public PersistenceGmSessionFactory localTribefireConnection(LocalTribefireConnection connection) {
		
		logger.debug(() -> "Creating local session factory for "+connection);

		boolean systemSessionFactory = connection.getSystemSession();
		
		PersistenceGmSessionFactory sessionFactory = systemSessionFactory ? systemUserRelated.sessionFactory() : requestUserRelated.sessionFactory();

		return sessionFactory;
	}


	@Managed
	public PersistenceGmSessionFactory remoteTribefireConnection(ExpertContext<RemoteTribefireConnection> context) {
		RemoteTribefireConnection deployable = context.getDeployable();
		return remoteTribefireConnection(deployable);
	}
	
	public PersistenceGmSessionFactory remoteTribefireConnection(RemoteTribefireConnection connection) {
		
		String url = connection.getServicesUrl();
		Credentials cred = connection.getCredentials();

		logger.debug(() -> "Creating remote session factory to "+url);

		PersistenceGmSessionFactory sessionFactory = null;

		try {
			sessionFactory = GmSessionFactories
					.remote(url)
					.authentication(cred)
					.done();
		} catch (GmSessionFactoryBuilderException e) {
			throw Exceptions.unchecked(e, "Error while trying to create a remote session factory to "+url);
		}

		return sessionFactory;
	}
	
}
