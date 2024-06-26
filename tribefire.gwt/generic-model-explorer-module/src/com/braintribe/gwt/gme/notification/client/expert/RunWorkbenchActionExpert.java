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

import com.braintribe.cfg.Required;
import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.logging.client.Logger;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.processing.notification.api.CommandExpert;
import com.braintribe.model.uicommand.RunWorkbenchAction;

public class RunWorkbenchActionExpert implements CommandExpert<RunWorkbenchAction> {

	private static Logger logger = new Logger(RunWorkbenchActionExpert.class);
	private ExplorerConstellation explorerConstellation;
	
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}	

	@Override
	public void handleCommand(RunWorkbenchAction command) {
		Object workbenchFolderID = command.getWorkbenchFolderId();
		Folder rootFolder = explorerConstellation.getWorkbench().getRootFolder();
		if (rootFolder == null) {
			logger.info("RunWorkbenchAction - no Root Folder defined in Workbench!");
			return;
		}
		
		Object id = rootFolder.getId();
		Folder folder = null;
		if (id.equals(workbenchFolderID))
			folder = rootFolder;
		else
			folder = findSubFolder(rootFolder, workbenchFolderID);
		
		if (folder == null) {
			logger.info("RunWorkbenchAction - no Folder found for: " + id.toString());
			return;
		}
		
		explorerConstellation.runWorkbenchAction(folder, command);
	}

	private Folder findSubFolder(Folder parentFolder, Object id) {		
		if (parentFolder == null || parentFolder.getSubFolders() == null || parentFolder.getSubFolders().isEmpty())
			return null;

		Folder folder = null;
		for (Folder subFolder : parentFolder.getSubFolders()) {
			Object subFolderId = subFolder.getId();
			if (subFolderId.equals(id)) 
				return subFolder;
			if (subFolder.getSubFolders() != null) {
				folder = findSubFolder(subFolder, id);
				if (folder != null)
					return folder;
			}
		}		
		return folder;		
	}
	
}
