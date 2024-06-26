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
package tribefire.extension.modelling.common.wire.space;

import static com.braintribe.wire.api.util.Sets.set;

import com.braintribe.model.resource.AdaptiveIcon;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.modelling.common.wire.contract.CommonIconContract;
import tribefire.extension.modelling.common.wire.contract.CommonResourceContract;

@Managed
public class CommonIconSpace extends AbstractInitializerSpace implements CommonIconContract {

	@Import
	CommonResourceContract resources;

	@Managed
	@Override
	public AdaptiveIcon newIcon() {
		AdaptiveIcon bean = create(AdaptiveIcon.T);

		bean.setName("New Icon");
		bean.setRepresentations(set(resources.new16Png(), resources.new32Png(), resources.new64Png()));
		return bean;
	}
	
	@Managed
	@Override
	public AdaptiveIcon deleteIcon() {
		AdaptiveIcon bean = create(AdaptiveIcon.T);
		
		bean.setName("Delete Icon");
		bean.setRepresentations(set(resources.delete16Png(), resources.delete32Png(), resources.delete64Png()));
		return bean;
	}
	
	@Managed
	@Override
	public AdaptiveIcon openIcon() {
		AdaptiveIcon bean = create(AdaptiveIcon.T);
		
		bean.setName("Open Icon");
		bean.setRepresentations(set(resources.open16Png(), resources.open32Png(), resources.open64Png()));
		return bean;
	}
	
	@Managed
	@Override
	public AdaptiveIcon infoIcon() {
		AdaptiveIcon bean = create(AdaptiveIcon.T);
		
		bean.setName("Info Icon");
		bean.setRepresentations(set(resources.info16Png(), resources.info32Png(), resources.info64Png()));
		return bean;
	}
	
	@Managed
	@Override
	public AdaptiveIcon modelIcon() {
		AdaptiveIcon bean = create(AdaptiveIcon.T);
		
		bean.setName("Model Icon");
		bean.setRepresentations(set(resources.model16Png(), resources.model32Png(), resources.model64Png()));
		return bean;
	}

}
