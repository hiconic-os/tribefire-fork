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
package com.braintribe.gwt.ioc.gme.client.action;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gxt.gxtresources.components.client.BtDialog;

import edu.ycp.cs.dh.acegwt.client.ace.AceEditor;
import edu.ycp.cs.dh.acegwt.client.ace.AceEditorMode;
import edu.ycp.cs.dh.acegwt.client.ace.AceEditorTheme;

public class ShowAceEditorAction extends Action{
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		BtDialog dialog = new BtDialog();
		dialog.setBorders(false);
		dialog.setHeaderVisible(false);
		
		AceEditor aceEditor = new AceEditor();
		
		aceEditor.setWidth("600px");
		aceEditor.setHeight("400px");
		
		aceEditor.startEditor();
		aceEditor.setMode(AceEditorMode.JAVA);
		aceEditor.setTheme(AceEditorTheme.ECLIPSE);	
		
		dialog.add(aceEditor);
		dialog.show();
	}

}
