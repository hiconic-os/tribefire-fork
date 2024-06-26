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
package com.braintribe.gwt.gxt.gxtresources.flattextfield.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.cell.core.client.form.FieldCell.FieldAppearanceOptions;
import com.sencha.gxt.cell.core.client.form.TextAreaInputCell.TextAreaCellOptions;
import com.sencha.gxt.core.client.util.Size;
import com.sencha.gxt.theme.base.client.field.TextAreaDefaultAppearance;

public class FlatTextAreaAppearance extends TextAreaDefaultAppearance {
	
	public interface FlatTextAreaResources extends TextAreaResources {

		@Override
		@Source({ "com/sencha/gxt/theme/base/client/field/ValueBaseField.gss", "com/sencha/gxt/theme/base/client/field/TextField.gss",
				"FlatTextField.gss", "com/sencha/gxt/theme/base/client/field/TextArea.gss" })
		TextAreaStyle css();
	}
	
	public FlatTextAreaAppearance() {
		super(GWT.<FlatTextAreaResources>create(FlatTextAreaResources.class));
	}
	
	/**
	 * Empty text session disabled due to bug.
	 */
	@Override
	public void render(SafeHtmlBuilder sb, String value, FieldAppearanceOptions options) {
		int width = options.getWidth();
		int height = options.getHeight();

		//boolean empty = false;

		String name = options.getName() != null ? " name='" + options.getName() + "' " : "";
		String disabled = options.isDisabled() ? " disabled=true" : "";
		String ro = options.isReadonly() ? " readonly" : "";
		String placeholder = options.getEmptyText() != null ? " placeholder='" + SafeHtmlUtils.htmlEscape(options.getEmptyText()) + "' " : "";

		/*if ((value == null || value.equals("")) && options.getEmptyText() != null) {
			if (GXT.isIE8() || GXT.isIE9()) {
				value = options.getEmptyText();
			}
			empty = true;
		}*/

		if (width == -1) {
			width = 150;
		}

		String inputStyles = "";
		String wrapStyles = "";

		Size adjusted = adjustTextAreaSize(width, height);

		if (width != -1) {
			wrapStyles += "width:" + width + "px;";
			width = adjusted.getWidth();
			inputStyles += "width:" + width + "px;";
		}

		if (height != -1) {
			height = adjusted.getHeight();
			inputStyles += "height: " + height + "px;";
		}

		TextAreaStyle style = getStyle();
		String cls = style.area() + " " + style.field();
		/*if (empty) {
			cls += " " + style.empty();
		}*/

		if (options instanceof TextAreaCellOptions) {
			TextAreaCellOptions opts = (TextAreaCellOptions) options;
			inputStyles += "resize:" + opts.getResizable().name().toLowerCase() + ";";
		}

		sb.appendHtmlConstant("<div style='" + wrapStyles + "' class='" + style.wrap() + "'>");
		sb.appendHtmlConstant(
				"<textarea " + name + disabled + " style='" + inputStyles + "' type='text' class='" + cls + "'" + ro + placeholder + ">");
		sb.append(SafeHtmlUtils.fromString(value));
		sb.appendHtmlConstant("</textarea></div>");
	}
	
	private native TextAreaStyle getStyle() /*-{
		return this.@com.sencha.gxt.theme.base.client.field.TextAreaDefaultAppearance::style;
	}-*/;

}
