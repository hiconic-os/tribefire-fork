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
package com.braintribe.wire.test.basic.space;

import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.context.WireContextConfiguration;
import com.braintribe.wire.api.space.WireSpace;
import com.braintribe.wire.example.BeanOriginManager;

@Managed
public class MetaSpace implements WireSpace {
	@Override
	public void onLoaded(WireContextConfiguration configuration) {
		configuration.addLifecycleListener(beanOriginManager());
	}
	
	@Managed
	public BeanOriginManager beanOriginManager() {
		BeanOriginManager bean = new BeanOriginManager();
		return bean;
	}

}