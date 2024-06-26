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
package tribefire.extension.demo.demo_wb_initializer.wire.contract;

import com.braintribe.model.resource.Resource;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;

/**
 * <p>
 * Those resources are being imported via ResourcePriming asset
 * <code>tribefire.extension.demo:demo-wb-resources</code>. <br>
 * 
 * The ResourcePriming asset takes care of creating and importing fully qualified resources. By that, this initializer
 * just needs to depend on them, as they already exist in the system at this time. <b>Note:</b> Please take care of the
 * dependency order of your assets! <br>
 */
@InstanceLookup(lookupOnly = true, globalIdPrefix = DemoWbInitializerResourceContract.GLOBAL_ID_PREFIX)
public interface DemoWbInitializerResourceContract extends WireSpace {

	String RESOURCE_ASSET_NAME = "tribefire.extension.demo:demo-wb-resources";
	String GLOBAL_ID_PREFIX = "asset-resource://" + RESOURCE_ASSET_NAME + "/";
	
	/**
	 * The globalIdPrefix of the InstanceLookup annotation is being applied on all globalId annotations here.
	 */
	
	@GlobalId("logo.png")
	Resource logoPng();
	
	@GlobalId("tribefire-16.png")
	Resource tribefire16Png();

	@GlobalId("person-16.png")
	Resource person16Png();

	@GlobalId("person-32.png")
	Resource person32Png();
	
	@GlobalId("person-64.png")
	Resource person64Png();

	@GlobalId("company-16.png")
	Resource company16Png();
	
	@GlobalId("company-32.png")
	Resource company32Png();
	
	@GlobalId("company-64.png")
	Resource company64Png();
	
}
