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

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmSelectionListener;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.dom.client.Element;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Class needed for being able to export {@link GmContentView} via JsInterop.
 */
@JsType(name = "GmContentView", namespace = InteropConstants.VIEW_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class GmContentViewInterop implements GmContentView {

	protected GmContentView viewWidget;

	@JsConstructor
	public GmContentViewInterop() {
		super();
	}

	/**
	 * Setter for the {@link GmContentView} which is related to this view.
	 */
	public void setViewWidget(GmContentView viewWidget) {
		this.viewWidget = viewWidget;
	}

	@Override
	@JsMethod
	public void addSelectionListener(GmSelectionListener sl) {
		// NOP
	}

	@Override
	@JsMethod
	public void removeSelectionListener(GmSelectionListener sl) {
		// NOP
	}

	@Override
	@JsMethod
	public ModelPath getFirstSelectedItem() {
		return null;
	}

	@Override
	@JsMethod
	public int getFirstSelectedIndex() {
		return -1;
	}

	@Override
	@JsMethod
	public List<ModelPath> getCurrentSelection() {
		return null;
	}

	@Override
	@JsMethod
	public boolean isSelected(Object element) {
		return false;
	}

	@Override
	@JsMethod
	public void select(int index, boolean keepExisting) {
		// NOP
	}

	@Override
	@JsIgnore
	public boolean select(Element element, boolean keepExisting) {
		return false;
	}

	@Override
	@JsMethod
	public void selectRoot(int index, boolean keepExisting) {
		// NOP
	}

	@Override
	@JsMethod
	public void deselectAll() {
		// NOP
	}

	@Override
	@JsMethod
	public GmContentView getView() {
		return this;
	}

	@Override
	@JsMethod
	public void configureGmSession(PersistenceGmSession gmSession) {
		// NOP
	}

	@Override
	@JsMethod
	public PersistenceGmSession getGmSession() {
		return null;
	}

	@Override
	@JsMethod
	public void configureUseCase(String useCase) {
		// NOP
	}

	@Override
	@JsMethod
	public String getUseCase() {
		return null;
	}

	@Override
	@JsMethod
	public ModelPath getContentPath() {
		return null;
	}

	@Override
	@JsMethod
	public void setContent(ModelPath modelPath) {
		// NOP
	}

	@Override
	@JsMethod
	public Object getUxElement() {
		return null;
	}

	@Override
	@JsMethod
	public Object getUxWidget() {
		return null;
	}

	@Override
	@JsMethod
	public void detachUxElement() {
		// NOP
	}

	@Override
	@JsMethod
	public void setReadOnly(boolean readOnly) {
		// NOP
	}

	@Override
	@JsMethod
	public boolean isReadOnly() {
		return GmContentView.super.isReadOnly();
	}

	@Override
	@JsMethod
	public boolean isViewReady() {
		return GmContentView.super.isViewReady();
	}

	@Override
	@JsMethod
	public Future<Boolean> waitReply() {
		return GmContentView.super.waitReply();
	}
}
