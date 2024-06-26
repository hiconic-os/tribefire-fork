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
package com.braintribe.gwt.gme.notification.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;

public class NotificationAction {

	private String id, text;
	private ImageResource icon;
	private String className;

	public NotificationAction(String id, String className, ImageResource icon, String text) {
		this.id = id;
		this.icon = icon;
		this.text = text;
		this.className = className;
	}

	public NotificationAction(String id, ImageResource icon, String text) {
		this.id = id;
		this.icon = icon;
		this.text = text;
		this.className = null;
	}
	
	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getClassName() {
		return className;
	}
	
	public SafeStyles getIconStyle() {
		return icon == null ? new SafeStylesBuilder().toSafeStyles() : new SafeStylesBuilder()//
				.backgroundImage(icon.getSafeUri()).width(icon.getWidth(), Unit.PX).height(icon.getHeight(), Unit.PX)//
				.toSafeStyles();
	}

}
