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
package com.braintribe.model.processing.query.smart.test.model.accessA.discriminator;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessA.StandardIdentifiableA;
import com.braintribe.model.processing.query.smart.test.model.smart.discriminator.SmartDiscriminatorBase;

/**
 * Mapped from hierarchy rooted at {@link SmartDiscriminatorBase} - when single discriminator case.
 * 
 * Mapped from hierarchy rooted at SmartCompositeDiscriminatorBase?? - when composite discriminator case.
 * 
 * @author peter.gazdik
 */

public interface DiscriminatorEntityA extends StandardIdentifiableA {

	final EntityType<DiscriminatorEntityA> T = EntityTypes.T(DiscriminatorEntityA.class);

	// @formatter:off
	/** This property is mapped by everyone */
	String getName();
	void setName(String value);

	/** This property is mapped only by type1 */
	String getType1Name();
	void setType1Name(String value);
	
	/** This property is mapped only by type2 */
	String getType2Name();
	void setType2Name(String value);
	
	String getDiscriminator();
	void setDiscriminator(String value);

	/** This */
	String getDiscriminator2();
	void setDiscriminator2(String value);
	// @formatter:on

}
