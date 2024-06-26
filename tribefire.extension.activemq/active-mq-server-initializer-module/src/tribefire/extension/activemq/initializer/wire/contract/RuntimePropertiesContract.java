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
package tribefire.extension.activemq.initializer.wire.contract;

import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;

public interface RuntimePropertiesContract extends PropertyLookupContract {
	
	@Default("false")
	boolean AMQ_DEPLOYMENT_START();
	
	@Default("0.0.0.0")
	String AMQ_SERVER_BINDADDRESS();

	@Default("61616")
	int AMQ_SERVER_PORT();

	@Default("activemq-data")
	String AMQ_SERVER_DATA_DIRECTORY();

	String AMQ_SERVER_BROKER_NAME();

	@Default("true")
	boolean AMQ_SERVER_USE_JMX();

	@Default("activemq-db")
	String AMQ_SERVER_PERSISTENCE_DB_DIRECTORY();
	
	@Default("70")
	Integer AMQ_SERVER_HEAP_USAGE_IN_PERCENT();
	
	Long AMQ_SERVER_DISK_USAGE_LIMIT();
	
	Long AMQ_SERVER_TEMP_USAGE_LIMIT();
	
	Boolean AMQ_SERVER_CREATE_VM_CONNECTOR();
	
	@Default("false")
	boolean AMQ_SERVER_PERSISTENT();
	
	String AMQ_CLUSTER_NODES();
	
	String AMQ_DISCOVERY_MULTICAST_URI();
	String AMQ_DISCOVERY_MULTICAST_GROUP();
	String AMQ_DISCOVERY_MULTICAST_NETWORK_INTERFACE();
	String AMQ_DISCOVERY_MULTICAST_ADDRESS();
	String AMQ_DISCOVERY_MULTICAST_INTERFACE();
	
}
