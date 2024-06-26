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
package com.braintribe.gwt.gme.constellation.client.action;

import java.util.Arrays;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gme.constellation.client.BrowsingConstellation;
import com.braintribe.gwt.gme.constellation.client.ChangesConstellation;
import com.braintribe.gwt.gme.constellation.client.ClipboardConstellation;
import com.braintribe.gwt.gme.constellation.client.CustomizationConstellation;
import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gme.constellation.client.MasterDetailConstellation;
import com.braintribe.gwt.gme.constellation.client.QueryConstellation;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.braintribe.gwt.gme.notification.client.NotificationConstellation;
import com.braintribe.gwt.gmview.action.client.ActionWithoutContext;
import com.braintribe.gwt.gmview.action.client.KnownActions;
import com.braintribe.gwt.gmview.action.client.LocalizedText;
import com.braintribe.gwt.gmview.client.DoubleStateAction;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;

/**
 * Action used for maximizing or restore a {@link MasterDetailConstellation}'s master view.
 * @author michel.docouto
 *
 */
@SuppressWarnings("unusable-by-js")
public class MaximizeViewAction extends ModelAction implements ActionWithoutContext, DoubleStateAction {
	
	private static boolean maximized = false;
	private ImageResource statusIcon1;
	private ImageResource statusIcon2;
	private String statusDescription1;
	private String statusDescription2;
	
	public MaximizeViewAction() {
		setHidden(false);
		setName(KnownActions.MAXIMIZE.getName());
		setTooltip(LocalizedText.INSTANCE.maximize());
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
		setStateIcon1(ConstellationResources.INSTANCE.maximize64());
		setStateIcon2(ConstellationResources.INSTANCE.restore64());
		setStateDescription1(LocalizedText.INSTANCE.maximize());
		setStateDescription2(LocalizedText.INSTANCE.restore());
		updateNameAndIcons();
	}
	
	@Override
	public void configureGmContentView(GmContentView gmContentView) {
		super.configureGmContentView(gmContentView);
		setHidden(gmContentView == null);
	}
	
	@Override
	protected void updateVisibility() {
		//setHidden(false);		
		setHidden(gmContentView == null, true);
		updateNameAndIcons();
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		boolean changedState;
		if (maximized)
			changedState = restore(gmContentView);
		else
			changedState = maximize(gmContentView);
		
		if (changedState)
			updateNameAndIcons();
	}

	private void updateNameAndIcons() {
		//setName(maximized ? LocalizedText.INSTANCE.restore() : LocalizedText.INSTANCE.maximize());
		setTooltip(maximized ? getStateDescription2()  : getStateDescription1());
		setIcon(maximized ? getStateIcon2() : getStateIcon1());
		setHoverIcon(maximized ? getStateIcon2() : getStateIcon1());
		//setHoverIcon(maximized ? ConstellationResources.INSTANCE.restoreBig() : ConstellationResources.INSTANCE.maximizeBig());
	}
	
	private boolean maximize(Object view) {
		if (maximizeParent(view)) {
			maximized = true;
			return true;
		}
		
		return false;
	}
	
	private boolean restore(Object view) {
		if (restoreParent(view)) {
			maximized = false;
			return true;
		}
		
		return false;
	}
	
	private boolean maximizeParent(Object parent) {
		if (parent instanceof QueryConstellation) {
			((QueryConstellation) parent).hideQueryEditor();
			maximizeParent(((QueryConstellation) parent).getParent());
		} else if (parent instanceof BrowsingConstellation) {
			//((BrowsingConstellation) parent).hideTetherBar();
			maximizeParent(((BrowsingConstellation) parent).getParent());
		} else if (parent instanceof NotificationConstellation)
			maximizeParent(((NotificationConstellation) parent).getParent());
		else if (parent instanceof ClipboardConstellation)
			maximizeParent(((ClipboardConstellation) parent).getParent());
		else if (parent instanceof ChangesConstellation)
			maximizeParent(((ChangesConstellation) parent).getParent());
		else if (parent instanceof MasterDetailConstellation)
			maximizeParent(((MasterDetailConstellation) parent).getParent());
		else if (parent instanceof ExplorerConstellation) {
			((ExplorerConstellation) parent).hideWorkbenchAndVerticalTabPanel();
			maximizeParent(((ExplorerConstellation) parent).getParent());
		} else if (parent instanceof CustomizationConstellation) {
			((CustomizationConstellation) parent).hideHeader();
			((CustomizationConstellation) parent).forceLayout();
		} else if (parent instanceof Widget)
			maximizeParent(((Widget) parent).getParent());
		else
			return false;
		
		return true;
	}

	private boolean restoreParent(Object parent) {
		if (parent instanceof QueryConstellation) {
			((QueryConstellation) parent).restoreQueryEditor();
			restoreParent(((QueryConstellation) parent).getParent());
		} else if (parent instanceof BrowsingConstellation) {
			//((BrowsingConstellation) parent).restoreTetherBar();
			restoreParent(((BrowsingConstellation) parent).getParent());
		} else if (parent instanceof NotificationConstellation)
			restoreParent(((NotificationConstellation) parent).getParent());
		else if (parent instanceof ClipboardConstellation)
			restoreParent(((ClipboardConstellation) parent).getParent());
		else if (parent instanceof ChangesConstellation)
			restoreParent(((ChangesConstellation) parent).getParent());
		else if (parent instanceof MasterDetailConstellation)
			restoreParent(((MasterDetailConstellation) parent).getParent());
		else if (parent instanceof ExplorerConstellation) {
			((ExplorerConstellation) parent).restoreWorkbenchAndVerticalTabPanel();
			restoreParent(((ExplorerConstellation) parent).getParent());
		} else if (parent instanceof CustomizationConstellation) {
			((CustomizationConstellation) parent).restoreHeader();
			((CustomizationConstellation) parent).forceLayout();
		} else if (parent instanceof Widget)
			restoreParent(((Widget) parent).getParent());
		else
			return false;
		
		return true;
	}

	@Override
	public void setStateIcon1(ImageResource icon) {
		statusIcon1 = icon;	
	}

	@Override
	public void setStateIcon2(ImageResource icon) {
		statusIcon2 = icon;	
	}

	@Override
	public ImageResource getStateIcon1() {
		return statusIcon1;
	}

	@Override
	public ImageResource getStateIcon2() {
		return statusIcon2;
	}

	@Override
	public void setStateDescription1(String description) {
		statusDescription1 = description;		
	}

	@Override
	public void setStateDescription2(String description) {
		statusDescription2 = description;		
	}

	@Override
	public String getStateDescription1() {
		return statusDescription1;
	}

	@Override
	public String getStateDescription2() {
		return statusDescription2;
	}

	@Override
	public void updateState() {
		updateNameAndIcons();
	}

	@Override
	public Boolean isDefaultState() {
		return !maximized ;
	}
	
}
