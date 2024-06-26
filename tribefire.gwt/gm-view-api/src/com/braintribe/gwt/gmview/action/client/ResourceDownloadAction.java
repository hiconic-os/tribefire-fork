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
package com.braintribe.gwt.gmview.action.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gmview.action.client.resources.GmViewActionResources;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.ModelPathElement;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.TransientSource;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.shared.FastMap;

public class ResourceDownloadAction extends ModelAction{
	
	private Function<Resource,String> urlProvider;
	private PersistenceGmSession gmSession;
	private Map<String, String> mimeTypeToExtension;
	//private Set<String> supportedMimeTypes;
	
	/**
	 * Configures the provider used for building urls from resources.
	 * If this is not set, then {@link #gmSession} is required.
	 */
	@Configurable
	public void setUrlProvider(Function<Resource, String> urlProvider) {
		this.urlProvider = urlProvider;
	}
	
	/**
	 * Configures the session used for handling resources.
	 * If this is not set, then {@link #urlProvider} is required.
	 */
	@Configurable
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}

	//public void setSupportedMimeTypes(Set<String> supportedMimeTypes) {
		//this.supportedMimeTypes = supportedMimeTypes;
	//}
	
	public ResourceDownloadAction() {
		setName(LocalizedText.INSTANCE.downloadResource());
		setIcon(GmViewActionResources.INSTANCE.download());
		setHoverIcon(GmViewActionResources.INSTANCE.downloadBig());
		setHidden(true);
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
		
		mimeTypeToExtension = new FastMap<String>();
		mimeTypeToExtension.put("application/pdf", "pdf");
	}
	
	@Override
	protected void updateVisibility() {
		if (modelPaths != null && !modelPaths.isEmpty()) {
			for (List<ModelPath> selection : modelPaths) {
				for (ModelPath modelPath : selection) {
					if (modelPath.last().getValue() instanceof Resource) {
						setHidden(false);
						return;
					}
				}
			}
		}
		
		setHidden(true);
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		if (modelPaths == null || modelPaths.isEmpty())
			return;
		
		for (List<ModelPath> selection : modelPaths) {
			for (ModelPath modelPath : selection) {
				ModelPathElement modelPathElement = modelPath.last();
				if (!(modelPathElement.getValue() instanceof Resource))
					continue;
				
				Resource resource = modelPathElement.getValue();
				String url = null;
				String fileName = "";
				if (urlProvider != null) {
					try {
						url = urlProvider.apply(resource);
					} catch (RuntimeException e) {
						GlobalState.showError("Error while downloading the resource", e);
					}
				} else {
					String downloadName = resource.getName() != null ? resource.getName() : resource.getResourceSource().getId();
					String extension = resource.getMimeType() != null ? mimeTypeToExtension.get(resource.getMimeType()) : null;
					if(extension != null && downloadName.endsWith("." + extension))
						extension = null;
					url = gmSession.resources().url(resource)
							.fileName(extension == null ? downloadName : downloadName + "." + extension)
							.download(true)
							.asString();
					fileName = extension == null ? downloadName : downloadName + "." + extension;
				}
				
				if (url != null) {
					if(resource.getResourceSource() instanceof TransientSource) {
						AnchorElement a = Document.get().createAnchorElement();
						a.setHref(url);
						a.setAttribute("download", fileName);
						RootPanel.get().getElement().appendChild(a);
						click(a);
						RootPanel.get().getElement().removeChild(a);
					}else {
						Window.open(url, "_new", "");
					}
				}
					
			}
		}
	}
	
	public static native void click(AnchorElement a) /*-{
		a.click();
	}-*/;

}
