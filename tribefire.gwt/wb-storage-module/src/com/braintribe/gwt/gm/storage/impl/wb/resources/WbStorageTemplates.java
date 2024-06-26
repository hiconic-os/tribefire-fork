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
package com.braintribe.gwt.gm.storage.impl.wb.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;

public interface WbStorageTemplates extends XTemplates {

	public static final WbStorageTemplates INSTANCE = GWT.create(WbStorageTemplates.class);

	@XTemplate(source = "wbStorageSaveDialog.html")
	SafeHtml wbStorageSaveDialog(String formDescriptionLabelId, String folderNameLabelId, String folderNameTableCellId, String parentFolderLabelId,
			String parentFolderTableCellId, String parentFolderButtonId, String okButtonTableId, String okButtonImageId, String okButtonLabelId,
			String cancelButtonTableId, String cancelButtonImageId, String cancelButtonLabelId);

	@XTemplate(source = "wbStorageSettingDialog.html")
	SafeHtml wbStorageSettingDialog(String formDescriptionLabelId, String folderNameLabelId, String folderNameTableCellId, String parentFolderLabelId,
			String parentFolderTableCellId, String parentFolderButtonId, String iconLabelId, String iconImageId, String iconChooseButtonId,
			String iconDeleteButtonId, String contextLabelId, String contextTableCellId, String contextChooseButtonId, String contextDeleteButtonId,
			String multiSelectionLabelId, String multiSelectionTableCellId, String forceFormLabelId, String forceFormTableCellId,
			String autoPagingSizeLabelId, String autoPagingSizeTableCellId, String defaultViewLabelId, String defaultViewTableCellId,
			String okButtonTableId, String okButtonImageId, String okButtonLabelId, String cancelButtonTableId, String cancelButtonImageId,
			String cancelButtonLabelId);
}
