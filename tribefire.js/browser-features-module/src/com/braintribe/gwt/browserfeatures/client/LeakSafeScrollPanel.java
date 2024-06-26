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
package com.braintribe.gwt.browserfeatures.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class LeakSafeScrollPanel extends ScrollPanel {
	private boolean leakToolEnsured;
	
	public LeakSafeScrollPanel() {
		super();
	}

	public LeakSafeScrollPanel(Widget child) {
		super(child);
	}
	
	/**
	 * overriding this method is the only way to initialize the elements with the LeakToolImpl before
	 * the com.google.gwt.user.client.ui.ScrollImpl.ScrollImplTrident.initialize() is called.
	 * There is no other reason.
	 */
	@Override
	public void setAlwaysShowScrollBars(boolean alwaysShow) {
		ensureLeakTool();
		super.setAlwaysShowScrollBars(alwaysShow);
	}

	private void ensureLeakTool() {
		if (!leakToolEnsured) {
			LeakToolImpl leakToolImpl = LeakToolImpl.getInstance();
			leakToolImpl.initialize(getContainerElement());
			leakToolImpl.initialize(getElement());
			leakToolEnsured = true;
		}
	}
	
	/**
	 * This method must be called to break cycles. Afterwards the LeakSafeScrollPanel is no longer usable because the Workaround has this weakness. 
	 */
	public void cleanup() {
		LeakToolImpl leakToolImpl = LeakToolImpl.getInstance();
		leakToolImpl.cleanup(getContainerElement());
		leakToolImpl.cleanup(getElement());
	}
	
	public static class LeakToolImpl {
		private static LeakToolImpl instance = GWT.create(LeakToolImpl.class);
		
		public static LeakToolImpl getInstance() {
			return instance;
		}
		
		/**
		 * @param element - the Element
		 */
		public void initialize(Element element) {
			// do nothing cause real browser have no leak with ScrollPane
		}
		
		/**
		 * @param element - the Element
		 */
		public void cleanup(Element element) {
			// do nothing cause real browser have no leak with ScrollPane
		}
	}
	
}
