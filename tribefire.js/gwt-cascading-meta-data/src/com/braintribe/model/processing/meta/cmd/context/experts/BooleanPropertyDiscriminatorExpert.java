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
package com.braintribe.model.processing.meta.cmd.context.experts;

import com.braintribe.model.meta.selector.BooleanPropertyDiscriminator;
import com.braintribe.model.processing.meta.cmd.CascadingMetaDataException;
import com.braintribe.model.processing.meta.cmd.context.SelectorContext;

@SuppressWarnings("unusable-by-js")
public class BooleanPropertyDiscriminatorExpert extends SimplePropertyDiscriminatorExpert<BooleanPropertyDiscriminator, Boolean> {

	public BooleanPropertyDiscriminatorExpert() {
		super(Boolean.class);
	}

	@Override
	public boolean matches(BooleanPropertyDiscriminator selector, SelectorContext context) throws CascadingMetaDataException {
		Boolean actualValue = getPropertyCasted(selector, context);

		if (actualValue != null) {
			boolean discriminatorValue = selector.getDiscriminatorValue();
			return discriminatorValue == actualValue.booleanValue();
		}

		return false;
	}

}
