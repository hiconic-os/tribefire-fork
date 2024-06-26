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

import java.util.function.Predicate;


/**
 * A {@link Predicate} that filters objects based on a {@link #setCheck(GenericCheck) delegate} {@link GenericCheck}.
 *
 * @author michael.lafite
 */
public class GenericCheckBasedFilter<T> implements Predicate<T> {

	private GenericCheck<T> check;

	public GenericCheckBasedFilter() {
		// nothing to do
	}

	public GenericCheckBasedFilter(final GenericCheck<T> check) {
		this.check = check;
	}

	public GenericCheck<T> getCheck() {
		return this.check;
	}

	public void setCheck(final GenericCheck<T> check) {
		this.check = check;
	}

	@Override
	public boolean test(final T obj) {
		return getCheck().check(obj);
	}
}
