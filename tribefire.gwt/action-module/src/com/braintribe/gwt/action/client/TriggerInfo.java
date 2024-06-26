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
package com.braintribe.gwt.action.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * This bean can have the properties received when triggering a certain action.
 * @author michel.docouto
 *
 */
public class TriggerInfo extends ActionPropertyHolder implements TriggerKnownProperties {
	
	private Widget widget;
	
	public void setWidget(Widget widget) {
		this.widget = widget;
	}
	
	public Widget getWidget() {
		return widget;
	}
	
	public Integer getAbsolutLeft() {
		return (this.widget == null ? null : this.widget.getAbsoluteLeft());
	}
	
	public Integer getAbsolutTop() {
		return (this.widget == null ? null : this.widget.getAbsoluteTop());
	}
	
	public Integer getOffsetHeight() {
		return (this.widget == null ? null : this.widget.getOffsetHeight());
	}
	
	public Integer getOffsetWidth() {
		return (this.widget == null ? null : this.widget.getOffsetWidth());
	}

}
