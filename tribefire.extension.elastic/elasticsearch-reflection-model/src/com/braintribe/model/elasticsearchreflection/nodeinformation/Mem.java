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
package com.braintribe.model.elasticsearchreflection.nodeinformation;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Mem extends StandardIdentifiable {

	final EntityType<Mem> T = EntityTypes.T(Mem.class);

	String getDirectMemoryMax();
	void setDirectMemoryMax(String directMemoryMax);

	String getHeapInit();
	void setHeapInit(String heapInit);

	String getHeapMax();
	void setHeapMax(String heapMax);

	String getNonHeapInit();
	void setNonHeapInit(String nonHeapInit);

	String getNonHeapMax();
	void setNonHeapMax(String nonHeapMax);

}
