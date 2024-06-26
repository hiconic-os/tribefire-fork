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

import static com.braintribe.utils.lcd.CollectionTools2.asList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author peter.gazdik
 */
public abstract class Tuple implements Iterable<Object> {

	public static <A, B> Tuple2<A, B> of(A a, B b) {
		return new Tuple2<>(a, b);
	}

	public static <A, B, C> Tuple3<A, B, C> of(A a, B b, C c) {
		return new Tuple3<>(a, b, c);
	}

	public static <A, B, C, D> Tuple4<A, B, C, D> of(A a, B b, C c, D d) {
		return new Tuple4<>(a, b, c, d);
	}

	public static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
		return new Tuple5<>(a, b, c, d, e);
	}

	private final Object[] elements;

	protected Tuple(Object... elements) {
		Objects.requireNonNull(elements);
		this.elements = elements;
	}

	public Object get(int index) {
		return elements[index];
	}

	public int size() {
		return elements.length;
	}

	@Override
	public Iterator<Object> iterator() {
		return toList().iterator();
	}

	public List<Object> toList() {
		return asList(elements);
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || //
				(getClass() == obj.getClass() && Arrays.deepEquals(elements, ((Tuple) obj).elements));
	}

	@Override
	public int hashCode() {
		return 31 * Arrays.deepHashCode(elements) + getClass().hashCode();
	}

	// @formatter:off
	public static class Tuple2<A, B> extends Tuple {
		private Tuple2(Object... elements) { super(elements); }
		public A val0() {	return (A) get(0); }
		public B val1() {	return (B) get(1); }
	}

	public static class Tuple3<A, B, C> extends Tuple2<A, B> {
		private Tuple3(Object... elements) { super(elements); }
		public C val2() {	return (C) get(2); }
	}

	public static class Tuple4<A, B, C, D> extends Tuple3<A, B, C> {
		private Tuple4(Object... elements) { super(elements); }
		public D val3() {	return (D) get(3);	}
	}

	public static class Tuple5<A, B, C, D, E> extends Tuple4<A, B, C, D> {
		private Tuple5(Object... elements) { super(elements); }
		public E val4() {	return (E) get(4); }
	}
	// @formatter:on

}
