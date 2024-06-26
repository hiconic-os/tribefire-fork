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
package tribefire.cortex.assets.default_wb_initializer.wire.contract;

import com.braintribe.model.folder.Folder;
import com.braintribe.model.workbench.WorkbenchConfiguration;
import com.braintribe.model.workbench.WorkbenchPerspective;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;

@InstanceLookup
public interface DefaultWbContract extends WireSpace {
	
	WorkbenchConfiguration defaultWorkbenchConfiguration();
	
	WorkbenchPerspective defaultRootPerspective();

	WorkbenchPerspective defaultHomeFolderPerspective();
	
	WorkbenchPerspective defaultActionbarPerspective();
	
	WorkbenchPerspective defaultGlobalActionbarPerspective();

	WorkbenchPerspective defaultHeaderbarPerspective();

	WorkbenchPerspective defaultTabActionbarPerspective();
	
	WorkbenchPerspective defaultViewActionbarPerspective();

	Folder defaultActionbarFolder();
	
	Folder defaultViewActionbarFolder();

	Folder exchangeContentViewFolder();
	
	Folder expandFolder();
	
	Folder showDetailsFolder();
	
	Folder restoreFolder();
	
	Folder hideDetailsFolder();

	Folder workWithEntityFolder();

	Folder gimaOpenerFolder();

	Folder deleteEntityFolder();

	Folder changeInstanceFolder();

	Folder clearEntityToNullFolder();

	Folder addToCollectionFolder();

	Folder insertBeforeToListFolder();

	Folder removeFromCollectionFolder();

	Folder clearCollectionFolder();

	Folder refreshEntitiesFolder();

	Folder resourceDownloadFolder();

	Folder executeServiceRequestFolder();

	Folder addToClipboardFolder();
	
	Folder defaultHeaderbarFolder();

	Folder tbLogoFolder();

	Folder quickAccessSlotFolder();

	Folder globalStateSlotFolder();

	Folder defaultSettingsMenuFolder();

	Folder reloadSessionFolder();

	Folder showSettingsFolder();

	Folder uiThemeFolder();

	Folder showAboutFolder();

	Folder defaultUserMenuFolder();

	Folder showUserProfileFolder();

	Folder showLogoutFolder();

	Folder defaultTabActionbarFolder();

	Folder defaultExplorerFolder();

	Folder homeConstellationFolder();

	Folder changesConstellationFolder();

	Folder transientChangesConstellationFolder();

	Folder clipboardConstellationFolder();

	Folder notificationsConstellationFolder();

	Folder defaultSelectionFolder();

	Folder quickAccessConstellationFolder();

	Folder expertUiFolder();
	
	Folder defaultGlobalActionbarFolder();

	Folder newFolder();

	Folder dualSectionButtonsFolder();

	Folder uploadFolder();

	Folder undoFolder();

	Folder redoFolder();

	Folder commitFolder();

}
