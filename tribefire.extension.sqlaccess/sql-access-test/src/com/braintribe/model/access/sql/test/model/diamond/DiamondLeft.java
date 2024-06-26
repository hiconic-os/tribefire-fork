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
package com.braintribe.model.access.sql.test.model.diamond;

import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author peter.gazdik
 */
public interface DiamondLeft extends DiamondBase {

	EntityType<DiamondLeft> T = EntityTypes.T(DiamondLeft.class);

	/**
	 * This property is declared in the {@link DiamondRight} too. This means that from {@link DiamondTail} point of
	 * view, this property comes from two different super-types, without a common base. The question is now, how do we
	 * represent this.
	 * 
	 * If we simply have a ManyToMany table for declaration of such property, we would have one table for DiamondLeft
	 * one for DiamondRight. This would mean that instances of DiamondTail would need to be in both tables.
	 */
	Set<DiamondFriend> getDiamondDuplicateSet();
	void setDiamondDuplicateSet(Set<DiamondFriend> diamondDuplicateSet);

}
