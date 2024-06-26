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
package com.braintribe.utils;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.junit.Test;

@SuppressWarnings("unused")
public class ReflectionToolsTest {

	@Test
	public void testGenericsParameterResolution() {
		Type type = ReflectionTools.getGenericsParameter(E.class, A.class, "T");

		assertThat(type).isSameAs(String.class);
	}

	@Test
	public void testUnconcreteGenericsParameterResolution() {
		Type type = ReflectionTools.getGenericsParameter(P.class, O.class, "T");

		assertThat(type).isInstanceOf(TypeVariable.class);
		assertThat(((TypeVariable<?>) type).getName()).isEqualTo("S");
	}

	@Test(expected = IllegalStateException.class)
	public void testGenericsParameterResolutionWithoutGivenInheritance() {
		Type type = ReflectionTools.getGenericsParameter(P.class, A.class, "T");
	}

	// @formatter:off
	interface O<T> { /* empty */	}
	interface P<S> extends O<S> { /* empty */ }
	interface A<T> { /* empty */ }
	interface Z<T> { /* empty */ }
	interface B<Y, X extends CharSequence> extends A<X>, Z<Y> { /* empty */ }
	interface B1<X1 extends CharSequence> extends B<Integer, X1> { /* empty */ }
	interface C { /* empty */ }
	interface D { /* empty */ }
	interface E extends B1<String>, C, D { /* empty */ }
	// @formatter:on

}
