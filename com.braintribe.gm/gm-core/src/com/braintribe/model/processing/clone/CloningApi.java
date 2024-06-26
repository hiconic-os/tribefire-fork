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
package com.braintribe.model.processing.clone;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.collection.LinearCollectionBase;
import com.braintribe.model.generic.collection.ListBase;
import com.braintribe.model.generic.collection.MapBase;
import com.braintribe.model.generic.collection.SetBase;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.LinearCollectionType;
import com.braintribe.model.generic.reflection.ListType;
import com.braintribe.model.generic.reflection.MapType;
import com.braintribe.model.generic.reflection.SetType;

public interface CloningApi {

	<K, V> MapBase<K, V> cloneMap(MapBase<K, V> map);

	<K, V> MapBase<K, V> cloneMap(Map<K, V> map, MapType mapType);

	<K, V> MapBase<K, V> cloneMap(Map<K, V> map);

	<T> SetBase<T> cloneSet(SetBase<T> set);

	<T> SetBase<T> cloneSet(Set<T> set, SetType setType);

	<T> SetBase<T> cloneSet(Set<T> set);

	<T> ListBase<T> cloneList(ListBase<T> list);

	<T> ListBase<T> cloneList(List<T> list, ListType listType);

	<T> ListBase<T> cloneList(List<T> list);

	<T> LinearCollectionBase<T> cloneCollection(LinearCollectionBase<T> collection);

	<T> LinearCollectionBase<T> cloneCollection(Collection<T> collection, LinearCollectionType collectionType);

	<T> LinearCollectionBase<T> cloneCollection(Collection<T> list);

	<T extends GenericEntity> T cloneEntity(T entity);

	<T> T cloneValue(Object value, GenericModelType type);
	
	<T> T cloneValue(Object value);

}
