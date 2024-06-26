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
package com.braintribe.gwt.gme.workbench.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface WorkbenchResources extends ClientBundle {
	
	public static final WorkbenchResources INSTANCE = GWT.create(WorkbenchResources.class);
	
	@Source ("workbench.gss")
	public WorkbenchCss css();
	
	@Source ("Cancel_16x16.png")
	public ImageResource cancel();
	@Source ("Persist_16x16.png")
	public ImageResource folderUpload();
	@Source("Persist_16x16.png")
	public ImageResource importFolders();

}
