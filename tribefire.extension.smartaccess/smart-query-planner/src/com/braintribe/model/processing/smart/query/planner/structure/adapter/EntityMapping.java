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
package com.braintribe.model.processing.smart.query.planner.structure.adapter;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmEntityType;

/**
 * 
 */
public class EntityMapping {

	private final GmEntityType smartEntityType;
	private final GmEntityType delegateEntityType;
	private final IncrementalAccess access;
	private final EmUseCase useCase;
	private final DiscriminatedHierarchy discriminatedHierarchy;

	public EntityMapping(GmEntityType smartEntityType, GmEntityType delegateEntityType, DiscriminatedHierarchy discriminatedHierarchy,
			IncrementalAccess access, EmUseCase useCase) {

		this.smartEntityType = smartEntityType;
		this.delegateEntityType = delegateEntityType;
		this.discriminatedHierarchy = discriminatedHierarchy;
		this.access = access;
		this.useCase = useCase;
	}

	public GmEntityType getSmartEntityType() {
		return smartEntityType;
	}

	public GmEntityType getDelegateEntityType() {
		return delegateEntityType;
	}

	public IncrementalAccess getAccess() {
		return access;
	}

	public EmUseCase getUseCase() {
		return useCase;
	}

	public boolean isPolymorphicAssignment() {
		return discriminatedHierarchy != null;
	}
	
	public DiscriminatedHierarchy getDiscriminatedHierarchy() {
		return discriminatedHierarchy;
	}

	// ########################################
	// ## . . . . . . . ToString . . . . . . ##
	// ########################################

	/**
	 * Prints the mapping in the form: "${smartSignature} -> ${mappedDelegateSignature} (${delegateAccess.externalId})"
	 */
	@Override
	public String toString() {
		return smartEntityType.getTypeSignature() + " -> " + delegateEntityType.getTypeSignature() + " (" + access.getExternalId() + ")" +
				useCaseStr();
	}

	private String useCaseStr() {
		return useCase == null ? "" : " [" + useCase + "]";
	}

}
