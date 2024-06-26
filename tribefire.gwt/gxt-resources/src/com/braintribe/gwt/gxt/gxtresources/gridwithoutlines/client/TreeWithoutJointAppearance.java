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
package com.braintribe.gwt.gxt.gxtresources.gridwithoutlines.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.theme.base.client.tree.TreeBaseAppearance;
import com.sencha.gxt.theme.blue.client.tree.BlueTreeAppearance.BlueTreeResources;

public class TreeWithoutJointAppearance extends TreeBaseAppearance {
	
	public interface TreeWithoutJointStyle extends TreeBaseStyle {
		String treeWithoutJoint();
    }
	
	public interface TreeWithoutJointResources extends BlueTreeResources {
		@Override
		@Source({"com/sencha/gxt/theme/base/client/tree/Tree.gss", "com/sencha/gxt/theme/blue/client/tree/TreeDefault.gss", "TreeWithoutJoint.gss"})
		TreeWithoutJointStyle style();
	}
	
	public TreeWithoutJointAppearance() {
		super((TreeWithoutJointResources) GWT.create(TreeWithoutJointResources.class));
	}
	
	@Override
	public void render(SafeHtmlBuilder sb) {
		sb.appendHtmlConstant("<div class=" + getStyle().tree() + " style=\"position: relative;\">"
				+ "<table cellpadding=0 cellspacing=0 width=100%><tr><td class='gxtReset'></td></tr></table>" + "</div>");
	}
	
	private native TreeBaseStyle getStyle() /*-{
		return this.@com.sencha.gxt.theme.base.client.tree.TreeBaseAppearance::style;
	}-*/;

}
