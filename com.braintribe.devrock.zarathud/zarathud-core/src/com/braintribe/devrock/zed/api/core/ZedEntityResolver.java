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

import com.braintribe.devrock.zed.api.context.ZedAnalyzerContext;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * 
 * @author pit
 *
 */
public interface ZedEntityResolver {

	/**
	 * @param context - the {@link ZedAnalyzerContext} 
	 * @param desc - the desc as a {@link String}
	 * @return - a {@link ZedEntity}, either fully qualified or shallow
	 */
	public Maybe<ZedEntity> acquireClassResource(ZedAnalyzerContext context, String desc);
	
	/**
	 * @param context - the {@link ZedAnalyzerContext}
	 * @param zed - the {@link ZedEntity} to qualify (analyze etc)
	 */
	void qualify(ZedAnalyzerContext context, ZedEntity zed);
}
