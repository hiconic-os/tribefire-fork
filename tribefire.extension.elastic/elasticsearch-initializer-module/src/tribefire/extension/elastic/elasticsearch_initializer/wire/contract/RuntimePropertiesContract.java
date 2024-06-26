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
package tribefire.extension.elastic.elasticsearch_initializer.wire.contract;

import java.util.Set;

import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;

public interface RuntimePropertiesContract extends PropertyLookupContract {

	@Default("true")
	boolean ELASTIC_RUN_SERVICE();

	String ELASTIC_ACCESS_INDEX(String defaultValue);

	String ELASTIC_HOST(String defaultValue);
	@Default("9200")
	int ELASTIC_HTTP_PORT(); // REST interface

	@Default("false")
	boolean ELASTIC_CREATE_DEMO_ACCESS();

	@Default("resources/res")
	String ELASTIC_SERVICE_BASE_PATH();
	@Default("9300")
	int ELASTIC_PORT(); // Intercommunication between nodes
	String ELASTIC_SERVICE_DATA_PATH();

	@Default("elasticsearch")
	String ELASTIC_CLUSTER_NAME();

	Set<String> ELASTIC_BIND_HOSTS();
	String ELASTIC_PUBLISH_HOST();
	Set<String> ELASTIC_REPOSITORY_PATHS();
	Integer ELASTIC_RECOVER_AFTER_NODES();
	Integer ELASTIC_RECOVER_AFTER_TIME_IN_MS();
	Integer ELASTIC_EXPECTED_NODES();
	Set<String> ELASTIC_CLUSTER_NODES();
}
