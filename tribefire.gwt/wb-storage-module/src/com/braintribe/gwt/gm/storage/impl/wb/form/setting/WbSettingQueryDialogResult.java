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
package com.braintribe.gwt.gm.storage.impl.wb.form.setting;

import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.generic.pr.criteria.TraversingCriterion;
import com.braintribe.model.resource.Icon;

public class WbSettingQueryDialogResult {
	private Folder parentFolder = null;
	private LocalizedString folderName = null;
	private Icon icon = null;
	private TraversingCriterion context = null;
	private Boolean multiSelection = null;
	private boolean forceForm = false;
	private Integer autoPagingSize;
	private String defaultView;

	public Folder getParentFolder() {
		return this.parentFolder;
	}

	public void setParentFolder(final Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public LocalizedString getFolderName() {
		return this.folderName;
	}

	public void setFolderName(final LocalizedString folderName) {
		this.folderName = folderName;
	}

	public Icon getIcon() {
		return this.icon;
	}

	public void setIcon(final Icon icon) {
		this.icon = icon;
	}

	public TraversingCriterion getContext() {
		return this.context;
	}

	public void setContext(final TraversingCriterion context) {
		this.context = context;
	}

	public boolean getMultiSelection() {
		return this.multiSelection;
	}

	public void setMultiSelection(final boolean multiSelection) {
		this.multiSelection = multiSelection;
	}
	
	public boolean getForceForm() {
		return forceForm;
	}
	
	public void setForceForm(boolean forceForm) {
		this.forceForm = forceForm;
	}
	
	public Integer getAutoPagingSize() {
		return autoPagingSize;
	}
	
	public void setAutoPagingSize(Integer autoPagingSize) {
		this.autoPagingSize = autoPagingSize;
	}

	public String getDefaultView() {
		return this.defaultView;
	}

	public void setDefaultView(final String defaultView) {
		this.defaultView = defaultView;
	}
}
