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

import com.braintribe.cartridge.common.wire.contract.HttpContract;
import com.braintribe.cartridge.common.wire.contract.MarshallingContract;
import com.braintribe.cartridge.extension.wire.contract.ResourcesContract;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

@Managed
public class DeployablesSpace implements WireSpace {

	@Import
	protected DeploymentSpace deployment;

	@Import
	protected CartridgeClientSpace cartridgeClient;
	
	@Import
	protected ResourcesContract resources;
	
	@Import
	protected ServletsSpace servlets;
	
	@Import
	protected RpcSpace rpc;
	
	@Import
	protected HttpContract http;

	@Import
	private MarshallingContract marshalling;


}
