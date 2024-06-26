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
package com.braintribe.gwt.gme.notification.client.adapter;

import java.util.HashSet;
import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.tracking.ManipulationListener;

/**
 * A type parameterized version of the DelegatingManipulationListener.
 * 
 */
public class ManipulationAdapterExpert<M extends Manipulation> implements ManipulationListener {

	private static final Logger logger = Logger.getLogger(ManipulationAdapterExpert.class);
	private Set<ManipulationAdapterListener<M>> delegates = new HashSet<>();

	@Override
	public void noticeManipulation(Manipulation manipulation) {
		M m = (M) manipulation;

		Set<ManipulationAdapterListener<M>> delegates = new HashSet<>(this.delegates);
		for (ManipulationAdapterListener<M> listenerDelegate : delegates) {
			try {
				listenerDelegate.noticeManipulation(m);
			} catch (Exception e) {
				logger.error("error while delegating manipulation", e);
				e.printStackTrace();
			}
		}
	}

	public boolean addDelegate(ManipulationAdapterListener<M> listener) {
		return delegates.add(listener);
	}

	public boolean removeDelegate(ManipulationAdapterListener<M> listener) {
		return delegates.remove(listener);
	}

	public Set<ManipulationAdapterListener<M>> getDelegates() {
		return delegates;
	}

	public void clearDelegates() {
		delegates.clear();
	}

}
