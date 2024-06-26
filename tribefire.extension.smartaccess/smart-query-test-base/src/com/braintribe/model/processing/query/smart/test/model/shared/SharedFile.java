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
package com.braintribe.model.processing.query.smart.test.model.shared;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartGenericEntity;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup;

/**
 * Default partition -> {@link SmartMappingSetup#accessIdB}
 */
public interface SharedFile extends SharedEntity, SmartGenericEntity {
	
	EntityType<SharedFile> T = EntityTypes.T(SharedFile.class);

	String getName();
	void setName(String name);

	SharedFile getParent();
	void setParent(SharedFile parent);

	SharedFileDescriptor getFileDescriptor();
	void setFileDescriptor(SharedFileDescriptor fileDescriptor);

	Set<SharedFile> getChildSet();
	void setChildSet(Set<SharedFile> childSet);

	List<SharedFile> getChildList();
	void setChildList(List<SharedFile> childList);

	Map<SharedFile, SharedFile> getChildMap();
	void setChildMap(Map<SharedFile, SharedFile> childMap);

}
