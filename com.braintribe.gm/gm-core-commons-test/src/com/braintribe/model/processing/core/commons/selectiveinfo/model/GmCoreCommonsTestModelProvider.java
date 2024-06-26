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
package com.braintribe.model.processing.core.commons.selectiveinfo.model;

import java.util.Arrays;
import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.model.tools.MetaModelTools;

/**
 * @author peter.gazdik
 */
public class GmCoreCommonsTestModelProvider {

	// @formatter:off
	private static final List<EntityType<?>> types = Arrays.asList(
			Address.T,
			House.T,
			HouseOwner.T
		);
	// @formatter:on

	public static final GmMetaModel model = MetaModelTools.provideRawModel(types);

}
