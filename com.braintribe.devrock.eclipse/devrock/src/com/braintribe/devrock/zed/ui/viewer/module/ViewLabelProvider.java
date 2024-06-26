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
package com.braintribe.devrock.zed.ui.viewer.module;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import com.braintribe.cfg.Configurable;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.api.ui.stylers.Stylers;
import com.braintribe.devrock.zarathud.model.common.Node;
import com.braintribe.devrock.zarathud.model.module.ImportedModuleNode;
import com.braintribe.devrock.zarathud.model.module.ModuleReferenceNode;
import com.braintribe.devrock.zarathud.model.module.PackageNode;
import com.braintribe.utils.lcd.LazyInitialized;

public class ViewLabelProvider extends CellLabelProvider implements IStyledLabelProvider {
	
	//private boolean showUnicodePrefixes = true;
	private UiSupport uiSupport;
	private String uiSupportStylersKey;

	private static final String STYLER_STANDARD = "standard";
	private static final String STYLER_EMPHASIS = "emphasis";
	
	private Image packageImage;
	private Image artifactImage;

	private LazyInitialized<Map<String, Styler>> stylerMap = new LazyInitialized<>( this::setupUiSupport);
	
	
	@Configurable
	public void setUiSupport(UiSupport uiSupport) {
		this.uiSupport = uiSupport;					
	}
	
	@Configurable
	public void setUiSupportStylersKey(String uiSupportStylersKey) {
		this.uiSupportStylersKey = uiSupportStylersKey;
	}
		
	/**
	 * lazy initializer for the ui stuff 
	 * @return
	 */
	private Map<String,Styler> setupUiSupport() {		
		
		Stylers stylers = uiSupport.stylers(uiSupportStylersKey);
		stylers.addStandardStylers();
		
		packageImage = uiSupport.images().addImage("package", ViewLabelProvider.class, "package_obj.png");
		artifactImage = uiSupport.images().addImage("artifact", ViewLabelProvider.class, "solution.jar.gif");
		
		Map<String,Styler> map = new HashMap<>();
		map.put(STYLER_EMPHASIS, stylers.standardStyler(Stylers.KEY_BOLD));
		map.put(STYLER_STANDARD, stylers.standardStyler(Stylers.KEY_DEFAULT));		
		return map;
	}
	
	@Override
	public Image getImage(Object obj) {
		// trigger lazy if required - for the pictures 
		stylerMap.get();
		
		if (obj instanceof PackageNode) {
			return packageImage;
		}
		else if (obj instanceof ImportedModuleNode) {
			return artifactImage;
		}
		return null;
	}

	@Override
	public StyledString getStyledText(Object arg0) {
		Node node = (Node) arg0;
		if (node instanceof ModuleReferenceNode) {
			ModuleReferenceNode moduleReferenceNode = (ModuleReferenceNode) node;
			return new StyledString( moduleReferenceNode.getReferencedArtifact());
		}
		else if (node instanceof ImportedModuleNode) {
			ImportedModuleNode importedModuleNode = (ImportedModuleNode) node;
			return new StyledString( importedModuleNode.getArtifactName());
		}
		else if (node instanceof PackageNode) {
			PackageNode packageNode = (PackageNode) node;
			return new StyledString(packageNode.getImportedPackageName());
		}
		return null;
	}

	@Override
	public void update(ViewerCell arg0) {	
	}
	
	
}
