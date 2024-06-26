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
package com.braintribe.gwt.querymodeleditor.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;

public interface QueryModelEditorTemplates extends XTemplates {

	public static final QueryModelEditorTemplates INSTANCE = GWT.create(QueryModelEditorTemplates.class);

	@XTemplate(source = "qmePanel.html")
	SafeHtml qmePanel(TemplateConfigurationBean bean);

	@XTemplate(source = "qmeAdvancedPanel.html")
	SafeHtml qmeAdvancedPanel(TemplateConfigurationBean bean);

	@XTemplate(source = "pagenationControl.html")
	SafeHtml pagenationControl(TemplateConfigurationBean bean);

	@XTemplate(source = "dropDownControl.html")
	SafeHtml dropDownControl(TemplateConfigurationBean bean);

	@XTemplate(source = "qacDialogPanel.html")
	SafeHtml qacDialogPanel(TemplateConfigurationBean bean);
}
