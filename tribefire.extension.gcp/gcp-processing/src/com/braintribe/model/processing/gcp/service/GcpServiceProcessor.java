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
package com.braintribe.model.processing.gcp.service;

import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.gcp.deployment.GcpConnector;
import com.braintribe.model.gcp.service.CheckConnection;
import com.braintribe.model.gcp.service.ConnectionStatus;
import com.braintribe.model.gcp.service.GcpRequest;
import com.braintribe.model.gcp.service.GcpResult;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor;
import com.braintribe.model.processing.accessrequest.api.AccessRequestProcessors;
import com.braintribe.model.processing.deployment.api.DeployRegistry;
import com.braintribe.model.processing.deployment.api.DeployedUnit;
import com.braintribe.model.processing.gcp.connect.GcpStorage;
import com.braintribe.model.processing.gcp.connect.GcpStorageConnector;

public class GcpServiceProcessor implements AccessRequestProcessor<GcpRequest, GcpResult>, LifecycleAware {

	private final static Logger logger = Logger.getLogger(GcpServiceProcessor.class);

	private DeployRegistry deployRegistry;
	

	private AccessRequestProcessor<GcpRequest, GcpResult> delegate = AccessRequestProcessors.dispatcher(dispatching -> {
		dispatching.register(CheckConnection.T, this::checkConnection);
	});

	
	public ConnectionStatus checkConnection(AccessRequestContext<CheckConnection> context) {
		
		ConnectionStatus result = ConnectionStatus.T.create();
		
		CheckConnection request = context.getRequest();
		GcpConnector connector = request.getConnector();
		
		DeployedUnit resolve = deployRegistry.resolve(connector);
		
		long start = System.currentTimeMillis();
		if (resolve != null) {
			GcpStorageConnector connectorImpl = (GcpStorageConnector) resolve.findComponent(GcpConnector.T);
			
			setConnectionStatus(result, connectorImpl);
		}
		
		result.setDurationInMs(System.currentTimeMillis()-start);
		
		return result;
	}
	
	protected static void setConnectionStatus(ConnectionStatus status, GcpStorageConnector connector) {
		
		GcpStorage storage = connector.getStorage();
		
		int totalCount = storage.getBucketCount(500);
		
		status.setBucketCount(totalCount);
	}

	
	@Override
	public GcpResult process(AccessRequestContext<GcpRequest> context) {
		return delegate.process(context);
	}
	@Override
	public void postConstruct() {
		logger.debug(() -> GcpServiceProcessor.class.getSimpleName() + " deployed.");
	}

	@Override
	public void preDestroy() {
		logger.debug(() -> GcpServiceProcessor.class.getSimpleName() + " undeployed.");
	}

	@Required
	public void setDeployRegistry(DeployRegistry deployRegistry) {
		this.deployRegistry = deployRegistry;
	}

}
