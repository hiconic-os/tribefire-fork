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

import com.braintribe.utils.lcd.Arguments;

/**
 * A {@link Pair} that doesn't permit <code>null</code> values.
 *
 * @author michael.lafite
 * @param <F>
 *            see {@link Pair}
 * @param <S>
 *            see {@link Pair}
 */
public class PairNonNull<F, S> extends Pair<F, S> {

	public PairNonNull(final F first, final S second) {
		super(first, second);
		Arguments.notNullWithNames("first", first, "second", second);
	}

	public PairNonNull(final Pair<F, S> otherPair) {
		super(otherPair);
		Arguments.notNullWithNames("otherPair.first", otherPair.first(), "otherPair.second", otherPair.second());
	}

}
