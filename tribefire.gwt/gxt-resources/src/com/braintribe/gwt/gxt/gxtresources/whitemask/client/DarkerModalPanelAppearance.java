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

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.ModalPanel.ModalPanelDefaultAppearance;

public class DarkerModalPanelAppearance extends ModalPanelDefaultAppearance {
	
	public interface DarkerModalPanelResources extends ModalPanelResources {
        @Override
        @Source({"com/sencha/gxt/widget/core/client/ModalPanel.gss", "DarkerModalPanel.gss"})
        ModalPanelStyle css();
    } 
	
	public DarkerModalPanelAppearance() {
		super(GWT.create(DarkerModalPanelResources.class));
	}

}
