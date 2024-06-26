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
package com.braintribe.gwt.querymodeleditor.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface QueryModelEditorResources extends ClientBundle {
	public static final QueryModelEditorResources INSTANCE = GWT.create(QueryModelEditorResources.class);

	/************ Both Panels ************/

	@Source("com/braintribe/gwt/gxt/gxtresources/images/add_16x16.png")
	public ImageResource add();	
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/add_32x32.png")
	public ImageResource addBig();

	@Source("com/braintribe/gwt/gxt/gxtresources/images/Remove_32x32.png")
	public ImageResource removeBig();
	
	@Source("search_white_transparent.png")
	public ImageResource query();

	@Source("dropdown_icon.png")
	public ImageResource dropDown();

	@Source("arrow-right.png")
	public ImageResource arrowRight();

	@Source("arrow-left.png")
	public ImageResource arrowLeft();

	/******* QueryModelEditorPanel *******/

	@Source("Ascending_16x16.png")
	public ImageResource ascending();

	@Source("Descending_16x16.png")
	public ImageResource descending();

	/*** QueryModelEditorAdvancedPanel ***/

	@Source("feedback_info_valid.png")
	public ImageResource valid();

	@Source("feedback_info_invalid.png")
	public ImageResource invalid();
	
	@Source("actions-delete-small.png")
	public ImageResource delete();

	/********** QueryFormDialog **********/

	@Source("Cancel_16x16.png")
	public ImageResource cancel();
}
