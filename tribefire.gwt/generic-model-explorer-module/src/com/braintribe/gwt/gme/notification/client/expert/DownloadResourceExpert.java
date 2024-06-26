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
package com.braintribe.gwt.gme.notification.client.expert;

import java.util.List;

import com.braintribe.model.processing.notification.api.CommandExpert;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.uicommand.DownloadResource;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.RootPanel;

public class DownloadResourceExpert implements CommandExpert<DownloadResource> {
	
	private PersistenceGmSession session;
	
	public void setSession(PersistenceGmSession session) {
		this.session = session;
	}

	@Override
	public void handleCommand(DownloadResource command) {
		if (command.getResources() == null || command.getResources().isEmpty())
			return;
		
		List<Resource> resources =  command.getResources();
		
		resources.forEach(r -> {
			String extension = (r.getMimeType() != null && r.getMimeType().contains("/")) ? r.getMimeType().split("/")[1] : "";
			String fileName = r.getName() != null ? r.getName() : r.getId().toString() + "." + extension;
			String url = session.resources().url(r).download(true).asString();
			AnchorElement a = Document.get().createAnchorElement();
			a.setHref(url);
			a.setAttribute("download", fileName);
			RootPanel.get().getElement().appendChild(a);
			click(a);
			RootPanel.get().getElement().removeChild(a);
			/*
			if (r.getResourceSource() instanceof TransientSource) {					
				AnchorElement a = Document.get().createAnchorElement();
				a.setHref(url);
				a.setAttribute("download", fileName);
				RootPanel.get().getElement().appendChild(a);
				click(a);
				RootPanel.get().getElement().removeChild(a);			
			} else {
				Window.open(url, "_new", "");
//				Location.assign(url);
			}
			*/				
		});			
	}
	
	public static native void click(AnchorElement a) /*-{
    	a.click();
	}-*/;
	
}
