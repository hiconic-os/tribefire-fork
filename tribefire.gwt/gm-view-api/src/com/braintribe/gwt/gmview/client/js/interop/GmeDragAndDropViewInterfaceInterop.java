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
import com.braintribe.gwt.gmview.client.GmeDragAndDropView;
import com.braintribe.gwt.gmview.client.ParentModelPathSupplier;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.workbench.TemplateBasedAction;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;

/**
 * Exposing the {@link GmeDragAndDropView} via JsInterop
 * @author michel.docouto
 *
 */
@SuppressWarnings("unusable-by-js")
public interface GmeDragAndDropViewInterfaceInterop extends GmeDragAndDropView {
	
	@Override
	@JsMethod
	int getMaxAmountOfFilesToUpload();
	
	@Override
	@JsMethod
	void handleDropFileList(FileList fileList);
	
	@Override
	@JsMethod
	PersistenceGmSession getGmSession();
	
	@Override
	@JsMethod
	WorkbenchActionContext<TemplateBasedAction> getDragAndDropWorkbenchActionContext();
	
	@Override
	@JsIgnore
	void prepareDropTargetWidget(Widget dropTarget, int indexForSelection);
	
	@Override
	@JsIgnore
	void prepareDropTarget(Element dropTarget, int indexForSelection);
	
	@Override
	@JsMethod
	WorkbenchActionContext<TemplateBasedAction> prepareWorkbenchActionContext();
	
	@Override
	@JsMethod
	ParentModelPathSupplier getParentModelPathSupplier(Object view);
	
	@Override
	@JsMethod
	boolean isUploadingFolder(FileList fileList);

}
