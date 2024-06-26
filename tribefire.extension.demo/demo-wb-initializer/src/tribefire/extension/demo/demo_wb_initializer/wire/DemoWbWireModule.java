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
package tribefire.extension.demo.demo_wb_initializer.wire;

import static com.braintribe.wire.api.util.Lists.list;

import java.util.List;

import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.assets.darktheme_wb_initializer.wire.DarkthemeWbWireModule;
import tribefire.cortex.assets.default_wb_initializer.wire.DefaultWbWireModule;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerMainContract;

/**
 * <p>
 * This is the WireModule of the demo-workbench-initializer. Within this module you can depend on other WireModules to
 * use their provided functionality. <br>
 * 
 * <p>
 * This WireModule provides the initializer's main contract. It is highly recommended that every initializer contains a
 * main contract to be extensible (once a contract is bound to its WireModule, an interface change would often
 * result in breaking changes).
 * 
 */
public enum DemoWbWireModule implements WireTerminalModule<DemoWbInitializerMainContract>{
	INSTANCE;

	@Override
	public Class<DemoWbInitializerMainContract> contract() {
		return DemoWbInitializerMainContract.class;
	}
	
	/**
	 * The demo workbench has the grayish-blue-style applied. Therefore we depend on the respective WireModule
	 * coming from asset <code>tribefire.cortex.assets:tribefire-grayish-blue-style-initializer</code>.
	 */
	@Override
	public List<WireModule> dependencies() {
		return list(DefaultWbWireModule.INSTANCE, DarkthemeWbWireModule.INSTANCE);
	}
}
