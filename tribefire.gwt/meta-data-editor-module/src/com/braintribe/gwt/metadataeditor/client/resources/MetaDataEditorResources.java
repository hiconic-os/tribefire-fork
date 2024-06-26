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
package com.braintribe.gwt.metadataeditor.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.theme.base.client.listview.ListViewDefaultAppearance.ListViewDefaultStyle;

public interface MetaDataEditorResources extends ClientBundle {

	public static final MetaDataEditorResources INSTANCE = GWT.create(MetaDataEditorResources.class);
	public static final boolean INJECTED = INSTANCE.listViewDefaultStyle().ensureInjected() && INSTANCE.constellationCss().ensureInjected();

	@Source("MetaDataEditorListView.gss")
	public ListViewDefaultStyle listViewDefaultStyle();

	@Source("MetaDataEditorConstellation.gss")
	public ConstellationCss constellationCss();
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/MetaData_16x16.png")
	public ImageResource metadata();
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/MetaData_32x32.png")
	public ImageResource metadataBig();	
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/New_16x16.png")
	public ImageResource newInstance();		
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/New_32x32.png")
	public ImageResource newInstanceBig();	
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/next2.png")
	public ImageResource next2();	

	@Source ("com/braintribe/gwt/gxt/gxtresources/images/previous2.png")
	public ImageResource previous2();	
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/clear.gif")
	public ImageResource clear();
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/EntityTypes_16x16.png")
	public ImageResource entityTypes16();
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/EntityTypes_32x32.png")
	public ImageResource entityTypes32();	
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/EnumTypes_16x16.png")
	public ImageResource enumTypes16();
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/EnumTypes_32x32.png")
	public ImageResource enumTypes32();

	@Source ("com/braintribe/gwt/gxt/gxtresources/images/Remove_16x16.png")
	public ImageResource remove16();
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/Remove_32x32.png")
	public ImageResource remove32();	
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/Change_16x16.png")
	public ImageResource change16();
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/Change_32x32.png")
	public ImageResource change32();	
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/Search_24x24.png")
	public ImageResource search24();	
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/checkbox_gray.png")
	public ImageResource checkGray();	
	
	
}
