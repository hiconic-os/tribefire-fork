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
package com.braintribe.gwt.genericmodelgxtsupport.client.resources;

import com.braintribe.gwt.browserfeatures.client.GWTMetaPropertiesUtil;
import com.braintribe.gwt.browserfeatures.client.GWTMetaPropertiesUtil.IconSet;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface GMGxtSupportResources extends ClientBundle {
	
	public static final GMGxtSupportResources INSTANCE = (GMGxtSupportResources) (GWTMetaPropertiesUtil.getIconSet().equals(IconSet.coloured) ? GWT.create(ColouredGMGxtSupportResources.class) :
		GWT.create(GMGxtSupportResources.class));
	
	@Source ("New_32x32.png")
	public ImageResource add();
	public ImageResource addLocale();
	@Source("OK_32x32.png")
	public ImageResource apply();
	public ImageResource bar_blue_bl();
	public ImageResource bar_blue_br();
	public ImageResource bar_blue_tl();
	public ImageResource bar_blue_tr();
	public ImageResource bar_brightness();
	public ImageResource bar_green_bl();
	public ImageResource bar_green_br();
	public ImageResource bar_green_tl();
	public ImageResource bar_green_tr();
	public ImageResource bar_hue();
	public ImageResource bar_red_bl();
	public ImageResource bar_red_br();
	public ImageResource bar_red_tl();
	public ImageResource bar_red_tr();
	public ImageResource bar_saturation();
	public ImageResource bar_white();
	public ImageResource calendar();
	public ImageResource color();
	@Source ("Delete_32x32.png")
	public ImageResource delete();
	public ImageResource dropDown();
	@Source ("Hide_32x32.png")
	ImageResource hide();
	public ImageResource map_blue_max();
	public ImageResource map_blue_min();
	public ImageResource map_brightness();
	public ImageResource map_hue();
	public ImageResource map_red_max();
	public ImageResource map_red_min();
	public ImageResource map_green_max();
	public ImageResource map_green_min();
	public ImageResource map_saturation();
	public ImageResource map_saturation_overlay();
	public ImageResource map_white();
	public ImageResource mappoint();
	@Source("Multiline_16x16.png")
	ImageResource multiLine();
	public ImageResource rangearrows();
	@Source("Remove_32x32.png")
	public ImageResource remove();
	@Source ("Save_32x32.png")
	public ImageResource save();
	@Source ("Show_32x32.png")
	ImageResource show();

}
