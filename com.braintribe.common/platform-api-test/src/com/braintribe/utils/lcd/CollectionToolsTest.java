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
package com.braintribe.utils.lcd;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThatExecuting;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;

import com.braintribe.common.lcd.Empty;

/**
 * Tests for {@link CollectionTools}.
 *
 * @author michael.lafite
 */
public class CollectionToolsTest {

	@Test
	public void testGetFirstElement() throws Exception {
		assertThatExecuting(() -> CollectionTools.getFirstElement(null)).fails().withUncheckedException();
		assertThatExecuting(() -> CollectionTools.getFirstElement(Empty.set())).fails().withUncheckedException();

		final List<String> list = CollectionTools.getList("string1", "string2");
		assertThat(CollectionTools.getFirstElement(list)).isEqualTo("string1");
	}

	@Test
	public void testGetStringRepresentationForEnumeration() {
		List<String> source = new ArrayList<>();
		source.add("1");
		source.add("2");
		source.add("3");

		assertThat(CollectionTools.getStringRepresentation(Collections.enumeration(source), null, "[", "]", ",", false)).isEqualTo("['1','2','3']");
		assertThat(CollectionTools.getStringRepresentation((Enumeration<?>) null, null, "[", "]", ",", false)).isEqualTo("null");

	}

	@Test
	public void testSplitList() {
		List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
		List<List<Integer>> result = null;
		List<List<Integer>> expectedResult = null;

		result = CollectionTools.splitList(list, 3);
		expectedResult = convertTwoDimArrayToLists(new Integer[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6 } });
		assertThat(result).isEqualTo(expectedResult);

		result = CollectionTools.splitList(list, 1);
		expectedResult = convertTwoDimArrayToLists(new Integer[][] { { 0 }, { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 } });
		assertThat(result).isEqualTo(expectedResult);

		result = CollectionTools.splitList(list, 7);
		expectedResult = convertTwoDimArrayToLists(new Integer[][] { { 0, 1, 2, 3, 4, 5, 6 } });
		assertThat(result).isEqualTo(expectedResult);

		result = CollectionTools.splitList(list, 100);
		expectedResult = convertTwoDimArrayToLists(new Integer[][] { { 0, 1, 2, 3, 4, 5, 6 } });
		assertThat(result).isEqualTo(expectedResult);
	}

	/**
	 * Converts a two-dimensional array into a list of lists of these values.
	 *
	 * @param <T>
	 *            The Type of the elements in the array.
	 * @param array
	 *            The array containing the values.
	 * @return A list of lists containing the elements.
	 */
	private static <T> List<List<T>> convertTwoDimArrayToLists(T[][] array) {
		if (array == null) {
			return null;
		}

		List<List<T>> result = new ArrayList<>();

		for (T[] items : array) {
			List<T> list = Arrays.asList(items);
			result.add(list);
		}

		return result;
	}
}
