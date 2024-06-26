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

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;

/**
 * 
 */
public class CorrelationInfo {

	private final GmEntityType delegateType1;
	private final GmProperty correlationProperty1;

	private final GmEntityType delegateType2;
	private final GmProperty correlationProperty2;
	private final GmProperty smartProperty;

	public CorrelationInfo(GmProperty smartProperty, GmProperty p1, GmProperty p2) {
		this.smartProperty = smartProperty;
		this.delegateType1 = p1.getDeclaringType();
		this.correlationProperty1 = p1;
		this.delegateType2 = p2.getDeclaringType();
		this.correlationProperty2 = p2;
	}

	public GmProperty getSmartProperty() {
		return smartProperty;
	}

	public GmProperty getCorrelationProperty(GmEntityType delegateType) {
		if (delegateType1 == delegateType)
			return correlationProperty1;

		if (delegateType2 == delegateType)
			return correlationProperty2;

		return null;
	}
}
