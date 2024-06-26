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
package com.braintribe.devrock.artifactcontainer.plugin.preferences.maven;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;

public class MavenPreferencesDelegatingLabelProvider extends DelegatingStyledCellLabelProvider {
	ToolTipProvider toolTipProvider;
	
	public MavenPreferencesDelegatingLabelProvider(IStyledLabelProvider labelProvider) {
		super(labelProvider);
		if (labelProvider instanceof ToolTipProvider) 
			toolTipProvider = (ToolTipProvider) labelProvider;

	}

	@Override
	public String getToolTipText(Object element) {
		if (toolTipProvider != null) {
			String tt = toolTipProvider.getToolTipText( element);
			if (tt != null)
				return tt;
		}
		return super.getToolTipText(element);
	}

}
