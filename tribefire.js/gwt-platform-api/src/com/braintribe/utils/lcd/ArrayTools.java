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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class provides utility methods related to arrays.
 *
 * @author michael.lafite
 */
public class ArrayTools {

	protected ArrayTools() {
		// nothing to do
	}

	public static boolean isEmpty(final byte... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final boolean... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final char... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final double... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final float... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final int... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final long... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final short... array) {
		return CommonTools.isEmpty(array);
	}

	public static boolean isEmpty(final Object... array) {
		return CommonTools.isEmpty(array);
	}

	/**
	 * Returns <code>true</code> if at least one object is <code>null</code>.
	 */
	public static boolean isAnyNull(final Object... objects) {
		return CommonTools.isAnyNull(objects);
	}

	/**
	 * Returns <code>true</code> if all objects are <code>null</code>.
	 */
	public static boolean isAllNull(final Object... objects) {
		if (isEmpty(objects)) {
			throw new IllegalArgumentException("Cannot perform check. No objects specified!");
		}
		for (final Object object : objects) {
			if (object != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the number of null pointers.
	 *
	 * @see #getNonNullPointerCount(Object...)
	 */
	public static int getNullPointerCount(final Object... objects) {
		return CommonTools.getNullPointerCount(objects);
	}

	/**
	 * Returns the number of objects that are not <code>null</code>.
	 *
	 * @see #getNullPointerCount(Object...)
	 */
	public static int getNonNullPointerCount(final Object... objects) {
		return objects.length - getNullPointerCount(objects);
	}

	/**
	 * Returns the first reference from the passed <code>objects</code> array that is not a null pointer (or
	 * <code>null</code> if the <code>objects</code> array is <code>null</code>, empty or contains only null pointers).
	 */

	public static Object getFirstNonNullPointer(final Object... objects) {
		if (!isEmpty(objects)) {
			for (final Object object : objects) {
				if (object != null) {
					return object;
				}
			}
		}
		return null;
	}

	public static List<Byte> toList(final byte... array) {
		return CommonTools.toList(array);
	}

	public static List<Boolean> toList(final boolean... array) {
		return CommonTools.toList(array);
	}

	public static List<Character> toList(final char... array) {
		return CommonTools.toList(array);
	}

	public static List<Double> toList(final double... array) {
		return CommonTools.toList(array);
	}

	public static List<Float> toList(final float... array) {
		return CommonTools.toList(array);
	}

	public static List<Integer> toList(final int... array) {
		return CommonTools.toList(array);
	}

	public static List<Long> toList(final long... array) {
		return CommonTools.toList(array);
	}

	public static List<Short> toList(final short... array) {
		return CommonTools.toList(array);
	}

	public static <T> List<T> toList(final T... array) {
		return CommonTools.toList(array);
	}

	/**
	 * Returns an array containing all the elements of the passed collection. If no <code>collection</code> is passed,
	 * an empty array is returned.
	 *
	 * @see #toArray(Collection, Object[])
	 */
	public static Object[] toArray(final Collection<?> collection) {
		final Object[] array = new Object[NullSafe.size(collection)];
		return toArray(collection, array);
	}

	/**
	 * Inserts all the elements of the <code>collection</code> into the target array. The target array must have the
	 * same size as the collection! The collection may be <code>null</code> (in which case an empty array is returned).
	 *
	 * @return the passed <code>array</code> (for convenience it is also returned)
	 * @see #toArray(Collection)
	 */
	public static <E> E[] toArray(final Collection<? extends E> collection, final E[] targetArrayWithCorrectSize) {
		final int size = NullSafe.size(collection);
		if (targetArrayWithCorrectSize.length != size) {
			throw new IllegalArgumentException("Invalid target array size! " + CommonTools.getParametersString("collection size", size, "array size",
					targetArrayWithCorrectSize.length, "collection", collection, "array", Arrays.asList(targetArrayWithCorrectSize)));
		}
		if (!CommonTools.isEmpty(collection)) {
			collection.toArray(targetArrayWithCorrectSize);
		}
		return targetArrayWithCorrectSize;
	}
}
