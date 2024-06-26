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
package com.braintribe.model.processing.smartquery.eval.api.function;

import com.braintribe.model.query.Source;

/**
 * 
 * @author peter.gazdik
 */
@Deprecated
public class PropertySelectionOperand  /*extends ClassEntity*/ {

	private Source source;
	private String propertyName;

	private PropertySelectionOperand() {
		
	}
	
	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public int hashCode() {
		return 31 * propertyName.hashCode() + source.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		PropertySelectionOperand other = (PropertySelectionOperand) obj;

		return source == other.source && propertyName.equals(other.propertyName);
	}

}
