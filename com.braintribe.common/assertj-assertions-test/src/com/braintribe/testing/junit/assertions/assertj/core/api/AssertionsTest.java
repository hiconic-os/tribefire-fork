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
package com.braintribe.testing.junit.assertions.assertj.core.api;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThatIgnoringGenerics;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assert;
import org.assertj.core.data.Percentage;
import org.junit.Test;

import com.braintribe.utils.MapTools;

/**
 * Provides general {@link Assertions} tests. Test cases for specific {@link Assert}s must be put in a separate class, e.g. named
 * <code>[ExampleAssert]Test</code>.
 *
 * @author michael.lafite
 */
public class AssertionsTest {

	@Test
	public void testVariousAssertions() {
		assertThat((Object) null).isNull();
		assertThat("").isEmpty();
		assertThat((CharSequence) "").isEmpty();
		assertThat("xc").hasSameSizeAs("xy");
		assertThat(10).isCloseTo(12, Percentage.withPercentage(30));

		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		assertThat(list).hasSize(2);

		Map<Integer, String> map = MapTools.getParameterizedMap(Integer.class, String.class, 1, "a", 2, "b", 3, "c");
		assertThat(map).containsKeys(3, 2, 1);
		assertThat(map).containsValue("b");

		Collection<?> unspecifiedGenericTypeCollection = list;
		// does not compile
		// assertThat(unspecifiedGenericTypeCollection).containsExactly(1, 2);
		assertThatIgnoringGenerics(unspecifiedGenericTypeCollection).containsExactly(1, 2);

		List<?> unspecifiedGenericTypeList = list;
		// does not compile
		// assertThat(unspecifiedGenericTypeList).containsExactly(1, 2);
		assertThatIgnoringGenerics(unspecifiedGenericTypeList).containsExactly(1, 2);

		Map<?, ?> unspecifiedGenericTypeMap = map;
		// does not compile
		// assertThat(unspecifiedGenericTypeMap).containsEntry(1, "a");
		assertThatIgnoringGenerics(unspecifiedGenericTypeMap).containsEntry(1, "a");

		File file = new File("/a/b/c");
		assertThat(file).hasParent("/a/b");

		assertThat(Number.class).isAssignableFrom(Integer.class);

		assertThat(int.class).isPrimitive();
		assertThat(int.class).isNotEnum();
		assertThat(int.class).hasName("int");

		assertThat("abcde").containsAll("a", "b", "e");
		assertThat("baaaaab").containsNTimes("b", 2);
		assertThat("aaaaa").containsAtMostNTimes("a", 7);

		assertThat(new File("/a/b/c")).hasAncestor(new File("/a"));
		assertThat(new File("/a/b/../b/c")).hasSameCanonicalPathAs(new File("/a/b/c"));
	}

	@Test
	public void testExceptions() {
		assertThatThrownBy(this::dummyFailure).hasMessageStartingWith("x").hasNoCause();
	}

	private void dummyFailure() {
		throw new IllegalArgumentException("xyc");
	}

}
