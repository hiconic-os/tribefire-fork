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
package com.braintribe.gwt.gmview.client.js.interop;

import java.util.List;

import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmSelectionListener;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.dom.client.Element;

import jsinterop.annotations.JsMethod;

/**
 * Interface used for exporting {@link GmContentView} via JsInterop.
 */
@SuppressWarnings("unusable-by-js")
public interface GmContentViewInterfaceInterop extends GmContentView {
	
	@Override
	@JsMethod
	public void addSelectionListener(GmSelectionListener sl);
	
	@Override
	@JsMethod
	public void removeSelectionListener(GmSelectionListener sl);

	@Override
	@JsMethod
	public ModelPath getFirstSelectedItem();
	
	@Override
	@JsMethod
	public int getFirstSelectedIndex();

	@Override
	@JsMethod
	public List<ModelPath> getCurrentSelection();

	@Override
	@JsMethod
	public boolean isSelected(Object element);

	@Override
	@JsMethod
	public void select(int index, boolean keepExisting);
	
	@Override
	@JsMethod (name = "selectElement")
	public boolean select(Element element, boolean keepExisting);
	
	@Override
	@JsMethod
	public void selectRoot(int index, boolean keepExisting);
	
	@Override
	@JsMethod
	public void deselectAll();

	@Override
	@JsMethod
	public GmContentView getView();
	
	@Override
	@JsMethod
	public void configureGmSession(PersistenceGmSession gmSession);
	
	@Override
	@JsMethod
	public PersistenceGmSession getGmSession();

	@Override
	@JsMethod
	public void configureUseCase(String useCase);

	@Override
	@JsMethod
	public String getUseCase();

	@Override
	@JsMethod
	public ModelPath getContentPath();

	@Override
	@JsMethod
	public void setContent(ModelPath modelPath);
	
	@Override
	@JsMethod
	public Object getUxElement();
	
	@Override
	@JsMethod
	public Object getUxWidget();
	
	@Override
	@JsMethod
	public void detachUxElement();
	
	@Override
	@JsMethod
	public void setReadOnly(boolean readOnly);
	
	@Override
	@JsMethod
	public boolean isReadOnly();
	
	@Override
	@JsMethod
	public boolean isViewReady();
}
