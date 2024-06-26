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
package com.braintribe.cc.lcd;

public class HashComparatorImmutableEqProxy<T> implements EqProxy<T> {

	private HashingComparator<? super T> comparator;
	private T subject;
	private int hash;

	public HashComparatorImmutableEqProxy(HashingComparator<? super T> comparator, T subject) {
		super();
		this.comparator = comparator;
		this.subject = subject;
		this.hash = comparator.computeHash(subject);
	}

	@Override
	public T get() {
		return subject;
	}

	@Override
	public int hashCode() {
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return comparator.compare(subject, null);
		
		return comparator.compare(subject, ((EqProxy<T>)obj).get());
	}
}
