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
package com.braintribe.gwt.gxt.gxtresources.whitemask.client;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.dom.Mask.MaskDefaultAppearance.MaskStyle;
import com.sencha.gxt.core.client.dom.Mask.MessageTemplates;

public interface NotificationMessageTemplates extends MessageTemplates {
	
	@XTemplate("<div class=\"{style.box} notificationBar-hintTextBig\"><div class=\"{style.text}\">{message}</div></div>")
	@Override
    SafeHtml template(MaskStyle style, String message);
	
	@XTemplate("<div class=\"{style.box} loadingParent\"><div class='loadingLogoParent'><img class='loadingLogo' id='progressBarImage' src='{imageSource}'/></div>" +
            "<div id ='progressBarTitle' class='loadingTitle'>{title}</div>" +
            "<div class='loadingText'>{message}</div><div class='progress-grey'><div id='loadingProgressBarMask' class='loadingProgressBar progress-blue' style='height: 2px; width:" +
			"{initialValue}%;'></div></div></div>")
    SafeHtml progressTemplate(MaskStyle style, String message, String imageSource, String title, int initialValue);

}
