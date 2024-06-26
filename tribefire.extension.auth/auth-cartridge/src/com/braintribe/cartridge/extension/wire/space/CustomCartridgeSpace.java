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
package com.braintribe.cartridge.extension.wire.space;

import com.braintribe.cartridge.common.processing.deployment.DenotationTypeBindingsConfig;
import com.braintribe.cartridge.common.wire.contract.CommonComponentsContract;
import com.braintribe.cartridge.extension.api.customization.CartridgeCustomization;
import com.braintribe.cartridge.extension.processing.customization.ConfigurableCartridgeCustomization;
import com.braintribe.cartridge.extension.wire.contract.CustomCartridgeContract;
import com.braintribe.cartridge.extension.wire.contract.MasterComponentsContract;
import com.braintribe.cartridge.extension.wire.contract.WebRegistryContract;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

@Managed
public class CustomCartridgeSpace implements CustomCartridgeContract {

	private final static String CARTRIDGE_EXTERNALID = "tribefire.extension.auth.auth-cartridge";

	@Import
	protected DeployablesSpace deployables;

	@Import
	protected MasterComponentsContract masterComponents;

	@Import
	private CommonComponentsContract commonComponents;

	@Import
	private DefaultDeployablesSpace defaultDeployables;
	
	@Import
	private WebRegistryContract webRegistry;
	
	
	@Managed
	@Override
	public CartridgeCustomization customization() {
		ConfigurableCartridgeCustomization bean = new ConfigurableCartridgeCustomization();
		bean.setExternalId(CARTRIDGE_EXTERNALID);
		bean.setExtensions(extensions());
		return bean;
	}
	

	@Managed
	public DenotationTypeBindingsConfig extensions() {
		DenotationTypeBindingsConfig bean = new DenotationTypeBindingsConfig();
		
		//@formatter:off
		
		
		//@formatter:on
		
		return bean;
	}
	
}
