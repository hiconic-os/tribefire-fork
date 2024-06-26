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

import java.util.Collections;
import java.util.Iterator;

/**
 * {@link Iterable} which enables iteration over multiple {@linkplain Iterable}s.
 *
 * @author peter.gazdik
 */
public class CompositeIterable<E> implements Iterable<E> {
	private final Iterable<? extends Iterable<? extends E>> iterables;

	public CompositeIterable(Iterable<? extends Iterable<? extends E>> iterables) {
		this.iterables = iterables;
	}

	@Override
	public Iterator<E> iterator() {
		return new CompositeIterator();
	}

	private class CompositeIterator implements Iterator<E> {

		private final Iterator<? extends Iterable<? extends E>> iteratorsIt = iterables.iterator();

		private Iterator<? extends E> currentIt = Collections.emptyIterator();
		private Iterator<? extends E> nextIt = Collections.emptyIterator();

		public CompositeIterator() {
			moveNextIt();
		}

		@Override
		public boolean hasNext() {
			return currentIt.hasNext() || nextIt.hasNext();
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new IllegalStateException("Iterator has already reached the end.");
			}

			if (currentIt.hasNext()) {
				return currentIt.next();
			}

			currentIt = nextIt;
			moveNextIt();

			return currentIt.next();
		}

		@Override
		public void remove() {
			currentIt.remove();
		}

		private void moveNextIt() {
			while (iteratorsIt.hasNext()) {
				nextIt = iteratorsIt.next().iterator();
				if (nextIt.hasNext()) {
					return;
				}
			}
		}

	}
}
