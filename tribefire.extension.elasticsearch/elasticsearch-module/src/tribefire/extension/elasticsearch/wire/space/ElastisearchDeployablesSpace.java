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
package tribefire.extension.elasticsearch.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.elasticsearch.processing.ElasticsearchProcessor;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class ElastisearchDeployablesSpace implements WireSpace {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	// -----------------------------------------------------------------------
	// PROCESSOR
	// -----------------------------------------------------------------------

	@Managed
	public ElasticsearchProcessor elasticsearchProcessor() {
		ElasticsearchProcessor bean = new ElasticsearchProcessor();
		bean.setCortexSessionSupplier(tfPlatform.systemUserRelated().cortexSessionSupplier());
		return bean;
	}

}
