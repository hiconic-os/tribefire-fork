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
package com.braintribe.gm.service.access.wire.common.space;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.braintribe.gm.service.access.api.AccessProcessingConfiguration;
import com.braintribe.gm.service.access.wire.common.contract.AccessProcessingConfigurationContract;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

@Managed
public class AccessProcessingConfigurationSpace implements AccessProcessingConfigurationContract {
	
	@Import
	private CommonAccessProcessingSpace commonAccessProcessing;
	
	@Override
	public void registerAccessConfigurer(Consumer<AccessProcessingConfiguration> configurer) {
		accessConfigurers().add(configurer);
	}
	
	@Managed
	public List<Consumer<AccessProcessingConfiguration>> accessConfigurers() {
		return new ArrayList<>();
	}
	
}
