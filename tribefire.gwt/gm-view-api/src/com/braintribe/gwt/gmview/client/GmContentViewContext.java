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
package com.braintribe.gwt.gmview.client;

import java.util.function.Supplier;

import com.braintribe.model.generic.path.ModelPathElement;
import com.google.gwt.resources.client.ImageResource;

public class GmContentViewContext implements ViewContext {
	
	private Supplier<? extends GmContentView> contentViewProvider;
	private Supplier<? extends GmEntityView> detailViewProvider;
	private String name;
	private ImageResource icon;
	private ImageResource hoverIcon;
	private String useCase;
	private boolean showDetails = true;
	private boolean forceUseCase;
	private boolean readOnly;
	private ModelPathElement modelPathElement;
	private boolean listView;
	
	public GmContentViewContext(Supplier<? extends GmContentView> contentViewProvider, String name, String useCase) {
		this(contentViewProvider, name, useCase, false);
	}
	
	public GmContentViewContext(Supplier<? extends GmContentView> contentViewProvider, String name, String useCase, boolean listView) {
		this.contentViewProvider = contentViewProvider;
		this.name = name;
		this.useCase = useCase;
		this.listView = listView;
	}
	
	public GmContentViewContext(Supplier<? extends GmContentView> contentViewProvider, String name, ImageResource icon, ImageResource hoverIcon,
			String useCase) {
		this(contentViewProvider, name, icon, hoverIcon, useCase, false);
	}
	
	public GmContentViewContext(Supplier<? extends GmContentView> contentViewProvider, String name, ImageResource icon, ImageResource hoverIcon,
			String useCase, boolean listView) {
		this.contentViewProvider = contentViewProvider;
		this.name = name;
		this.icon = icon;
		this.hoverIcon = hoverIcon;
		this.useCase = useCase;
		this.listView = listView;
	}
	
	public Supplier<? extends GmContentView> getContentViewProvider() {
		return contentViewProvider;
	}
	
	public void setContentViewProvider(Supplier<? extends GmContentView> contentViewProvider) {
		this.contentViewProvider = contentViewProvider;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIcon(ImageResource icon) {
		this.icon = icon;
	}
	
	public ImageResource getIcon() {
		return icon;
	}
	
	public void setHoverIcon(ImageResource hoverIcon) {
		this.hoverIcon = hoverIcon;
	}
	
	public ImageResource getHoverIcon() {
		return hoverIcon;
	}
	
	public String getUseCase() {
		return useCase;
	}
	
	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}
	
	public void setShowDetails(boolean showDetails) {
		this.showDetails = showDetails;
	}
	
	public boolean getShowDetails() {
		return showDetails;
	}
	
	public void setForceUseCase(boolean forceUseCase) {
		this.forceUseCase = forceUseCase;
	}
	
	public boolean isForceUseCase() {
		return forceUseCase;
	}
	
	public void setListView(boolean listView) {
		this.listView = listView;
	}
	
	@Override
	public boolean isListView() {
		return listView;
	}
	
	public void setDetailViewProvider(Supplier<? extends GmEntityView> detailViewProvider) {
		this.detailViewProvider = detailViewProvider;
	}
	
	public Supplier<? extends GmEntityView> getDetailViewProvider() {
		return detailViewProvider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((contentViewProvider == null) ? 0 : contentViewProvider
						.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GmContentViewContext other = (GmContentViewContext) obj;
		if (contentViewProvider == null) {
			if (other.contentViewProvider != null)
				return false;
		} else if (!contentViewProvider.equals(other.contentViewProvider))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public void setModelPathElement(ModelPathElement modelPathElement) {
		this.modelPathElement = modelPathElement;
	}

	@Override
	public ModelPathElement getModelPathElement() {
		return modelPathElement;
	}

}
