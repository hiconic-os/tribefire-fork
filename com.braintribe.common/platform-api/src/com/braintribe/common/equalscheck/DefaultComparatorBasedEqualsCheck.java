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
package com.braintribe.common.equalscheck;

import java.util.Comparator;

import com.braintribe.common.lcd.equalscheck.ComparatorBasedEqualsCheck;

/**
 * Default implementation of {@link ComparatorBasedEqualsCheck}.
 *
 * @author michael.lafite
 *
 * @param <T>
 *            the type of the objects to check for equality (and also the type of the objects that may be compared by the comparator this check is
 *            based on)
 */
public class DefaultComparatorBasedEqualsCheck<T> implements ComparatorBasedEqualsCheck<T> {

	private final Comparator<T> delegate;

	private final Class<T> comparedType;

	public DefaultComparatorBasedEqualsCheck(final Comparator<T> delegate, final Class<T> comparedType) {
		this.delegate = delegate;
		this.comparedType = comparedType;
	}

	/**
	 * The comparator passed to the {@link DefaultComparatorBasedEqualsCheck#DefaultComparatorBasedEqualsCheck(Comparator, Class) constructor}.
	 */
	public Comparator<T> getDelegate() {
		return this.delegate;
	}

	/**
	 * Returns <code>true</code>, if the result of {@link #compare(Object, Object)} is <code>0</code>, otherwise <code>false</code>. The method also
	 * returns <code>false</code>, if <code>object2</code> is not an instance of <code>T</code>.
	 */
	@Override
	public boolean equals(final T object1, final Object object2) {
		if (object1 == null) {
			return (object2 == null);
		}

		if (!this.comparedType.isInstance(object2)) {
			return false;
		}
		return compare(object1, this.comparedType.cast(object2)) == 0;
	}

	/**
	 * Delegates to the {@link #getDelegate() delegate comparator}.
	 */

	@Override
	public int compare(final T object1, final T object2) {
		return getDelegate().compare(object1, object2);
	}
}
