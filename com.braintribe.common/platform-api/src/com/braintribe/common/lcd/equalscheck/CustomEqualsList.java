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
package com.braintribe.common.lcd.equalscheck;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * {@link List} extension of {@link CustomEqualsCollectionImpl}.
 *
 * @author michael.lafite
 *
 * @param <E>
 *            The type of the elements in this collection
 */
@SuppressFBWarnings({ "BC_BAD_CAST_TO_ABSTRACT_COLLECTION", "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE" })
public class CustomEqualsList<E> extends CustomEqualsCollectionImpl<E, List<E>> implements List<E> {

	public CustomEqualsList(final EqualsCheck<E> equalsCheck, final List<E> delegate) {
		super(equalsCheck, delegate, true);
	}

	@Override
	public int indexOf(final Object searchedElement) {
		return CustomEqualsCollectionTools.indexOf(this, searchedElement, getEqualsCheck());
	}

	@Override
	public int lastIndexOf(final Object searchedElement) {
		return CustomEqualsCollectionTools.lastIndexOf(this, searchedElement, getEqualsCheck());
	}

	// *** delegating methods ***

	@Override
	public boolean addAll(final int index, final Collection<? extends E> elementsToAdd) {
		if (elementsToAdd == null) {
			throw new NullPointerException("No elements to add specified!"); // SuppressPMDWarnings (NPE okay here)
		}
		return getDelegate().addAll(index, elementsToAdd);
	}

	@Override
	public E get(final int index) {
		return getDelegate().get(index);
	}

	@Override
	public E set(final int index, final E element) {
		return getDelegate().set(index, element);
	}

	@Override
	public void add(final int index, final E element) {
		getDelegate().add(index, element);
	}

	@Override
	public E remove(final int index) {
		return getDelegate().remove(index);
	}

	@Override
	public ListIterator<E> listIterator() {
		return getDelegate().listIterator();
	}

	@Override
	public ListIterator<E> listIterator(final int index) {
		return getDelegate().listIterator(index);
	}

	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		return getDelegate().subList(fromIndex, toIndex);
	}
}
