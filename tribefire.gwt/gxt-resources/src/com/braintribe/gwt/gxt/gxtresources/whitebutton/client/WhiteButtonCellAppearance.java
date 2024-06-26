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
package com.braintribe.gwt.gxt.gxtresources.whitebutton.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.theme.base.client.button.ButtonCellDefaultAppearance;
import com.sencha.gxt.theme.base.client.frame.TableFrame;

public class WhiteButtonCellAppearance<C> extends ButtonCellDefaultAppearance<C> {
	
	public interface WhiteButtonCellStyle extends ButtonCellStyle {
		//NOP
	}
	
	public interface WhteButtonCellResources extends ButtonCellResources {
		
		@Override
		@Source({"com/sencha/gxt/theme/base/client/button/ButtonCell.gss", "WhiteButtonCell.gss"})
		WhiteButtonCellStyle style();
		
		@Override
		@Source("com/braintribe/gwt/gxt/gxtresources/images/arrow.png")
		ImageResource arrow();
		
	}
	
	public WhiteButtonCellAppearance() {
		super(GWT.<WhteButtonCellResources>create(WhteButtonCellResources.class), GWT.<ButtonCellTemplates> create(ButtonCellTemplates.class),
				new TableFrame(GWT.<WhiteButtonTableFrameResources> create(WhiteButtonTableFrameResources.class)));
	}
	
	/*
	 * Overriding this to export a new text style.
	 */
	@Override
	protected void writeValue(SafeHtmlBuilder builder, SafeHtml value, int width, int height) {
		SafeStylesBuilder sb = new SafeStylesBuilder();
		if (height > 0) {
			int adjustedHeight = height - getHeightOffset();
			sb.append(SafeStylesUtils.fromTrustedString("height:" + adjustedHeight + "px;"));
		}
		if (width > 0)
			sb.append(SafeStylesUtils.fromTrustedString("width:" + width + "px;"));
		//This is the only change
		builder.append(templates.textWithStyles(style.text() + " gmeButtonText", sb.toSafeStyles(), value));
	}
	
	/*
	 * Overriding this to export a new icon style. 
	 */
	@Override
	protected void writeIcon(SafeHtmlBuilder builder, ImageResource icon, int height) {
		SafeHtml iconHtml = AbstractImagePrototype.create(icon).getSafeHtml();
		if (height == -1) {
			builder.append(templates.icon(style.iconWrap() + " gmeButtonIcon", iconHtml));
		} else {
			int adjustedHeight = height - getHeightOffset();
			SafeStyles heightStyle = SafeStylesUtils.fromTrustedString("height:" + adjustedHeight + "px;");
			builder.append(templates.iconWithStyles(style.iconWrap() + " gmeButtonIcon", heightStyle, iconHtml));
		}
	}
	
	/*
	 * Overriding to export a new sytle.
	 */
	@Override
	public void onOver(XElement parent, boolean over) {
		super.onOver(parent, over);
		if (frame instanceof TableFrame) {
			XElement contentAreaElement = parent.child("." + ((TableFrame) frame).getResources().style().contentArea());
			if (contentAreaElement != null)
				contentAreaElement.setClassName("gmeButtonOver", over);
		}
	}
	
	private native int getHeightOffset() /*-{
		return this.@com.sencha.gxt.theme.base.client.button.ButtonCellDefaultAppearance::heightOffset;
	}-*/;

}
