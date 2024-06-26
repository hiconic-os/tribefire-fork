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
package com.braintribe.model.accessdeployment.smart.meta;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Maps all kinds of model elements 1:1 to the delegate. For entities this means we map a smart model type to itself,
 * for properties it means the name and type are the same in smart and delegate models and no value conversion is done.
 * For enums it's analogical.
 * 
 * @author peter.gazdik
 */
public interface AsIs extends EntityAssignment, DirectPropertyAssignment, EnumConstantAssignment {

	EntityType<AsIs> T = EntityTypes.T(AsIs.class);

}
