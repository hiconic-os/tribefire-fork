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
package com.braintribe.collections;

import java.util.Iterator;

public class GrowingIterable<E> implements Iterable<E> {
	private final Node firstNode = new Node(null);
	private Node lastNode = firstNode;

	@Override
	public Iterator<E> iterator() {
		return new NodeIterator(firstNode);
	}

	public void add(E element) {
		Node newNode = new Node(element);

		lastNode.next = newNode;
		lastNode = newNode;
	}

	private class Node {
		final E element;
		Node next;

		public Node(E element) {
			this.element = element;
		}
	}

	private class NodeIterator implements Iterator<E> {
		private GrowingIterable<E>.Node node;

		public NodeIterator(Node node) {
			this.node = node;
		}

		@Override
		public boolean hasNext() {
			return node.next != null;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new IllegalStateException("Iterator does not have further elements.");
			}

			node = node.next;
			return node.element;
		}
	}

}
