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

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.query.Operand;
import com.braintribe.model.query.Source;

/**
 * 
 * @author peter.gazdik
 */
public interface SignatureSelectionOperand extends Operand {
	
	final EntityType<SignatureSelectionOperand> T = EntityTypes.T(SignatureSelectionOperand.class);

	// @formatter:off
	Source getSource();
	void setSource(Source source);
	// @formatter:on

	@Override
	int hashCode();

	@Override
	boolean equals(Object other);

	public static class DefaultMethods {
		public static int hashCode(SignatureSelectionOperand me) {
			return me.getSource().hashCode();
		}

		public static boolean equals(SignatureSelectionOperand me, Object obj) {
			if (me == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			return me.getSource() == ((SignatureSelectionOperand) obj).getSource();
		}
	}

}
