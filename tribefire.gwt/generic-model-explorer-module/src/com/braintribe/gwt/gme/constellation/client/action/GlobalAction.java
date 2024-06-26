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

import com.braintribe.gwt.action.client.Action;
import com.google.gwt.resources.client.ImageResource;

public class GlobalAction {
	private Action action;
	private ImageResource icon;
	private ImageResource hoverIcon;
	private String description;
	private String knownName;
	
	public GlobalAction(String knownName) {
		this.knownName = knownName;
	}
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public ImageResource getIcon() {
		return icon;
	}
	public void setIcon(ImageResource icon) {
		this.icon = icon;
	}
	public ImageResource getHoverIcon() {
		return hoverIcon;
	}
	public void setHoverIcon(ImageResource hoverIcon) {
		this.hoverIcon = hoverIcon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKnownName() {
		return knownName;
	}
	
	public void setKnownName(String knownName) {
		this.knownName = knownName;
	}

}
