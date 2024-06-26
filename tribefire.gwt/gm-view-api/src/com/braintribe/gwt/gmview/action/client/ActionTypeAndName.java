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

import com.braintribe.gm.model.uiaction.ActionFolderContent;
import com.braintribe.gwt.gmview.client.js.interop.InteropConstants;
import com.braintribe.model.generic.reflection.EntityType;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

@JsType (namespace = InteropConstants.VIEW_NAMESPACE)
public class ActionTypeAndName {
	
	private EntityType<? extends ActionFolderContent> denotationType;
	private String actionName;

	public ActionTypeAndName(EntityType<? extends ActionFolderContent> denotationType, String actionName) {
		this.denotationType = denotationType;
		this.actionName = actionName;
	}

	@JsIgnore
	public ActionTypeAndName() {
		this(null, null);
	}
	
	@JsIgnore
	public ActionTypeAndName(String actionName) {
		this(null, actionName);
	}
	
	@JsIgnore
	public ActionTypeAndName(ActionTypeAndName other) {
		this(other.getDenotationType(), other.getActionName());
	}

	public EntityType<? extends ActionFolderContent> getDenotationType() {
		return denotationType;
	}
	
	public void setDenotationType(EntityType<? extends ActionFolderContent> denotationType) {
		this.denotationType = denotationType;
	}
	
	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionName == null) ? 0 : actionName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionTypeAndName other = (ActionTypeAndName) obj;
		
		if (actionName != null && other.actionName != null)
			return actionName.equals(other.actionName);
		
		if (denotationType != null && other.denotationType != null)
			return denotationType.equals(other.denotationType);
		
		if (actionName == null) {
			if (other.actionName != null)
				return false;
		} else if (!actionName.equals(other.actionName))
			return false;
		
		if (denotationType == null) {
			if (other.denotationType != null)
				return false;
		} else if (!denotationType.equals(other.denotationType))
			return false;
		
		return true;
	}
	
}
