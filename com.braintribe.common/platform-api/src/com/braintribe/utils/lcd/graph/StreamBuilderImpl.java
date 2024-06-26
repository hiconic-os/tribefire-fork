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
package com.braintribe.utils.lcd.graph;

import static com.braintribe.utils.lcd.CollectionTools2.newSet;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import com.braintribe.utils.lcd.graph.StreamBuilder.StreamBuilder2;

/**
 * @author peter.gazdik
 */
public class StreamBuilderImpl<N> implements StreamBuilder<N>, StreamBuilder2<N> {

	private final N root;

	private Function<N, ? extends Stream<? extends N>> neighborFunction;
	private boolean distinct;
	private TraversalOrder order = TraversalOrder.postOrder;

	public StreamBuilderImpl(N root) {
		this.root = root;
	}

	@Override
	public StreamBuilder2<N> withNeighbors(Function<N, ? extends Collection<? extends N>> neighborFunction) {
		this.neighborFunction = n -> toStream(neighborFunction.apply(n));
		return this;
	}

	private Stream<? extends N> toStream(Collection<? extends N> c) {
		return c == null ? Stream.empty() : c.stream();
	}

	@Override
	public StreamBuilder2<N> withNeighborStream(Function<N, ? extends Stream<? extends N>> neighborFunction) {
		this.neighborFunction = neighborFunction;
		return this;
	}

	@Override
	public StreamBuilder2<N> distinct() {
		this.distinct = true;
		return this;
	}

	@Override
	public StreamBuilder2<N> withOrder(TraversalOrder order) {
		this.order = order;
		return this;
	}

	@Override
	public Stream<N> please() {
		return modelsStream(root, distinct ? newSet() : null);
	}

	private Stream<N> modelsStream(N node, Set<Object> visited) {
		if (node == null) {
			return Stream.empty();
		}

		if (distinct && !visited.add(node)) {
			return Stream.empty();
		}

		Stream<N> result = Stream.of(node);

		Stream<? extends N> neighbors = neighborFunction.apply(node);
		if (neighbors == null) {
			return result;
		}

		Stream<N> neighborStream = neighbors.flatMap(n -> modelsStream(n, visited));
		if (order == TraversalOrder.preOrder) {
			return Stream.concat(result, neighborStream);
		} else {
			return Stream.concat(neighborStream, result);
		}
	}

}
