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
package com.braintribe.model.processing.zargo.wire;


import java.util.List;

import com.braintribe.model.processing.xmi.converter.wire.XmiConverterModule;
import com.braintribe.model.processing.zargo.wire.contract.WorkerContract;
import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;
import com.braintribe.wire.api.util.Lists;

/**
 * the wire module for the zargo feature set
 * @author pit
 *
 */
public enum ZargoConverterModule implements WireTerminalModule<WorkerContract> {
	INSTANCE;
	
	@Override
	public List<WireModule> dependencies() {
		return Lists.list( XmiConverterModule.INSTANCE);
	}
	
}
