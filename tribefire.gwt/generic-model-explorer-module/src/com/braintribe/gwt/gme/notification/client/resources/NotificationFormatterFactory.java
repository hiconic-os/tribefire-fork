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


import com.braintribe.gwt.gme.notification.client.MessageModel;
import com.braintribe.gwt.gme.notification.client.NotificationViewModel;
import com.braintribe.gwt.gmview.util.client.GMEUtil;
import com.braintribe.model.notification.NotificationEventSource;
import com.braintribe.model.processing.notification.api.NotificationEventSourceExpert;
import com.sencha.gxt.core.client.XTemplates.Formatter;

public final class NotificationFormatterFactory {

	/* ----- NotificationViewModel Formatter ----- */

	public static Formatter<NotificationViewModel> getExpanded(final NotificationViewStyle style) {
		return new Formatter<NotificationViewModel>() {
			@Override
			public String format(NotificationViewModel model) {
				return model.isExpanded() ? style.expanded() : style.collapsed();
			}
		};
	}

	public static Formatter<NotificationViewModel> getSource(final NotificationEventSourceExpert<NotificationEventSource> expert) {
		return new Formatter<NotificationViewModel>() {
			@Override
			public String format(NotificationViewModel model) {
				return expert.render(model.getEntry().getEventSource());
			}
		};
	}

	public static Formatter<NotificationViewModel> getUnread(final NotificationViewStyle style) {
		return new Formatter<NotificationViewModel>() {
			@Override
			public String format(NotificationViewModel model) {
				return model.getEntry().getWasReadAt() == null ? style.unread() : "";
			}
		};
	}

	/* ----- Notification Formatter ----- */

	public static Formatter<MessageModel> getLevel() {
		return new Formatter<MessageModel>() {
			@Override
			public String format(MessageModel data) {
				if (data.getLevel() != null)
					return data.getLevel().name();
				
				return null;
			}
		};
	}

	public static Formatter<MessageModel> getLevel(final NotificationViewStyle style) {
		return new Formatter<MessageModel>() {
			@Override
			public String format(MessageModel data) {
				if (data.getLevel() != null)
					switch (data.getLevel()) {
					case ERROR:
						return style.levelError();
					case INFO:
						return style.levelInfo();
					case WARNING:
						return style.levelWarning();
					case SUCCESS:
						return style.levelSuccess();
					}
				return null;
			}
		};
	}

	public static Formatter<MessageModel> getCommand() {
		return new Formatter<MessageModel>() {
			@Override
			public String format(MessageModel data) {
				if (data.getCommand() != null)
					return data.getCommand().getName();
				return null;
			}
		};
	}

	public static Formatter<MessageModel> getMessage() {
		return new Formatter<MessageModel>() {
			@Override
			public String format(MessageModel data) {
				return GMEUtil.htmlReEscape(data.getMessage());
			}
		};
	}

}
