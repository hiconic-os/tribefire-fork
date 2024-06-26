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

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface AboutResources extends ClientBundle {
	
	public static final AboutResources INSTANCE = GWT.create(AboutResources.class);
	
	public interface AboutCssResource extends CssResource {
		String about();
		String aboutMain();
		String aboutName();
		String aboutVersionWrapper();
		String aboutVersion();
		String aboutDate();
		String aboutUrlWrapper();
		String aboutUrl();
		String aboutUser();
	}

	@Source("about.gss")
	public AboutCssResource aboutStyles();

}
