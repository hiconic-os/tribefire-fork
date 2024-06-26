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
package com.braintribe.model.access.hibernate.tools;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.findrefs.meta.BasicPropertyReferenceAnalyzer;
import com.braintribe.model.processing.meta.oracle.ModelOracle;

/**
 * @author peter.gazdik
 */
public class HibernatePropertyReferenceAnalyzer extends BasicPropertyReferenceAnalyzer {

	private final HibernateMappingInfoProvider mpip;

	public HibernatePropertyReferenceAnalyzer(ModelOracle modelOracle, HibernateMappingInfoProvider mpip) {
		super(modelOracle, false);

		this.mpip = mpip;
		this.initialize();
	}

	@Override
	protected boolean ignoreEntity(GmEntityType entityType) {
		return !mpip.isEntityMapped(entityType);
	}

	@Override
	protected boolean ignoreProperty(GmEntityType actualOwner, GmProperty property) {
		return !mpip.isPropertyMapped(actualOwner.getTypeSignature(), property.getName());
	}

}
