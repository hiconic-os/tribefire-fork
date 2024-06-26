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
package com.braintribe.model.generic.validation.log;

import java.util.ArrayList;
import java.util.HashMap;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.generic.validation.ValidatorResult;

public class ValidationLog extends HashMap<GenericEntity, ArrayList<ValidatorResult>> {
	private static final long serialVersionUID = -3250735922573765286L;
	
	private LocalizedString name;
	private LocalizedString description;
	
	public void setName(LocalizedString name) {
		this.name = name;
	}
	
	public LocalizedString getName() {
		return name;
	}
	
	public void setDescription(LocalizedString description) {
		this.description = description;
	}
	
	public LocalizedString getDescription() {
		return description;
	}

}
