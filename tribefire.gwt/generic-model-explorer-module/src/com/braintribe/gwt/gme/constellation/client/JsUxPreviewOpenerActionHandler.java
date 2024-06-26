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
package com.braintribe.gwt.gme.constellation.client;

import java.util.function.Function;

import com.braintribe.gwt.gme.constellation.client.expert.AbstractPreviewOpenerActionHandler;
import com.braintribe.gwt.gmview.client.GmContentViewWindow;
import com.braintribe.gwt.gmview.client.GmListView;
import com.braintribe.gwt.gmview.client.js.ExternalWidgetGmContentView;
import com.braintribe.gwt.gmview.client.js.JsUxComponentContext;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

import tribefire.extension.js.model.deployment.JsUxComponent;
import tribefire.extension.js.model.deployment.JsUxPreviewOpenerAction;

/**
 * Handler for handling {@link JsUxPreviewOpenerAction} within the workbench.
 * @author michel.docouto
 *
 */
public class JsUxPreviewOpenerActionHandler extends AbstractPreviewOpenerActionHandler {
	
	private ExternalWidgetGmContentView externalView;
	private Function<JsUxComponentContext, ExternalWidgetGmContentView> jsUxComponentWidgetSupplier;
	private PersistenceGmSession gmSession;
	
	public JsUxPreviewOpenerActionHandler() {
		maskPreview = false;
		handleBeforeHide = true;
	}
	
	/**
	 * Configures the required supplier for a given {@link JsUxComponent}.
	 */
	@Required
	public void setJsUxComponentWidgetSupplier(Function<JsUxComponentContext, ExternalWidgetGmContentView> jsUxComponentWidgetSupplier) {
		this.jsUxComponentWidgetSupplier = jsUxComponentWidgetSupplier;
	}
	
	/**
	 * Configures the required {@link PersistenceGmSession} which will be configured to the external view.
	 */
	@Required
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}

	@Override
	protected GmListView getView() {
		if (externalView != null)
			return externalView;
		
		JsUxComponent jsUxComponent = ((JsUxPreviewOpenerAction) action).getComponent();
		
		externalView = jsUxComponentWidgetSupplier.apply(new JsUxComponentContext(jsUxComponent, getContentViewWindow()));
		externalView.configureGmSession(gmSession); //TODO: is this needed?
		externalView.addContentSpecificationListener(this);
		return externalView;
	}
	
	private GmContentViewWindow getContentViewWindow() {
		return new GmContentViewWindow() {
			@Override
			public void restore() {
				if (documentWindow != null)
					documentWindow.restore();
			}
			
			@Override
			public void maximize() {
				if (documentWindow != null)
					documentWindow.maximize();
			}
			
			@Override
			public boolean isMaximized() {
				return documentWindow != null ? documentWindow.isMaximized() : false;
			}
			
			@Override
			public void close() {
				if (documentWindow != null)
					documentWindow.hide();
			}
		};
	}

}
