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
package tribefire.extension.metrics.templates.wire.space;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.metrics.templates.api.MetricsTemplateContext;
import tribefire.extension.metrics.templates.wire.contract.BasicInstancesContract;

@Managed
public class BasicInstancesSpace implements WireSpace, BasicInstancesContract {

	@Override
	public IncrementalAccess workbenchAccess(MetricsTemplateContext context) {
		return context.lookup("hardwired:access/workbench");
	}

	@Override
	public GmMetaModel essentialMetaDataModel(MetricsTemplateContext context) {
		return context.lookup("model:com.braintribe.gm:essential-meta-data-model");
	}

	@Override
	public GmMetaModel workbenchModel(MetricsTemplateContext context) {
		return context.lookup("model:com.braintribe.gm:workbench-model");
	}

}
