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
package com.braintribe.coding;

/**
 * a simple wrapper for the test bean, which redirects identity behavior to the property "name" of the wrapped bean
 *
 * @author pit
 *
 */
public class BeanWrapper {

	private Bean bean;

	public BeanWrapper(final Bean bean) {
		this.bean = bean;
	}

	public Bean getBean() {
		return this.bean;
	}

	public void setBean(final Bean bean) {
		this.bean = bean;
	}

	@Override
	public int hashCode() {
		return this.bean.getName().hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof BeanWrapper)) {
			return false;
		}
		final BeanWrapper wrapper = (BeanWrapper) obj;
		return this.bean.getName().equals(wrapper.bean.getName());
	}
}
