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
package tribefire.extension.wopi.wb_initializer.wire.space;

import com.braintribe.logging.Logger;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.wopi.wb_initializer.wire.contract.CommonWorkbenchContract;
import tribefire.extension.wopi.wb_initializer.wire.contract.IconContract;

@Managed
public class CommonWorkbenchSpace extends AbstractInitializerSpace implements CommonWorkbenchContract {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CommonWorkbenchSpace.class);

	@Import
	private DefaultWbContract workbench;

	@Import
	private IconContract icons;

	@Override
	public void standardActions() {

		workbench.exchangeContentViewFolder().setIcon(icons.viewIcon());
		// workbench.expandFolder().setIcon(icons.expandIcon());
		workbench.workWithEntityFolder().setIcon(icons.openIcon());
		workbench.gimaOpenerFolder().setIcon(icons.infoIcon());
		workbench.gimaOpenerFolder().getDisplayName().putDefault("Details");
		workbench.gimaOpenerFolder().setName("$gimaOpenerForDetails");
		workbench.deleteEntityFolder().setIcon(icons.deleteIcon());
		workbench.changeInstanceFolder().setIcon(icons.assignIcon());
		workbench.clearEntityToNullFolder().setIcon(icons.removeIcon());
		workbench.addToCollectionFolder().setIcon(icons.addIcon());
		workbench.insertBeforeToListFolder().setIcon(icons.addIcon());
		workbench.removeFromCollectionFolder().setIcon(icons.removeIcon());
		workbench.clearCollectionFolder().setIcon(icons.removeIcon());
		workbench.resourceDownloadFolder().setIcon(icons.downloadIcon());
		workbench.executeServiceRequestFolder().setIcon(icons.runIcon());
		workbench.homeConstellationFolder().setIcon(icons.homeIcon());
		workbench.notificationsConstellationFolder().setIcon(icons.mailIcon());
		workbench.clipboardConstellationFolder().setIcon(icons.copyIcon());
		workbench.changesConstellationFolder().setIcon(icons.changesIcon());
		workbench.quickAccessConstellationFolder().setIcon(icons.quickAccessIcon());

		workbench.newFolder().setIcon(icons.addIcon());
		workbench.uploadFolder().setIcon(icons.uploadIcon());
		workbench.undoFolder().setIcon(icons.backIcon());
		workbench.redoFolder().setIcon(icons.nextIcon());
		workbench.commitFolder().setIcon(icons.commitIcon());
		// workbench.refreshEntitiesFolder().setIcon(icons.refreshIcon());
		// workbench.addToClipboardFolder()

		workbench.defaultActionbarFolder().getSubFolders().remove(workbench.addToClipboardFolder());
		workbench.insertBeforeToListFolder().getDisplayName().putDefault("Insert");

		workbench.defaultTabActionbarFolder().getSubFolders().remove(workbench.clipboardConstellationFolder());
		workbench.defaultTabActionbarFolder().getSubFolders().remove(workbench.changesConstellationFolder());

		workbench.defaultSelectionFolder().getSubFolders().remove(workbench.homeConstellationFolder());
		workbench.defaultSelectionFolder().getSubFolders().remove(workbench.changesConstellationFolder());

	}

}
