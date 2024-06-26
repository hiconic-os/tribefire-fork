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
import com.braintribe.model.resource.source.TransientSource;
import com.braintribe.model.uicommand.PrintResource;
import com.google.gwt.user.client.Window;

public class PrintResourceExpert implements CommandExpert<PrintResource> {
	
	private PersistenceGmSession session;
	
	public void setSession(PersistenceGmSession session) {
		this.session = session;
	}

	@Override
	public void handleCommand(PrintResource command) {
		if (command.getResources() == null || command.getResources().isEmpty())
			return;
		
		List<Resource> resources =  command.getResources();
		
		resources.forEach(r -> {
			String extension = (r.getMimeType() != null && r.getMimeType().contains("/")) ? r.getMimeType().split("/")[1] : "";
			String fileName = r.getName() != null ? r.getName() : r.getId().toString() + "." + extension;
			String url = session.resources().url(r).download(true).fileName(fileName).asString();
			if (r.getResourceSource() instanceof TransientSource)
				openAndPrint(url);					
			else
				System.err.println("not supported yet");
		});			
	}
	
	private native void print(Window w) /*-{
		w.print();
	}-*/;

	private native Window openAndPrint(String url) /*-{
		var oReq = new XMLHttpRequest();
        oReq.open("GET", url,  true);
        oReq.responseType = "blob";
        oReq.onload = function(oEvent) {
            var gwt_print_dialog_frame_id = "gwt-print-dialog-frame";

            el = document.getElementById(gwt_print_dialog_frame_id);
            if(el)
                el.parentNode.removeChild(el);

            var blob = oReq.response;		  
            var newBlob = new Blob([blob], {type: "application/pdf"});
            var data = window.URL.createObjectURL(newBlob);

            var a = document.createElement("iframe");
            a.setAttribute("id", gwt_print_dialog_frame_id);			
            setTimeout(function(){	
                a.contentWindow.focus();
                a.contentWindow.print();				
            },1000);					
            document.body.appendChild(a);			
            a.src = data;
        };
        oReq.send();		
	}-*/;
	
}
