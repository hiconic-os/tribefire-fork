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
package com.braintribe.model.processing.manipulation.parser.impl.collection;

import static com.braintribe.model.processing.manipulation.parser.impl.collection.GmmlCollectionTools.addToCollection;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.reflection.ListType;
import com.braintribe.model.generic.reflection.MapType;
import com.braintribe.model.generic.reflection.SetType;
import com.braintribe.model.processing.manipulation.parser.api.CollectionDeltaManipulator;
import com.braintribe.model.processing.manipulation.parser.api.GmmlManipulatorErrorHandler;

/**
 * @author peter.gazdik
 */
public class SetDeltaManipulator implements CollectionDeltaManipulator {

	private final Set<Object> values;
	private final GmmlManipulatorErrorHandler errorHandler;

	public SetDeltaManipulator(Set<Object> values, GmmlManipulatorErrorHandler errorHandler) {
		this.values = values;
		this.errorHandler = errorHandler;
	}

	@Override
	public void addToList(List<Object> list, ListType type) {
		values.forEach(value -> addToCollection(list, type, value, errorHandler));
	}

	@Override
	public void addToSet(Set<Object> set, SetType type) {
		values.forEach(value -> addToCollection(set, type, value, errorHandler));
	}

	@Override
	public void addToMap(Map<Object, Object> map, MapType type) {
		values.forEach(value -> addToMap(map, type, value));
	}

	private void addToMap(Map<Object, Object> map, MapType type, Object value) {
		if (value != missing) {
			if (type.getKeyType().isValueAssignable(value))
				map.put(value, null);
			else
				errorHandler.wrongValueTypeToAddToCollection(value, type);
		}
	}

	@Override
	public void removeFromList(List<Object> list, ListType type) {
		list.removeAll(values);
	}

	@Override
	public void removeFromSet(Set<Object> set, SetType type) {
		set.removeAll(values);
	}

	@Override
	public void removeFromMap(Map<Object, Object> map, MapType type) {
		map.keySet().removeAll(values);
	}

}
