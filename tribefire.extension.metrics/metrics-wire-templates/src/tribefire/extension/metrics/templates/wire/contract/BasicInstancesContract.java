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
package tribefire.extension.metrics.templates.wire.contract;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.metrics.templates.api.MetricsTemplateContext;

/**
 * Contract to access basic managed instances - e.g. from CoreInstancesContract
 * 
 *
 */
public interface BasicInstancesContract extends WireSpace {

	IncrementalAccess workbenchAccess(MetricsTemplateContext context);

	GmMetaModel essentialMetaDataModel(MetricsTemplateContext context);

	GmMetaModel workbenchModel(MetricsTemplateContext context);

}
