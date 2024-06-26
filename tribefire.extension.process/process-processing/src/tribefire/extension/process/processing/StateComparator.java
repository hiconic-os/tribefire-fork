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

public class StateComparator implements Comparator<Object> {
	public static Comparator<Object> instance = new StateComparator();

	@Override
	public int compare(Object o1, Object o2) {
		if (o1 == o2)
			return 0;
		else if (o1 == null)
			return -1;
		else if (o2 == null)
			return 1;
		else {
			Class<? extends Object> class1 = o1.getClass();
			Class<? extends Object> class2 = o2.getClass();
			if (class1 == class2) {
				@SuppressWarnings("unchecked")
				Comparable<Object> comparable = (Comparable<Object>)o1;
				return comparable.compareTo(o2);
			}
			else {
				return class1.getName().compareTo(class2.getName());
			}
		}
	}
}
