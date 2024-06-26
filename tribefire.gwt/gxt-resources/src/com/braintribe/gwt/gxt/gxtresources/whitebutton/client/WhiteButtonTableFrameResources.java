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

import com.sencha.gxt.theme.base.client.button.ButtonTableFrameResources;
import com.sencha.gxt.theme.base.client.frame.TableFrame.TableFrameStyle;

public interface WhiteButtonTableFrameResources extends ButtonTableFrameResources {
	
	public interface WhiteButtonTableFrameStyle extends TableFrameStyle {
		String disabledStyle();
	}
	
	@Source({"com/sencha/gxt/theme/base/client/frame/TableFrame.gss", "com/sencha/gxt/theme/base/client/button/ButtonTableFrame.gss", "WhiteButtonTableFrame.gss"})
	@Override
	WhiteButtonTableFrameStyle style();

}
