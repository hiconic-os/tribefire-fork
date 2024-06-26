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
package com.braintribe.model.processing.smart.query.planner.structure;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.meta.oracle.ModelOracle;

/**
 * Utility class for various methods operating on given smart meta-model, or resolving meta-model information from given meta-model related
 * data (without any other context). All the methods in this class work with skeletal model information only (no meta-data) and therefore
 * all the results can be cached and a single instance of this class can be used in the smart access.
 * <p>
 * Note that this means all the components of this class MUST BE THREAD-SAFE and as highly concurrent as possible.
 * <p>
 * There is another model-related utility class with extended API which is instantiated per task (query/applyManipulation) -
 * {@link ModelExpert}.
 * 
 */
public class StaticModelExpert {

	protected final ModelOracle smartModelOracle;
	protected final AccessModelExpert accessModelExpert;
	protected final ModelHierarchyExpert smartHierarchyExpert;

	public StaticModelExpert(ModelOracle smartModelOracle) {
		this.smartModelOracle = smartModelOracle;
		this.accessModelExpert = new AccessModelExpert();
		this.smartHierarchyExpert = new ModelHierarchyExpert(smartModelOracle);
	}

	public ModelOracle getSmartModelOracle() {
		return smartModelOracle;
	}

	public GmEntityType resolveSmartEntityType(String signature) {
		return smartModelOracle.getEntityTypeOracle(signature).asGmEntityType();
	}

	public boolean containsEntityType(GmEntityType entityType, IncrementalAccess access) {
		return accessModelExpert.containsEntityType(entityType.getTypeSignature(), access);
	}

	public GmEntityType resolveEntityType(String delegateSignature, IncrementalAccess access) {
		return accessModelExpert.resolveEntityType(delegateSignature, access);
	}

	public boolean isSmartType(GmEntityType gmType) {
		return smartModelOracle.findEntityTypeOracle(gmType) != null;
	}

	public Map<String, GmProperty> getAllProperties(GmEntityType gmEntityType) {
		return accessModelExpert.getAllProperties(gmEntityType);
	}

	public Set<GmEntityType> getDirectSmartSubTypes(GmEntityType smartEntityType) {
		return smartHierarchyExpert.getDirectSubTypes(smartEntityType);
	}

	public boolean isFirstAssignableFromSecond(GmEntityType et1, GmEntityType et2) {
		return smartHierarchyExpert.isFirstAssignableFromSecond(et1, et2);
	}

	/** @see ModelHierarchyExpert#resolveHierarchyRootedAt(GmEntityType) */
	public EntityHierarchyNode resolveHierarchyRootedAt(GmEntityType smartType) {
		return smartHierarchyExpert.resolveHierarchyRootedAt(smartType);
	}
}
