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
package com.braintribe.gwt.gme.notification.client.resources;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.braintribe.model.notification.Level;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public interface NotificationResources extends ClientBundle {

	public static final NotificationResources INSTANCE = GWT.create(NotificationResources.class);

	
	public static final String ERROR_COLOR = "#ffe0e0";
	public static final String WARNING_COLOR  = "#fff7d7";
	public static final String SUCCESS_COLOR = "#ebffd6";
	public static final String HINT_COLOR = "#fff7d7";
	
	public static LevelStyle INFO_STYLE = new LevelStyle(NotificationResources.HINT_COLOR, new Image(NotificationResources.INSTANCE.infosInfoBigCircle().getSafeUri()));
	public static LevelStyle SUCCESS_STYLE = new LevelStyle(NotificationResources.SUCCESS_COLOR, new Image(NotificationResources.INSTANCE.infosSuccessBigCircle().getSafeUri()));
	public static LevelStyle WARNING_STYLE = new LevelStyle(NotificationResources.WARNING_COLOR, new Image(NotificationResources.INSTANCE.infosAlertBigCircle().getSafeUri()));
	public static LevelStyle ERROR_STYLE = new LevelStyle(NotificationResources.ERROR_COLOR, new Image(NotificationResources.INSTANCE.infosErrorBigCircle().getSafeUri()));

	
	
	public static final Map<Level, NotificationBarStyle> LEVEL_STYLES = Collections.unmodifiableMap(new EnumMap<Level, NotificationBarStyle>(Level.class) {
		private static final long serialVersionUID = 1L;
		{
			put(Level.ERROR, NotificationResources.INSTANCE.notificationBarError());
			put(Level.WARNING, NotificationResources.INSTANCE.notificationBarWarning());
			put(Level.INFO, NotificationResources.INSTANCE.notificationBarHint());
			put(Level.SUCCESS, NotificationResources.INSTANCE.notificationBarSuccess());
			for (NotificationBarStyle style : values())
				style.ensureInjected();
		}
	});
	
	public static final Map<Level, NotificationBarStyle> LEVEL_STYLES_BIG = Collections.unmodifiableMap(new EnumMap<Level, NotificationBarStyle>(Level.class) {
		private static final long serialVersionUID = 1L;
		{
			put(Level.ERROR, NotificationResources.INSTANCE.notificationBarErrorBig());
			put(Level.WARNING, NotificationResources.INSTANCE.notificationBarWarningBig());
			put(Level.INFO, NotificationResources.INSTANCE.notificationBarHintBig());
			put(Level.SUCCESS, NotificationResources.INSTANCE.notificationBarSuccessBig());
			for (NotificationBarStyle style : values())
				style.ensureInjected();
		}
	});
	
	/* Image Resources */

	@Source("letter.orange.big.png")
	ImageResource notificationsBig();

	@Source("letter.orange.big.png")
	ImageResource notificationsOrange();
	
	@Source("Info-64.png")
	ImageResource infoBig();	

	/* Data Resources */

	@Source("Warning-16.png")
	DataResource attributeAlert2();

	@Source("Warning-16.png")
	DataResource infosAlertCircle();

	@Source("Warning-32.png")
	DataResource infosAlertBigCircle();

	@Source("Info-16.png")
	DataResource infosInfoCircle();

	@Source("Info-32.png")
	DataResource infosInfoBigCircle();
	
	@Source("Info-32.png")
	ImageResource infoBigCircle();

	@Source("Success-16.png")
	DataResource infosSuccessCircle();

	@Source("Success-32.png")
	DataResource infosSuccessBigCircle();

	@Source("Error-16.png")
	DataResource infosErrorCircle();

	@Source("Error-32.png")
	DataResource infosErrorBigCircle();

	@Source("ArrowCollapsed.png")
	DataResource arrowCollapsed();

	@Source("ArrowExpanded.png")
	DataResource arrowExpanded();

	@Source ("com/braintribe/gwt/gxt/gxtresources/images/checked.png")
	ImageResource checked();
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/unchecked.png")
	ImageResource unchecked();
	
	/* Styles */

	@Source("NotificationBar.gss")
	NotificationBarStyle.Error notificationBarError();

	@Source("NotificationBar.gss")
	NotificationBarStyle.Hint notificationBarHint();

	@Source("NotificationBar.gss")
	NotificationBarStyle.Warning notificationBarWarning();

	@Source("NotificationBar.gss")
	NotificationBarStyle.Success notificationBarSuccess();

	@Source("NotificationBar.gss")
	NotificationBarStyle.ErrorBig notificationBarErrorBig();

	@Source("NotificationBar.gss")
	NotificationBarStyle.HintBig notificationBarHintBig();
	
	@Source("NotificationBar.gss")
	NotificationBarStyle.MessageBig notificationBarMessageBig();

	@Source("NotificationBar.gss")
	NotificationBarStyle.WarningBig notificationBarWarningBig();

	@Source("NotificationBar.gss")
	NotificationBarStyle.SuccessBig notificationBarSuccessBig();

	@Source("NotificationIcon.gss")
	NotificationIconStyle notificationIconStyles();

	@Source("NotificationView.gss")
	NotificationViewStyle notificationViewStyles();
	
	
	public static class LevelStyle {
		String color;
		Image image;
		
		public LevelStyle(String color, Image image) {
			this.color = color;
			this.image = image;
		}
		
		public String getColor() {
			return color;
		}
		
		public Image getImage() {
			return image;
		}
		
	}


}
