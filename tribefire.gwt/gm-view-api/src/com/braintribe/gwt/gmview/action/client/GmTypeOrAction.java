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
package com.braintribe.gwt.gmview.action.client;

import com.braintribe.model.meta.GmType;
import com.braintribe.model.workbench.WorkbenchAction;

public class GmTypeOrAction {
	
	private GmType type;
	private WorkbenchAction action;
	
	public GmTypeOrAction(GmType type, WorkbenchAction action) {
		this.type = type;
		this.action = action;
	}

	public GmType getType() {
		return type;
	}

	public void setType(GmType type) {
		this.type = type;
	}

	public WorkbenchAction getAction() {
		return action;
	}

	public void setAction(WorkbenchAction action) {
		this.action = action;
	}

}
