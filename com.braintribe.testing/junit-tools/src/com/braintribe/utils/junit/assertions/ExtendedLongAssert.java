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
package com.braintribe.utils.junit.assertions;

import static java.lang.Math.abs;

import org.fest.assertions.Formatting;
import org.fest.assertions.LongAssert;

/**
 * An extended version of {@link LongAssert}.
 *
 * @see #isEqualTo(long, long)
 *
 *
 */
public class ExtendedLongAssert extends LongAssert {

	protected ExtendedLongAssert(final long actual) {
		super(actual);
	}

	protected ExtendedLongAssert(final Long actual) {
		super(actual);
	}

	/**
	 * Asserts the actual value is equal to an expected value, allowing a specified delta.
	 */
	public LongAssert isEqualTo(final long expected, final long deltaValue) {
		if (abs(expected - this.actual) <= deltaValue) {
			return this;
		}
		failIfCustomMessageIsSet();
		throw failure(unexpectedNotEqual(this.actual, expected) + Formatting.format(" using delta:<%s>", Double.valueOf(deltaValue)));
	}

	static String unexpectedNotEqual(final Object actual, final Object expected) {
		return Formatting.format("expected:<%s> but was:<%s>", expected, actual);
	}

}
