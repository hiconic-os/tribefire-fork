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
package com.braintribe.gwt.ioc.gme.client.expert;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.linker.CrossSiteIframeLinker;
 
public class HttpsCrossSiteIFrameLinker extends CrossSiteIframeLinker {
	@Override
	protected String getJsDevModeRedirectHookPermitted(LinkerContext context) {
		return "$wnd.location.protocol == \"http:\" || $wnd.location.protocol == \"file:\" "
				+ "|| $wnd.location.protocol == \"https:\"";
	}
}