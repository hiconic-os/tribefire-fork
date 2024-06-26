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
package com.braintribe.gwt.logging.ui.gxt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface LocalizedText extends Messages {
	
	public static LocalizedText INSTANCE = GWT.create(LocalizedText.class);
	
	String advanced();
	public String clear();
	public String close();
	public String details();
	String disableProfiling();
	String enableProfiling();
	String hideText();
	public String log();
	public String message();
	public String maximize();
	public String restore();
	String showError();
	String overview();
	public String packagingInfo();
	public String serverNotReachable();
	public String serverNotReachableDescription(String serverInfo);
	String showAsText();
	public String update();

}
