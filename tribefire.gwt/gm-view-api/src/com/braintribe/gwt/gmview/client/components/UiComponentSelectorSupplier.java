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
package com.braintribe.gwt.gmview.client.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.braintribe.gwt.gmview.client.TabbedWidgetContext;
import com.braintribe.gwt.gmview.client.js.ExternalWidgetGmContentView;
import com.braintribe.gwt.gmview.client.js.JsUxComponentContext;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.ModelPathElement;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.meta.cmd.builders.EntityMdResolver;
import com.braintribe.model.processing.session.api.common.GmSessions;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.utils.i18n.I18nTools;

import tribefire.extension.js.model.deployment.DetailWithUiComponent;
import tribefire.extension.js.model.deployment.JsUxComponent;
import tribefire.extension.js.model.deployment.PropertyPanelUxComponent;

/**
 * Function responsible for preparing a list of {@link TabbedWidgetContext} based on the given {@link ModelPathElement},
 * by checking the {@link DetailWithUiComponent} and getting the components configured there. When no component is set,
 * then we use the {@link PropertyPanelUxComponent} as default.
 * 
 * @author michel.docouto
 *
 */
public class UiComponentSelectorSupplier implements Function<ModelPathElement, List<TabbedWidgetContext>> {
	
	private PersistenceGmSession gmSession;
	private Set<String> useCases;
	private Map<EntityType<? extends JsUxComponent>, TabbedWidgetContext> wellKnownComponentsContextMap;
	private Function<JsUxComponentContext, ExternalWidgetGmContentView> externalWidgetSupplier;
	
	/**
	 * Configures the required {@link PersistenceGmSession}.
	 */
	@Required
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/**
	 * Configures the required supplier which supplies an external widget.
	 */
	@Required
	public void setExternalWidgetSupplier(Function<JsUxComponentContext, ExternalWidgetGmContentView> externalWidgetSupplier) {
		this.externalWidgetSupplier = externalWidgetSupplier;
	}
	
	/**
	 * Configures a list of well known components and their contexts.
	 */
	@Configurable
	public void setWellKnownComponentsContextMap(Map<EntityType<? extends JsUxComponent>, TabbedWidgetContext> wellKnownComponentsContextMap) {
		this.wellKnownComponentsContextMap = wellKnownComponentsContextMap;
	}
	
	/**
	 * Use cases used when resolving the {@link DetailWithUiComponent} metadata.
	 */
	@Configurable
	public void setUseCases(Set<String> useCases) {
		this.useCases = useCases;
	}

	@Override
	public List<TabbedWidgetContext> apply(ModelPathElement element) {
		EntityMdResolver entityMdResolver = getEntityMdResolver(element);
		
		if (useCases != null)
			entityMdResolver = entityMdResolver.useCases(useCases);
		
		List<TabbedWidgetContext> contexts = new ArrayList<>();
		
		List<DetailWithUiComponent> list = entityMdResolver.meta(DetailWithUiComponent.T).list();
		int counter = 0;
		for (DetailWithUiComponent md : list) {
			TabbedWidgetContext context = null;
			
			if (wellKnownComponentsContextMap != null) {
				EntityType<? extends JsUxComponent> entityType = null;
				if (md.getComponent() != null)
					entityType = md.getComponent().entityType();
				else
					entityType = PropertyPanelUxComponent.T;
				
				context = wellKnownComponentsContextMap.get(entityType);
			}
			
			if (context == null) {
				String name = md.getComponent().getModule().getName();
				String display = I18nTools.getLocalized(md.getDisplayName());
				//if the DetailWithUiComponent has priority > 0.5, then it will be added first in the list. Otherwise, it will be added in the end
				int index = -1;
				
				double conflictPriority = (md.getConflictPriority() != null) ? md.getConflictPriority() : 0; 				
				if (conflictPriority > 0.5)
					index = counter++;
				context = new TabbedWidgetContext(name, display,() -> {
					ExternalWidgetGmContentView view = externalWidgetSupplier.apply(new JsUxComponentContext(md.getComponent()));
					view.setReadOnly(md.getReadOnly());
					return view;
				}, index, md.getHideDefaultDetails());
			}
			
			contexts.add(context);
		}
		
		return contexts;
	}
	
	private EntityMdResolver getEntityMdResolver(ModelPathElement element) {
		if (element.getValue() instanceof GenericEntity) {
			GenericEntity entity = element.getValue();
			return GmSessions.getMetaData(entity).lenient(true).entity(entity);
		}
		
		return gmSession.getModelAccessory().getMetaData().lenient(true).entityType((EntityType<?>) element.getType());
	}

}
