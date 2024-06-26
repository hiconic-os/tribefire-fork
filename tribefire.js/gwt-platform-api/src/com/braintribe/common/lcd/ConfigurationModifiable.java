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
package com.braintribe.common.lcd;

/**
 * Interface for classes with a configuration that can be changed at runtime. The interface is used to observe these
 * configuration changes.
 *
 * @author michael.lafite
 */
public interface ConfigurationModifiable {

	/**
	 * Adds the passed <code>observer</code> to the set of observers.
	 *
	 * @param observer
	 *            the observer to add.
	 */
	void addConfigurationObserver(ConfigurationObserver observer);

	/**
	 * Removes the passed <code>observer</code> from the set of observers.
	 *
	 * @param observer
	 *            to observer to remove.
	 * @return whether or not the observer has been removed.
	 */
	boolean removeConfigurationObserver(ConfigurationObserver observer);

}
