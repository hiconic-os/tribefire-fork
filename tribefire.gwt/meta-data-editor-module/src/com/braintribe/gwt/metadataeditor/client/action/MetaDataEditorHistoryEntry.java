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
package com.braintribe.gwt.metadataeditor.client.action;

import com.braintribe.gwt.gme.tetherbar.client.TetherBar;
import com.braintribe.gwt.gme.tetherbar.client.TetherBarElement;
import com.braintribe.gwt.gmview.client.WorkWithEntityActionListener;
import com.braintribe.model.generic.path.ModelPath;
import com.google.gwt.user.client.ui.Widget;

public class MetaDataEditorHistoryEntry {
	
	public ModelPath modelPath;
	public Widget widget;
	public String tabType;
	public TetherBar tetherBar;
	public TetherBarElement tetherBarElement;
	public WorkWithEntityActionListener listener;
}
