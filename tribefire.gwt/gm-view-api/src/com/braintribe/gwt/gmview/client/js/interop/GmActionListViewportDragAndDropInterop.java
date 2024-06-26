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

import com.braintribe.gwt.fileapi.client.FileList;
import com.braintribe.gwt.gmview.actionbar.client.GmViewActionProvider;
import com.braintribe.gwt.gmview.client.GmActionSupport;
import com.braintribe.gwt.gmview.client.GmListView;
import com.braintribe.gwt.gmview.client.GmViewport;
import com.braintribe.gwt.gmview.client.GmeDragAndDropView;
import com.braintribe.gwt.gmview.client.ParentModelPathSupplier;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.workbench.TemplateBasedAction;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Class needed for being able to export the following classes via JsInterop: {@link GmListView},
 * {@link GmActionSupport}, {@link GmViewActionProvider}, {@link GmViewport} and {@link GmeDragAndDropView}.
 */
@JsType(name = "GmActionListViewportDragAndDrop", namespace = InteropConstants.VIEW_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class GmActionListViewportDragAndDropInterop extends GmActionListViewportInterop implements GmeDragAndDropViewInterfaceInterop {
	
	@JsConstructor
	public GmActionListViewportDragAndDropInterop() {
		super();
	}

	@Override
	@JsMethod
	public int getMaxAmountOfFilesToUpload() {
		return 0;
	}

	@Override
	@JsMethod
	public void handleDropFileList(FileList fileList) {
		//NOP
	}

	@Override
	@JsMethod
	public WorkbenchActionContext<TemplateBasedAction> getDragAndDropWorkbenchActionContext() {
		return null;
	}

	@Override
	@JsIgnore
	public void prepareDropTargetWidget(Widget dropTarget, int indexForSelection) {
		//NOP
	}

	@Override
	@JsIgnore
	public void prepareDropTarget(Element dropTarget, int indexForSelection) {
		//NOP
	}

	@Override
	@JsMethod
	public WorkbenchActionContext<TemplateBasedAction> prepareWorkbenchActionContext() {
		return null;
	}

	@Override
	@JsMethod
	public ParentModelPathSupplier getParentModelPathSupplier(Object view) {
		return null;
	}

	@Override
	@JsMethod
	public boolean isUploadingFolder(FileList fileList) {
		return false;
	}

}
