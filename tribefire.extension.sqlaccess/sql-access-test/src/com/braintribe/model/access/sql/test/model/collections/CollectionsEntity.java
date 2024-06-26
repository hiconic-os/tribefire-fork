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
package com.braintribe.model.access.sql.test.model.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.access.sql.test.model.SqlAccessEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author peter.gazdik
 */
public interface CollectionsEntity extends SqlAccessEntity {

	EntityType<CollectionsEntity> T = EntityTypes.T(CollectionsEntity.class);

	String stringList = "stringList";
	String stringSet = "stringSet";
	String integerStringMap = "integerStringMap";
	String entityList = "entityList";
	String entitySet = "entitySet";
	String integerEntityMap = "integerEntityMap";
	String entityIntegerMap = "entityIntegerMap";

	List<String> getStringList();
	void setStringList(List<String> stringList);

	Set<String> getStringSet();
	void setStringSet(Set<String> stringSet);

	Map<Integer, String> getIntegerStringMap();
	void setIntegerStringMap(Map<Integer, String> integerStringMap);

	List<CollectionsEntity> getEntityList();
	void setEntityList(List<CollectionsEntity> entityList);

	Set<CollectionsEntity> getEntitySet();
	void setEntitySet(Set<CollectionsEntity> entitySet);

	Map<Integer, CollectionsEntity> getIntegerEntityMap();
	void setIntegerEntityMap(Map<Integer, CollectionsEntity> integerEntityMap);

	Map<CollectionsEntity, Integer> getEntityIntegerMap();
	void setEntityIntegerMap(Map<CollectionsEntity, Integer> entityIntegerMap);

}
