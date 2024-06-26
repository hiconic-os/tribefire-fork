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
package com.braintribe.gwt.modeller.client.filter;

import java.util.function.Predicate;
import java.util.function.Supplier;

import com.braintribe.gwt.gmview.action.client.SpotlightPanel;
import com.braintribe.gwt.modeller.client.GmModellerPanel;
import com.braintribe.gwt.modeller.client.typesoverview.GmModellerTypesOverviewPanel;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.modellerfilter.view.ModellerView;
import com.braintribe.model.processing.modellergraph.ModelGraphConfigurationsNew;
import com.braintribe.model.processing.modellergraph.filter.relationship.Relationship;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

public class GmModellerFilterPanel extends FlowPanel{
	
//	private GmModellerPanel gmModellerPanel;
//	private GmModellerTypesOverviewPanel typesOverviewPanel;
	private PersistenceGmSession session;
//	private ModelGraphConfigurationsNew modelGraphConfigurations;
//	private GmMetaModel gmMetaModel;
	private DefaultFiltering defaultFiltering = new DefaultFiltering();
	private ModellerView currentModellerView;
//	private List<GmModellerFilterPanelSection> sections = new ArrayList<>();
	Supplier<SpotlightPanel> spotlightPanelProvider;
	
	FilterPanelSectionContext includes;
	FilterPanelSectionContext excludes;
	FilterPanelSectionContext relationship;
	FilterPanelSectionContext settingz;
	
	private TextBox viewName;
	private FlowPanel sectionsWrapperPanel;
	RelationshipFilterValuesProvider relationshipFilterValuesProvider = new RelationshipFilterValuesProvider();
	
	public GmModellerFilterPanel() {
		includes = DefaultFiltering.prepareIncludesFilterContext(null);
		excludes = DefaultFiltering.prepareExcludesFilterContext(null);
		relationship = DefaultFiltering.prepareRelationshipFilterContext(null);
		settingz = DefaultFiltering.prepareSettingsContext(null);
		
		add(getViewName());
		add(getSectionsWrapperPanel());	
		addStyleName("gmModelerFilterPanel");
	}
	
	private void init() {
		getSectionsWrapperPanel().clear();
		
		excludes.spotlightPanelProvider = spotlightPanelProvider;
		excludes.relationshipFilterValuesProvider = relationshipFilterValuesProvider;
		GmModellerFilterPanelSection exclude = new GmModellerFilterPanelSection(session, excludes, false);
		getSectionsWrapperPanel().add(exclude);
		
		includes.spotlightPanelProvider = spotlightPanelProvider;
		includes.relationshipFilterValuesProvider = relationshipFilterValuesProvider;
		GmModellerFilterPanelSection include = new GmModellerFilterPanelSection(session, includes);
		getSectionsWrapperPanel().add(include);
		
		relationship.spotlightPanelProvider = spotlightPanelProvider;
		relationship.relationshipFilterValuesProvider = relationshipFilterValuesProvider;
		GmModellerFilterPanelSection relationshipSection = new GmModellerFilterPanelSection(session, relationship);
		getSectionsWrapperPanel().add(relationshipSection);
		
		getSectionsWrapperPanel().add(new GmModellerFilterPanelSection(session, settingz));
	}
	
	@SuppressWarnings("unused")
	public void setTypesOverviewPanel(GmModellerTypesOverviewPanel typesOverviewPanel) {
//		this.typesOverviewPanel = typesOverviewPanel;
	}
	
	@SuppressWarnings("unused")
	public void setGmModellerPanel(GmModellerPanel gmModellerPanel) {
//		this.gmModellerPanel = gmModellerPanel;
	}

	public void setModelGraphConfigurations(ModelGraphConfigurationsNew modelGraphConfigurations) {
		defaultFiltering.setModelGraphConfigurations(modelGraphConfigurations);
	}

	public void setGmMetaModel(GmMetaModel gmMetaModel) {
		if(gmMetaModel != null)
			relationshipFilterValuesProvider.setMetaModel(gmMetaModel);	
	}

	public void setModellerView(ModellerView currentModellerView) {
		this.currentModellerView = currentModellerView;
		
		if(currentModellerView != null) {
		
			getViewName().setText(currentModellerView.getName());
			
			includes.parentEntity = currentModellerView.getIncludesFilterContext();
			excludes.parentEntity = currentModellerView.getExcludesFilterContext();
			relationship.parentEntity = currentModellerView.getRelationshipKindFilterContext();
			settingz.parentEntity = currentModellerView.getSettings();
		}else {
			getViewName().setText("");
		}
		
		init();
	}
	
	public TextBox getViewName() {
		if(viewName == null) {
			viewName = new TextBox();
			viewName.getElement().setAttribute("placeholder", "viewName");
			viewName.addStyleName("gmModelerFilterViewName");
			viewName.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					String newName = viewName.getValue();
					if(newName != null) {
						currentModellerView.setName(newName);
					}
				}
			});
			
			viewName.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
						viewName.setFocus(false);
				}
			});
			viewName.setEnabled(false);
		}
		return viewName;
	}
	
	public FlowPanel getSectionsWrapperPanel() {
		if(sectionsWrapperPanel == null) {
			sectionsWrapperPanel = new FlowPanel();
		}
		return sectionsWrapperPanel;
	}

	public void setSession(PersistenceGmSession session) {
		this.session = session;
	}

	public void setQuickAccessPanelProvider(Supplier<SpotlightPanel> spotlightPanelProvider) {
		this.spotlightPanelProvider = spotlightPanelProvider;
	}

	public Predicate<Relationship> getFilter() {
		return defaultFiltering.getFilter(currentModellerView, null /* typesOverviewPanel.getExcludes() */);
	}

}
