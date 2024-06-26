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
package com.braintribe.gwt.resourceuploadui.client;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.KnownProperties;
import com.braintribe.gwt.gme.constellation.client.DualSectionButton;
import com.google.gwt.resources.client.ImageResource;

public class ShowUploadDialogsAction extends DualSectionButton {
	
	/*
	private ShowWindowAction showResourceUpload;
	private ShowDocumentUploadDialogAction showDocumentUpload;
	
	public void setShowResourceUpload(ShowWindowAction showResourceUpload) {
		this.showResourceUpload = showResourceUpload;
	}
	
	public void setShowDocumentUpload(ShowDocumentUploadDialogAction showDocumentUpload) {
		this.showDocumentUpload = showDocumentUpload;
		
		showDocumentUpload.addPropertyListener((source, property) -> {
			if (KnownProperties.PROPERTY_HIDDEN.equals(property)) {
				setDualSectionVisible(!showDocumentUpload.getHidden());
			}
		});
	}
	*/
	
	@Override
	public void setPrimaryAction(Action primaryAction) {
		super.setPrimaryAction(primaryAction);
	}
	
	@Override
	public void setSecondaryAction(Action secondaryAction) {
		super.setSecondaryAction(secondaryAction);				
		secondaryAction.addPropertyListener((source, property) -> {
			if (KnownProperties.PROPERTY_HIDDEN.equals(property)) 
				setDualSectionVisible(!secondaryAction.getHidden());
		});		
	}	
	
	public ShowUploadDialogsAction(ImageResource icon, ImageResource dualIcon, String text, String qTip) {
		super(icon, dualIcon, text, qTip);
		/*
		addDualSectionButtonListener(new DualSectionButtonListener() {
			@Override
			public void onUpperSectionClicked(ClickEvent event) {
				showResourceUpload.perform(null);
			}
			
			@Override
			public void onLowerSectionClicked(ClickEvent event) {
				showDocumentUpload.perform(null);
			}
		});
		*/
	}
	

}
