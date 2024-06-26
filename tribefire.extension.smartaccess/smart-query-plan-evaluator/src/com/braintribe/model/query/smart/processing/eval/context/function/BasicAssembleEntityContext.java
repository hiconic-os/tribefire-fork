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
package com.braintribe.model.query.smart.processing.eval.context.function;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;

import com.braintribe.model.processing.smartquery.eval.api.AssembleEntityContext;
import com.braintribe.model.smartqueryplan.functions.AssembleEntity;
import com.braintribe.model.smartqueryplan.functions.PropertyMapping;
import com.braintribe.model.smartqueryplan.functions.PropertyMappingNode;

/**
 * 
 */
public class BasicAssembleEntityContext implements AssembleEntityContext {

	private final Map<String, PropertyMappingNode> signatureToPropertyMappingNode;
	private final Map<String, PropertyMapping> pNameToMapping = newMap();

	public BasicAssembleEntityContext(AssembleEntity aeFunction) {
		this.signatureToPropertyMappingNode = aeFunction.getSignatureToPropertyMappingNode();
	}

	@Override
	public PropertyMappingNode getPropertyMappingNode(String entitySignature) {
		return signatureToPropertyMappingNode.get(entitySignature);
	}

	@Override
	public PropertyMapping getPropertyMapping(String propertyName) {
		return pNameToMapping.get(propertyName);
	}

}
