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
package com.braintribe.gwt.aceeditor.listeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for manage and fire listeners.
 * 
 */
public abstract class AbstractListeners<L> {

	private final List<L> listeners = new ArrayList<L>();

	public boolean add(L listener) {
		return this.listeners.add(listener);
	}

	public boolean remove(L listener) {
		return this.listeners.remove(listener);
	}

	public void fireListeners() {
		if (this.listeners != null && !this.listeners.isEmpty())
			for (L listener : this.listeners)
				execute(listener);
	}

	protected abstract void execute(L listener);

}
