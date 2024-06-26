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
package com.braintribe.gwt.gme.constellation.client.action;

import com.braintribe.gwt.gme.constellation.client.LocalizedText;

public enum GlobalActionsToolBarConfig {

	ACTION_HOME("$new", LocalizedText.INSTANCE.newEntry(), "", "action"),
	ACTION_DUAL_SECTION("$dualSectionButtons", "Dual Section Buttons", "", "action"),
	ACTION_UPLOAD("$upload", LocalizedText.INSTANCE.upload(), "$dualSectionButtons", "action"),
	ACTION_UNDO("$undo", LocalizedText.INSTANCE.undo(), "", "action"),
	ACTION_REDO("$redo", LocalizedText.INSTANCE.redo(), "", "action"),
	ACTION_COMMIT("$commit", LocalizedText.INSTANCE.commit(), "", "action"),
	;
	
	GlobalActionsToolBarConfig(String name, String displayName, String parentFolder, String kind) {
		this.name = name;
		this.displayName = displayName;
		this.parentFolder = parentFolder;
		this.kind = kind;
	}
	
	private String name;
	private String displayName;
	private String parentFolder;
	private String kind;
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getParentFolder() {
		return parentFolder;
	}

	public String getKind() {
		return kind;
	}
}
