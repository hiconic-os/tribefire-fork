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
package com.braintribe.gwt.gme.constellation.client.gima;

import java.util.List;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.gme.constellation.client.GIMADialog;
import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gme.constellation.client.MasterDetailConstellation;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.braintribe.gwt.gmview.action.client.ActionGroup;
import com.braintribe.gwt.gmview.action.client.ActionTypeAndName;
import com.braintribe.gwt.gmview.client.GmActionSupport;
import com.braintribe.gwt.gmview.client.GmCheckListener;
import com.braintribe.gwt.gmview.client.GmCheckSupport;
import com.braintribe.gwt.gmview.client.GmContentSupport;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmContentViewActionManager;
import com.braintribe.gwt.gmview.client.GmContentViewListener;
import com.braintribe.gwt.gmview.client.GmInteractionListener;
import com.braintribe.gwt.gmview.client.GmInteractionSupport;
import com.braintribe.gwt.gmview.client.GmSelectionListener;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client.FixedTextButtonCell;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class MasterDetailGIMAView extends SimpleContainer implements GIMAView, GmCheckSupport, GmInteractionSupport, GmActionSupport, GmContentSupport {
	
	private TextButton mainButton;
	private MasterDetailConstellation masterDetailConstellation;
	private GIMADialog gimaDialog;
	
	public MasterDetailGIMAView(MasterDetailConstellation masterDetailConstellation, GIMADialog gimaDialog) {
		this.masterDetailConstellation = masterDetailConstellation;
		this.gimaDialog = gimaDialog;
		add(masterDetailConstellation);
	}

	@Override
	public TextButton getMainButton() {
		if (mainButton != null)
			return mainButton;

		mainButton = new TextButton(new FixedTextButtonCell(), LocalizedText.INSTANCE.apply());
		mainButton.setToolTip(LocalizedText.INSTANCE.applyDescription());
		mainButton.setScale(ButtonScale.LARGE);
		mainButton.addSelectHandler(event -> gimaDialog.performApply(false));
		mainButton.setIcon(ConstellationResources.INSTANCE.finish());
		mainButton.setEnabled(true);
		mainButton.addStyleName(GIMADialog.GIMA_MAIN_BUTTON);

		return mainButton;
	}

	@Override
	public List<ButtonConfiguration> getAdditionalButtons() {
		return null;
	}
	
	@Override
	public boolean isApplyAllHandler() {
		return false;
	}
	
	@Override
	public ModelPath getContentPath() {
		return masterDetailConstellation.getContentPath();
	}
	
	@Override
	public void configureActionGroup(ActionGroup actionGroup) {
		masterDetailConstellation.configureActionGroup(actionGroup);
	}
	
	@Override
	public void configureExternalActions(List<Pair<ActionTypeAndName, ModelAction>> externalActions) {
		masterDetailConstellation.configureExternalActions(externalActions);
	}

	@Override
	public List<Pair<ActionTypeAndName, ModelAction>> getExternalActions() {
		return masterDetailConstellation.getExternalActions();
	}
	
	@Override
	public void setActionManager(GmContentViewActionManager actionManager) {
		masterDetailConstellation.setActionManager(actionManager);
	}

	@Override
	public void setContent(ModelPath modelPath) {
		masterDetailConstellation.setContent(modelPath);
	}

	@Override
	public void addGmContentViewListener(GmContentViewListener listener) {
		masterDetailConstellation.addGmContentViewListener(listener);
	}

	@Override
	public void removeGmContentViewListener(GmContentViewListener listener) {
		masterDetailConstellation.removeGmContentViewListener(listener);
	}

	@Override
	public void addSelectionListener(GmSelectionListener sl) {
		masterDetailConstellation.addSelectionListener(sl);
	}

	@Override
	public void removeSelectionListener(GmSelectionListener sl) {
		masterDetailConstellation.removeSelectionListener(sl);
	}

	@Override
	public ModelPath getFirstSelectedItem() {
		return masterDetailConstellation.getFirstSelectedItem();
	}

	@Override
	public List<ModelPath> getCurrentSelection() {
		return masterDetailConstellation.getCurrentSelection();
	}

	@Override
	public boolean isSelected(Object element) {
		return masterDetailConstellation.isSelected(element);
	}

	@Override
	public void select(int index, boolean keepExisting) {
		masterDetailConstellation.select(index, keepExisting);
	}

	@Override
	public GmContentView getView() {
		return this;
	}

	@Override
	public void addInteractionListener(GmInteractionListener il) {
		masterDetailConstellation.addInteractionListener(il);
	}

	@Override
	public void removeInteractionListener(GmInteractionListener il) {
		masterDetailConstellation.removeInteractionListener(il);
	}

	@Override
	public void addCheckListener(GmCheckListener cl) {
		masterDetailConstellation.addCheckListener(cl);
	}

	@Override
	public void removeCheckListener(GmCheckListener cl) {
		masterDetailConstellation.removeCheckListener(cl);
	}

	@Override
	public ModelPath getFirstCheckedItem() {
		return masterDetailConstellation.getFirstCheckedItem();
	}

	@Override
	public List<ModelPath> getCurrentCheckedItems() {
		return masterDetailConstellation.getCurrentCheckedItems();
	}

	@Override
	public boolean isChecked(Object element) {
		return masterDetailConstellation.isChecked(element);
	}

	@Override
	public boolean uncheckAll() {
		return masterDetailConstellation.uncheckAll();
	}

	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		masterDetailConstellation.configureGmSession(gmSession);
	}

	@Override
	public PersistenceGmSession getGmSession() {
		return masterDetailConstellation.getGmSession();
	}

	@Override
	public void configureUseCase(String useCase) {
		masterDetailConstellation.configureUseCase(useCase);
	}

	@Override
	public String getUseCase() {
		return masterDetailConstellation.getUseCase();
	}

}
