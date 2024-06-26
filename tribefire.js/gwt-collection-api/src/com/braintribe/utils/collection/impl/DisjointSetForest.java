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
package com.braintribe.utils.collection.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.braintribe.utils.collection.api.DisjointSets;

/**
 * Just what it says.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure#Disjoint-set_forests">wikipedia</a>
 * 
 * @author peter.gazdik
 */
public class DisjointSetForest<E> implements DisjointSets<E> {

	private final Map<E, SetNode<E>> elementToItsSet;

	public DisjointSetForest() {
		this.elementToItsSet = new HashMap<E, SetNode<E>>();
	}

	public DisjointSetForest(Comparator<? super E> comparator) {
		this.elementToItsSet = new TreeMap<E, SetNode<E>>(comparator);
	}

	private static class SetNode<E> {
		SetNode<E> parent;
		int rank;
		E element;

		private SetNode(E element) {
			this.element = element;
			this.parent = this;
			this.rank = 0;
		}
	}

	@Override
	public boolean isSameSet(E e1, E e2) {
		return findSet(e1) == findSet(e2);
	}

	@Override
	public void union(E e1, E e2) {
		SetNode<E> set1 = findSetNode(e1);
		SetNode<E> set2 = findSetNode(e2);

		if (set1 == set2) {
			return;
		}

		if (set1.rank < set2.rank) {
			set1.parent = set2;

		} else if (set1.rank > set2.rank) {
			set2.parent = set1;

		} else {
			set2.parent = set1;
			set1.rank++;
		}
	}

	@Override
	public E findSet(E e) {
		return findSetNode(e).element;
	}

	public SetNode<E> findSetNode(E e) {
		SetNode<E> elementNode = elementToItsSet.get(e);
		if (elementNode == null) {
			elementNode = new SetNode<E>(e);
			elementToItsSet.put(e, elementNode);
		}

		return findSetNode(elementNode);
	}

	private SetNode<E> findSetNode(SetNode<E> set) {
		while (set.parent != set) {
			set = set.parent;
		}

		return set;
	}

}
