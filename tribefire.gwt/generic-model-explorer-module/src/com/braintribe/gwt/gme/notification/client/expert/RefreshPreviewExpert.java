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
package com.braintribe.gwt.gme.notification.client.expert;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gwt.gmview.client.PreviewRefreshHandler;
import com.braintribe.model.processing.notification.api.CommandExpert;
import com.braintribe.model.uicommand.RefreshPreview;

public class RefreshPreviewExpert implements CommandExpert<RefreshPreview> {
	
	private List<PreviewRefreshHandler> previewPanels = new ArrayList<>();

	@Override
	public void handleCommand(RefreshPreview command) {
		previewPanels.forEach(p -> p.onPreviewRefresh(command));
	}

	public void addPreviewPanel(PreviewRefreshHandler bean) {
		previewPanels.add(bean);
	}

}
