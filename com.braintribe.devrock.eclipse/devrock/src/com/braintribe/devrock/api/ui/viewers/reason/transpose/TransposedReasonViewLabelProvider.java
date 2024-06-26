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
package com.braintribe.devrock.api.ui.viewers.reason.transpose;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.api.ui.stylers.Stylers;
import com.braintribe.devrock.api.ui.viewers.StyledStringReasonStyler;
import com.braintribe.devrock.eclipse.model.resolution.nodes.ReasonNode;

public class TransposedReasonViewLabelProvider extends CellLabelProvider implements IStyledLabelProvider {
	
	public static final String KEY_TYPE = "grp";
	public static final String KEY_MESSAGE = "path";

	private UiSupport uiSupport;
	private String propertyName;
	private String uiSupportStylersKey;
	private Styler typeStyler;

	
	private StyledStringReasonStyler reasonStyler = new StyledStringReasonStyler();
		
	@Configurable @Required
	public void setUiSupport(UiSupport uiSupport) {
		this.uiSupport = uiSupport;
		setupUiSupport();
	}
	@Configurable
	public void setUiSupportStylersKey(String uiSupportStylersKey) {
		this.uiSupportStylersKey = uiSupportStylersKey;
	}
		
	private void setupUiSupport() {	
		Stylers stylers = uiSupport.stylers(uiSupportStylersKey);
		reasonStyler.setUiSupport(uiSupport);
		stylers.addStandardStylers();
		typeStyler = stylers.standardStyler(Stylers.KEY_BOLD);		
	}

	public TransposedReasonViewLabelProvider(String propertyName, UiSupport uiSupport, String uiSupportStylersKey) {
		this.uiSupport = uiSupport;
		this.propertyName = propertyName;		
		this.uiSupportStylersKey = uiSupportStylersKey;
		
		setupUiSupport();
	}

	
	

	@Override
	public StyledString getStyledText(Object object) {
		ReasonNode r = (ReasonNode) object;
			
		if (propertyName.equals(KEY_TYPE) && r.getType() != null) {		
			return new StyledString( r.getType(), typeStyler);	
		}
		if (propertyName.equals( KEY_MESSAGE)) {
			//StyledString styledString = SimpleReasonStyler.createStyledStringFromReason( r.getBackingReason(), typeStyler, messageStyler);
			StyledString styledString = reasonStyler.process( r.getBackingReason()); 
			return styledString;
		}
	
		return new StyledString("");
	}
		
	
	
	@Override
	public String getToolTipText(Object element) {
		ReasonNode reason = (ReasonNode) element;
		
		if (propertyName.equals( KEY_TYPE) && reason.getType() != null)
			return "Type of the Reason is " + reason.getType();
		
		if (propertyName.equals( KEY_MESSAGE))
			return "Message of the Reason " + reason.getText();
		
		return super.getToolTipText(element);
	}


	@Override
	public void update(ViewerCell arg0) {
		System.out.println();
		
	}

	@Override
	public void dispose() {				
	}

	@Override
	public Image getImage(Object arg0) {
		
		return null;
	}
	
	

}
