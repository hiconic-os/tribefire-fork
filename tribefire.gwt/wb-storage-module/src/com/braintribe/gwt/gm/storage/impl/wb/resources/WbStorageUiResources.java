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
package com.braintribe.gwt.gm.storage.impl.wb.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface WbStorageUiResources extends ClientBundle {
	public static final WbStorageUiResources INSTANCE = GWT.create(WbStorageUiResources.class);

	@Source("Apply.png")
	public ImageResource apply();

	@Source("Cancel.png")
	public ImageResource cancel();

	@Source("Delete.png")
	public ImageResource delete();

	@Source("Blank.png")
	public ImageResource blank();

	@Source("Checked.png")
	public ImageResource checked();

	@Source("Unchecked.png")
	public ImageResource unchecked();
}
