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
package com.braintribe.model.processing.zargo.wire.contract;

import com.braintribe.model.processing.zargo.MetaModelToZargoConverterWorker;
import com.braintribe.model.processing.zargo.ZargoToMetaModelConverterWorker;
import com.braintribe.wire.api.space.WireSpace;

/**
 * the {@link WireSpace} that gives access to the two workers
 * @author pit
 *
 */
public interface WorkerContract extends WireSpace {
	/**
	 * @return - a fully instrumented {@link MetaModelToZargoConverterWorker} to turn a GmMetaModel into a .zargo file 
	 */
	MetaModelToZargoConverterWorker metaModelToZargoWorker();
	
	/**
	 * @return a fully instrumented {@link ZargoToMetaModelConverterWorker} to turn a .zargo back into a GmMetaModel (if possible of course)
	 */
	ZargoToMetaModelConverterWorker zargoToMetaModelWorker();
	
}
