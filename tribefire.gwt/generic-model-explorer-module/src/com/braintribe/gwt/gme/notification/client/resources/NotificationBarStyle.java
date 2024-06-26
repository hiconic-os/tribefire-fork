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

import com.google.gwt.resources.client.CssResource;

public interface NotificationBarStyle extends CssResource {

	String up();

	String text();

	String down();

	String action();

	@ClassName("notificationBar-detailText")
	String detailText();
	
	@ClassName("notificationBar-boldText")
	String boldText();

	@ClassName("notificationBar-italicText")
	String italicText();

	@ClassName("notificationBar-underlineText")
	String underlineText();	

	@ClassName("notificationBar-strikeoutText")
	String strikeoutText();	
	
	public static interface Error extends NotificationBarStyle {

		@Override
		@ClassName("notificationBar-errorUp")
		String up();

		@Override
		@ClassName("notificationBar-errorText")
		String text();

		@Override
		@ClassName("notificationBar-errorDown")
		String down();

		@Override
		@ClassName("notificationBar-errorAction")
		String action();
	}

	public static interface Hint extends NotificationBarStyle {

		@Override
		@ClassName("notificationBar-hintUp")
		String up();

		@Override
		@ClassName("notificationBar-hintText")
		String text();

		@Override
		@ClassName("notificationBar-hintDown")
		String down();

		@Override
		@ClassName("notificationBar-hintAction")
		String action();
	}
	
	public static interface Warning extends NotificationBarStyle {

		@Override
		@ClassName("notificationBar-warningUp")
		String up();

		@Override
		@ClassName("notificationBar-warningText")
		String text();

		@Override
		@ClassName("notificationBar-warningDown")
		String down();

		@Override
		@ClassName("notificationBar-warningAction")
		String action();
		
	}

	public static interface Success extends NotificationBarStyle {

		@Override
		@ClassName("notificationBar-successUp")
		String up();

		@Override
		@ClassName("notificationBar-successText")
		String text();

		@Override
		@ClassName("notificationBar-successDown")
		String down();

		@Override
		@ClassName("notificationBar-successAction")
		String action();
		
	}

	public static interface ErrorBig extends Error {
		@Override
		@ClassName("notificationBar-errorTextBig")
		String text();
	}

	public static interface WarningBig extends Warning {
		@Override
		@ClassName("notificationBar-warningTextBig")
		String text();
	}

	public static interface HintBig extends Hint {
		@Override
		@ClassName("notificationBar-hintTextBig")
		String text();
	}
	
	public static interface MessageBig extends Hint {
		@Override
		@ClassName("notificationBar-hintTextBig")
		String text();
		
		@Override
		@ClassName("notificationBar-messageBig")
		String detailText();
	}

	public static interface SuccessBig extends Success {
		@Override
		@ClassName("notificationBar-successTextBig")
		String text();
	}

}
