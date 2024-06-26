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
package com.braintribe.gwt.gmview.client;

import java.util.List;

public interface HasAddtionalWidgets extends GmContentView{
	
	public void configureAdditionalWidgets(List<TabbedWidgetContext> additionalWidgets);
	public List<TabbedWidgetContext> getTabbedWidgetContexts();
	
	default String getDefaultActiveTabbedWidget() {
		return null;
	}
	
	/**
	 * @param key - the tab key
	 */
	default void setActiveTabbedWidget(String key) {
		//NOP
	}
	
	default HasAddtionalWidgets getLink() {
		return null;
	}
	
	/**
	 * @param haw - the link
	 */
	default void setLink(HasAddtionalWidgets haw) {
		//NOP
	}

}
