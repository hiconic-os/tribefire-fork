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
package com.braintribe.gwt.customizationui.client;

import com.braintribe.gwt.ioc.client.DisposableBean;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.sencha.gxt.widget.core.client.form.TextField;

public class UserNameTextField extends TextField implements DisposableBean {
	
	private HandlerRegistration attachHandlerRegistration;
	
	public UserNameTextField() {
		setWidth(175);
		attachHandlerRegistration = this.addAttachHandler(event -> {
			if (event.isAttached())
				Scheduler.get().scheduleDeferred(UserNameTextField.this::focus);
		});
	}
	
	@Override
	public void disposeBean() {
		attachHandlerRegistration.removeHandler();
	}
}
