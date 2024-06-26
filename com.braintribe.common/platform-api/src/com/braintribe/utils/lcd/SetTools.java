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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class provides utility methods related to {@link Set}s.
 *
 * @author michael.lafite
 */
public class SetTools {

	protected SetTools() {
		// nothing to do
	}

	/**
	 * Returns a {@link java.util.SortedSet} containing all the elements of the passed <code>set</code>.
	 */
	public static <K> Set<K> getSortedSet(final Set<K> set) {
		return new TreeSet<>(set);
	}

	/**
	 * Adds the passed <code>elements</code> to the set. If the set already contains the element (or more precisely if an {@link Object#equals(Object)
	 * equal} element already exists), it is removed.
	 */
	public static <E> void addOrReplace(final Set<E> set, final Collection<E> elements) {
		if (!CollectionTools.isEmpty(elements)) {
			for (final E element : elements) {
				set.remove(element);
				set.add(element);
			}
		}
	}

	/**
	 * See {@link #addOrReplace(Set, Collection)}.
	 */
	public static <E> void addOrReplace(final Set<E> set, final E... elements) {
		if (!CommonTools.isEmpty(elements)) {
			addOrReplace(set, CommonTools.toList(elements));
		}
	}

	/**
	 * Alias for {@link #getSet(Object...)}.
	 */
	public static <E> Set<E> asSet(final E... elements) {
		return getSet(elements);
	}

	/**
	 * See {@link CommonTools#getSet(Object...)}.
	 */
	public static <E> Set<E> getSet(final E... elements) {
		return CommonTools.getSet(elements);
	}

	/** Creates a new set containing all the elements from <code>s1</code> which are not in <code>s2</code>. */
	public static <E> Set<E> subtract(final Set<? extends E> s1, final Set<? extends E> s2) {
		final Set<E> result = new HashSet<>();
		nullSafeAdd(result, s1);
		nullSafeRemove(result, s2);
		return result;
	}

	/** Creates a new set containing all the elements from <code>s1</code> and <code>s2</code>. */
	public static <E> Set<E> union(final Set<? extends E> s1, final Set<? extends E> s2) {
		final Set<E> result = new HashSet<>();
		nullSafeAdd(result, s1);
		nullSafeAdd(result, s2);
		return result;
	}

	public static <E> void nullSafeAdd(final Set<E> set, final Set<? extends E> setToAdd) {
		if (setToAdd != null) {
			set.addAll(setToAdd);
		}
	}

	public static void nullSafeRemove(final Set<?> set, final Set<?> setToRemove) {
		if (setToRemove != null) {
			set.removeAll(setToRemove);
		}
	}

	/**
	 * Merges the passed sets, if that's possible without exceeding the specified <code>idealSize</code>.
	 *
	 * @param sets
	 *            the sets to merge
	 * @param idealSize
	 *            the ideal set size. If a set already exceeds that size, it won't be modified, i.e. no split! Empty sets will be removed. If the size
	 *            is negative, nothing will be merged. This method is a heuristic approach, and therefore it does not provide the best set merging
	 *            solution.
	 * @param duplicatesCheckEnabled
	 *            duplicates (i.e. elements contained in both sets). Since this check can be expensive, it can be disabled
	 */
	public static <T> void mergeSetsIfPossible(final Collection<Set<T>> sets, final int idealSize, final boolean duplicatesCheckEnabled) {

		if (CollectionTools.isEmpty(sets)) {
			return;
		}

		final List<Set<T>> list = CollectionTools.getList(sets);
		Collections.sort(list, new Comparator<Set<T>>() {

			@Override
			public int compare(final Set<T> o1, final Set<T> o2) {
				final Integer size1 = o1.size();
				final Integer size2 = o2.size();
				return size1.compareTo(size2);
			}

		});

		final Iterator<Set<T>> iterator = list.iterator();

		Set<T> currentSet = iterator.next();
		while (iterator.hasNext()) {
			final Set<T> nextSet = iterator.next();

			final int nextSetSize = nextSet.size();
			final int maxMergedSetSize = nextSetSize + currentSet.size();

			if ((maxMergedSetSize <= idealSize)
					|| (duplicatesCheckEnabled && (maxMergedSetSize - countDuplicates(currentSet, nextSet) <= idealSize))) {
				currentSet.addAll(nextSet);
				iterator.remove();
			} else if (nextSet.size() > idealSize) {
				break;
			} else {
				currentSet = nextSet;
			}
		}
		sets.clear();
		sets.addAll(list);
	}

	/**
	 * Counts the duplicates in the passed sets (using {@link Set#contains(Object) contains check}).
	 */
	public static int countDuplicates(final Set<?> set1, final Set<?> set2) {
		int count = 0;
		if (!CollectionTools.isAnyEmpty(set1, set2)) {
			for (final Object element : set1) {
				if (set2.contains(element)) {
					count++;
				}
			}
		}
		return count;
	}
}
