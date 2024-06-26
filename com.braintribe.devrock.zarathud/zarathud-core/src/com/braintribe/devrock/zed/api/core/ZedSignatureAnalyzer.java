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
package com.braintribe.devrock.zed.api.core;

import com.braintribe.devrock.zed.api.context.ZedAnalyzerProcessContext;
import com.braintribe.devrock.zed.scan.asm.MethodSignature;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.zarathud.model.data.TypeReferenceEntity;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * @author pit
 *
 */
public interface ZedSignatureAnalyzer {
	/**
	 * @param context - the {@link ZedAnalyzerProcessContext}
	 * @param signature - the signature of the method
	 * @return - a modelled {@link MethodSignature}
	 */
	MethodSignature extractMethodSignature( ZedAnalyzerProcessContext context, String signature);
	
	/**
	 * @param context - the {@link ZedAnalyzerProcessContext}
	 * @param desc - the desc 
	 * @param expectation - the expected {@link EntityType} 
	 * @return - the {@link TypeReferenceEntity} 
	 */
	
	TypeReferenceEntity analyzeReference( ZedAnalyzerProcessContext context, String desc, EntityType<? extends ZedEntity> expectation);
	/**
	 * @param context - the {@link ZedAnalyzerProcessContext}
	 * @param desc - the type desc
	 * @param expectation - the expected {@link EntityType}
	 * @return - the {@link TypeReferenceEntity}
	 */
	TypeReferenceEntity processType(ZedAnalyzerProcessContext context, String desc, EntityType<? extends ZedEntity> expectation);
}
