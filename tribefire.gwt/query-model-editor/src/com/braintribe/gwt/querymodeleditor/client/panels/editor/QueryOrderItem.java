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
package com.braintribe.gwt.querymodeleditor.client.panels.editor;

import com.braintribe.model.query.OrderingDirection;
import com.google.gwt.dom.client.ImageElement;

public class QueryOrderItem {
    private String propertyName;
    private String displayName;
    private String orderButtonId;
    private OrderingDirection orderingDirection;
    private ImageElement buttonImageElement;
     
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getOrderButtonId() {
		return orderButtonId;
	}
	public void setOrderButtonId(String orderButtonId) {
		this.orderButtonId = orderButtonId;
	}
	public OrderingDirection getOrderingDirection() {
		return orderingDirection;
	}
	public void setOrderingDirection(OrderingDirection orderingDirection) {
		this.orderingDirection = orderingDirection;
	}
	public ImageElement getButtonImageElement() {
		return buttonImageElement;
	}
	public void setButtonImageElement(ImageElement buttonImageElement) {
		this.buttonImageElement = buttonImageElement;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
     
}
