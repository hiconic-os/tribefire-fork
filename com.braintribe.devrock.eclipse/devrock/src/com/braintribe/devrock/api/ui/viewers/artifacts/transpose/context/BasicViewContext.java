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
package com.braintribe.devrock.api.ui.viewers.artifacts.transpose.context;

import com.braintribe.devrock.eclipse.model.storage.ViewerContext;

public class BasicViewContext implements ViewContextBuilder {
	private boolean shortRanges;
	private boolean showGroups;
	private boolean showDependencies;
	private boolean showNatures;

	@Override
	public ViewContextBuilder shortRanges(boolean shortRanges) {
		this.shortRanges = shortRanges;
		return this;
	}

	@Override
	public ViewContextBuilder showGroups(boolean showGroups) {
		this.showGroups = showGroups;
		return this;
	}
	
	

	@Override
	public ViewContextBuilder showDependencies(boolean showDependencies) {
		this.showDependencies = showDependencies;
		return this;
	}
	
	
	@Override
	public ViewContextBuilder showNatures(boolean showNatures) {
		this.showNatures = showNatures;
		return this;
	}

	@Override
	public ViewerContext done() {
		ViewerContext vc = ViewerContext.T.create();
		vc.setShowGroupIds(showGroups);
		vc.setShowShortNotation(shortRanges);
		vc.setShowDependencies(showDependencies);
		vc.setShowNature(showNatures);
		return vc;
	}
	
	

	
	public static ViewContextBuilder build() {
		return new BasicViewContext();
	}

}
