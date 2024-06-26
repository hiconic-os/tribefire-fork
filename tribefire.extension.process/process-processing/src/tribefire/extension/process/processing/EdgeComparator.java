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
package tribefire.extension.process.processing;

import java.util.Comparator;

import tribefire.extension.process.model.deployment.Edge;

public class EdgeComparator implements Comparator<Edge> {
	public static Comparator<Edge> instance = new EdgeComparator();

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(Edge e1, Edge e2) {
		if (e1 == e2)
			return 0;
		else 
			return e1.<Comparable> getId().compareTo(e2.getId());
	}
	
}
