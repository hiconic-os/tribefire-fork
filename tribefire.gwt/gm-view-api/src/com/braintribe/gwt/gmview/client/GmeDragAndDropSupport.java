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

import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.fileapi.client.FileList;
import com.braintribe.model.workbench.TemplateBasedAction;

/**
 * Interface for the expert which handles file upload.
 * @author michel.docouto
 *
 */
public interface GmeDragAndDropSupport {
	
	public void handleDropFileList(FileList fileList, GmeDragAndDropView view);
	public Supplier<List<TemplateBasedAction>> getTemplateActionsSupplier();

}
