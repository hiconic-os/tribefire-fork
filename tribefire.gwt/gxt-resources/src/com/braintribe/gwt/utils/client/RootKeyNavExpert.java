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
package com.braintribe.gwt.utils.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.NativeEvent;

/**
 * Expert for adding listeners for shortcuts globally.
 * @author michel.docouto
 *
 */
public class RootKeyNavExpert {
	
	private static List<RootKeyNavListener> listeners;
	
	protected RootKeyNavExpert() {
		//Not instantiable
	}
	
	public static void addRootKeyNavListener(RootKeyNavListener listener) {
		if (listener == null)
			return;
		
		if (listeners == null)
			listeners = new ArrayList<>();
		
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public static void removeRootKeyNavListener(RootKeyNavListener listener) {
		if (listener == null && listeners == null)
			return;
		
		if (listeners.contains(listener))
			listeners.remove(listener);
		
		if (listeners.isEmpty())
			listeners = null;
	}
	
	public static List<RootKeyNavListener> getListeners() {
		return listeners;
	}
	
	public interface RootKeyNavListener {
		public void onRootKeyPress(NativeEvent evt);
	}

}
