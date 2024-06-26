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
package com.braintribe.gwt.gm.storage.impl.wb.form.save;

import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.i18n.LocalizedString;

public class WbSaveQueryDialogResult {
	private Folder parentFolder = null;
	private LocalizedString folderName = null;

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
}
