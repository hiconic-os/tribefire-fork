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
package com.braintribe.devrock.zarathud.wirings.main;

import java.util.Arrays;
import java.util.List;

import com.braintribe.devrock.zarathud.wirings.console.ZedConsoleOutWireModule;
import com.braintribe.devrock.zarathud.wirings.core.ZedCoreWireModule;
import com.braintribe.devrock.zarathud.wirings.forensic.ZedForensicsWireModule;
import com.braintribe.devrock.zarathud.wirings.main.contract.ZedMainContract;
import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;

public enum ZedWireTerminalModule implements WireTerminalModule<ZedMainContract>{
	INSTANCE;

	@Override
	public List<WireModule> dependencies() {	
		return Arrays.asList(  ZedCoreWireModule.INSTANCE, ZedForensicsWireModule.INSTANCE, ZedConsoleOutWireModule.INSTANCE);
	}
	
	
	
	
}
