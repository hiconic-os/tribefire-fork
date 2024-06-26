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
package com.braintribe.model.smartqueryplan.functions;

import java.util.Map;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.query.functions.QueryFunction;

/**
 * This is a constant value which is solely dependent on the signature of the entity, just like discriminator in
 * hibernate.
 */
public interface DiscriminatorValue extends QueryFunction {

	EntityType<DiscriminatorValue> T = EntityTypes.T(DiscriminatorValue.class);

	/** This (entitySignature) is here for debugging purposes only */
	String getEntityPropertySignature();
	void setEntityPropertySignature(String entityPropertySignature);

	int getSignaturePosition();
	void setSignaturePosition(int signaturePosition);

	Map<String, Object> getSignatureToStaticValue();
	void setSignatureToStaticValue(Map<String, Object> signatureToStaticValue);

}
