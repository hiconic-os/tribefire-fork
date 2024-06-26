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
package tribefire.extension.demo.demo_wb_initializer.wire.space;

import static com.braintribe.wire.api.util.Sets.set;

import com.braintribe.model.resource.AdaptiveIcon;
import com.braintribe.model.resource.Icon;
import com.braintribe.model.resource.SimpleIcon;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerIconContract;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerResourceContract;

@Managed
public class DemoWbInitializerIconSpace extends AbstractInitializerSpace implements DemoWbInitializerIconContract {

	@Import
	DemoWbInitializerResourceContract resources;
	
	@Managed
	@Override
	public SimpleIcon logoIcon() {
		SimpleIcon bean = create(SimpleIcon.T);
		
		bean.setName("Logo Icon");
		bean.setImage(resources.logoPng());
		
		return bean;
	}

	@Managed
	@Override
	public Icon tribefireIcon() {
		SimpleIcon bean = create(SimpleIcon.T);
		
		bean.setName("tribefire Icon");
		bean.setImage(resources.tribefire16Png());
		
		return bean;
	}

	@Managed
	@Override
	public Icon personIcon() {
		AdaptiveIcon bean = create(AdaptiveIcon.T);

		bean.setName("Person Icon");
		bean.setRepresentations(set(
				resources.person16Png(),
				resources.person32Png(),
				resources.person64Png()
				));
		
		return bean;
	}
	
	@Managed
	@Override
	public Icon companyIcon() {
		AdaptiveIcon bean = create(AdaptiveIcon.T);
		
		bean.setName("Company Icon");
		bean.setRepresentations(set(
				resources.company16Png(),
				resources.company32Png(),
				resources.company64Png()
				));
		
		return bean;
	}
	
}
