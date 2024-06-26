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

import com.braintribe.utils.lcd.CommonTools;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

/**
 * Simple helper class that holds two associated objects.
 *
 * @author michael.lafite
 *
 * @param <F>
 *            type of the first of the pair's two objects
 * @param <S>
 *            type of the second of the pair's two objects
 */
@JsType(namespace = "$tf.util")
public class Pair<F, S> {
	public final F first;
	public final S second;

	public Pair(F first, final S second) {
		this.first = first;
		this.second = second;
	}

	@JsIgnore
	public Pair(final Pair<F, S> otherPair) {
		this(otherPair.first(), otherPair.second());
	}
	
	public static <F,S> Pair<F,S> of(F first, S second) {
		return new Pair<F,S>(first, second);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Pair) {
			final Pair<?, ?> otherPair = (Pair<?, ?>) other;
			return CommonTools.equalsOrBothNull(getFirst(), otherPair.getFirst()) && CommonTools.equalsOrBothNull(getSecond(), otherPair.getSecond());

		}
		return false;
	}

	@Override
	public int hashCode() {
		return CommonTools.getHashCode(getFirst()) + CommonTools.getHashCode(getSecond());
	}

	public F getFirst() {
		return this.first;
	}

	public S getSecond() {
		return this.second;
	}

	@JsIgnore
	public F first() {
		return this.first;
	}

	@JsIgnore
	public S second() {
		return this.second;
	}

	@Override
	public String toString() {
		return "(" + first() + "," + second() + ")";
	}

}
