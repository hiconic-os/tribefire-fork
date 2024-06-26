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
package com.braintribe.gwt.gme.cssresources.client;

import com.google.gwt.xhr.client.XMLHttpRequest;

public class TitleCssLoader {
	private String url = "../tribefire-services/publicResource/dynamic/gme-title";	//Default value
	private Boolean titleIsSet = false;
	
	public void loadTitle (String accessId) {
		setSetting(accessId, null);
	}

	public void loadTitle (String accessId, String applicationId) {
		setSetting(accessId, applicationId);
	}
	
	public void setTitleUrl(String url) {
        this.url = url;		
	}
			
	public boolean isTitleSet() {
		return titleIsSet;
	}
	
	private String addParameterSeparator(Boolean isFirst) {
		return isFirst ? "?" : "&";
	} 
	
	private void setSetting(String accessId, String applicationId) {				
		String urlString = this.url;
		Boolean isFirstParameter = true;
	    if (accessId != null) {
		   urlString = urlString + addParameterSeparator(isFirstParameter) + "accessId=" + accessId;
		   isFirstParameter = false;
		}
	    if (applicationId != null) { 
		   urlString = urlString + addParameterSeparator(isFirstParameter) +"applicationId=" + applicationId;
		   isFirstParameter = false;
	    }
	    
		//TEST
		//urlString = "../tribefire-services/publicResource/dynamic/favicon.ico";

		if (urlString == null || urlString.isEmpty())
			return;
		
		XMLHttpRequest request = XMLHttpRequest.create();
		request.open("get", urlString);
		request.setRequestHeader("Accept", "gm/jse");
		
		request.setOnReadyStateChange(xhr -> {
			if (xhr.getReadyState() != XMLHttpRequest.DONE || xhr.getStatus() != 200)
				return;
			
			String title = xhr.getResponseText();
			if (title != null && !title.isEmpty()) {
				CssLinkLoader.setDocumentTitle(title);
				titleIsSet = true;
			}
		});			
		request.send();			
	}	
}
