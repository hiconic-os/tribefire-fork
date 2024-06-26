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

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Experimental, use at your own risk.
 *
 * @author peter.gazdik
 */
public interface StreamBuilder<N> {

	StreamBuilder2<N> withNeighbors(Function<N, ? extends Collection<? extends N>> neighborFunction);

	StreamBuilder2<N> withNeighborStream(Function<N, ? extends Stream<? extends N>> neighborFunction);

	/**
	 * This is intended as the second step of the builder, after the neighbor function was provided via {@link StreamBuilder}
	 */
	public static interface StreamBuilder2<N> {

		StreamBuilder2<N> distinct();

		/** Stream elements using given traversal order. Default is post-order. */
		StreamBuilder2<N> withOrder(TraversalOrder order);

		Stream<N> please();

	}

	public static enum TraversalOrder {
		// might implement breadth-first in the future
		preOrder,
		postOrder;
	}

}
