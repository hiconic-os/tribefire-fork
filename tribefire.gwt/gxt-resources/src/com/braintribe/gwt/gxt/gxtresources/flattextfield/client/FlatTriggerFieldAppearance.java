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
package com.braintribe.gwt.gxt.gxtresources.flattextfield.client;

import com.google.gwt.core.shared.GWT;
import com.sencha.gxt.theme.base.client.field.TriggerFieldDefaultAppearance;

public class FlatTriggerFieldAppearance extends TriggerFieldDefaultAppearance {
	
	public interface FlatTriggerFieldResources extends TriggerFieldResources {

		@Override
		@Source({ "com/sencha/gxt/theme/base/client/field/ValueBaseField.gss", "com/sencha/gxt/theme/base/client/field/TextField.gss", "FlatTextField.gss",
				"com/sencha/gxt/theme/base/client/field/TriggerField.gss" })
		TriggerFieldStyle css();
	}
	
	public FlatTriggerFieldAppearance() {
		super(GWT.<FlatTriggerFieldResources>create(FlatTriggerFieldResources.class));
	}

}
