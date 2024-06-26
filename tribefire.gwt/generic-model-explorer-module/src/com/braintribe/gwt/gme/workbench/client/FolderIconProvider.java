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
package com.braintribe.gwt.gme.workbench.client;

import java.util.List;

import com.braintribe.gwt.gmresourceapi.client.GmImageResource;
import com.braintribe.gwt.gmview.client.IconAndType;
import com.braintribe.gwt.gmview.client.IconProviderAdapter;
import com.braintribe.gwt.gmview.util.client.GMEIconUtil;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.ModelPathElement;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Icon;
import com.braintribe.model.resource.Resource;


/**
 * Icon provider for the {@link Folder} entity.
 * @author michel.docouto
 *
 */
public class FolderIconProvider extends IconProviderAdapter {
	
	public enum IconSize {
		small, medium, large
	}
	
	private IconSize iconSize = IconSize.small;
	private List<IconSize> iconSizes;
	private PersistenceGmSession gmSession;
	
	/**
	 * Configures the desirable icon size to be returned. Defaults to small.
	 */
	@Configurable
	public void setIconSize(IconSize iconSize) {
		this.iconSize = iconSize;
	}
	
	/**
	 * Configures the desirable icon sizes to be returned. Defaults to null.
	 * Used for accepting more than one size. The icons are searched in the order they appear in the list.
	 */
	public void setIconSizes(List<IconSize> iconSizes) {
		this.iconSizes = iconSizes;
	}
	
	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	@Override
	public PersistenceGmSession getGmSession() {
		return gmSession;
	}

	@Override
	public IconAndType apply(ModelPath modelPath) {
		if (modelPath == null)
			return null;
		
		ModelPathElement pathElement = modelPath.last();
		Object value = pathElement.getValue();
		if (!(value instanceof Folder))
			return null;
		
		Icon icon = ((Folder) value).getIcon();
		Resource imageResource = null;
		if (iconSizes != null && !iconSizes.isEmpty()) {
			for (IconSize iconSize : iconSizes) {
				imageResource = getIcon(iconSize, icon);
				if (imageResource != null)
					break;
			}
		} else
			imageResource = getIcon(iconSize, icon);
		
		if (imageResource != null)
			return new IconAndType(new GmImageResource(imageResource, gmSession.resources().url(imageResource).asString()), false);
		
		return null;
	}
	
	private Resource getIcon(IconSize iconSize, Icon icon) {
		Resource imageResource;
		switch (iconSize) {
		case small:
			imageResource = GMEIconUtil.getSmallImageFromIcon(icon);
			break;
		case medium:
			imageResource = GMEIconUtil.getMediumImageFromIcon(icon);
			break;
		case large:
		default:
			imageResource = GMEIconUtil.getLargeImageFromIcon(icon);
			break;
		}
		
		return imageResource;
	}

}
