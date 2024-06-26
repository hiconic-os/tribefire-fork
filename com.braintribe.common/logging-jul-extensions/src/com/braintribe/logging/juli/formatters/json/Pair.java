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
package com.braintribe.logging.juli.formatters.json;

public class Pair<F, S> {
	private F firstOfTwo;
	private S secondOfTwo;

	public Pair(final F first, final S second) {
		this.firstOfTwo = first;
		this.secondOfTwo = second;
	}

	public Pair(final Pair<F, S> otherPair) {
		this(otherPair.first(), otherPair.second());
	}

	public F first() {
		return this.firstOfTwo;
	}

	public S second() {
		return this.secondOfTwo;
	}

	@Override
	public String toString() {
		return "(" + first() + "," + second() + ")";
	}

}
