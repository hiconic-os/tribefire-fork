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
package tribefire.cortex.assets.darktheme_wb_initializer.wire.contract;

import com.braintribe.model.resource.Resource;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;

@InstanceLookup(lookupOnly=true, globalIdPrefix=DarkthemeWbResourceContract.GLOBAL_ID_PREFIX)
public interface DarkthemeWbResourceContract extends WireSpace {

	String RESOURCE_ASSET_NAME = "tribefire.cortex.assets:tribefire-standard-wb-resources";
	String GLOBAL_ID_PREFIX = "asset-resource://" + RESOURCE_ASSET_NAME + "/";
	
	@GlobalId("run-16.png")
	Resource run16Png();

	@GlobalId("run-32.png")
	Resource run32Png();
	
	@GlobalId("logo.png")
	Resource logoPng();

	@GlobalId("home-16.png")
	Resource home16Png();
	@GlobalId("home-32.png")
	Resource home32Png();
	@GlobalId("home-64.png")
	Resource home64Png();

	@GlobalId("changes-16.png")
	Resource changes16Png();
	@GlobalId("changes-32.png")
	Resource changes32Png();
	@GlobalId("changes-64.png")
	Resource changes64Png();

	@GlobalId("clipboard-16.png")
	Resource clipboard16Png();
	@GlobalId("clipboard-32.png")
	Resource clipboard32Png();
	@GlobalId("clipboard-64.png")
	Resource clipboard64Png();

	@GlobalId("notification-16.png")
	Resource notification16Png();
	@GlobalId("notification-32.png")
	Resource notification32Png();
	@GlobalId("notification-64.png")
	Resource notification64Png();

	@GlobalId("magnifier-16.png")
	Resource magnifier16Png();

	@GlobalId("explorer-style-template.css")
	Resource explorerStyleTemplateCss();

	@GlobalId("new-16.png")
	Resource new16Png();
	
	@GlobalId("new-32.png")
	Resource new32Png();

	@GlobalId("upload-32.png")
	Resource upload32Png();

	@GlobalId("undo-32.png")
	Resource undo32Png();

	@GlobalId("redo-32.png")
	Resource redo32Png();

	@GlobalId("commit-32.png")
	Resource commit32Png();
}
