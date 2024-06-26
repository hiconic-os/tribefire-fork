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
package com.braintribe.gwt.customization.client.tests;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static com.braintribe.utils.lcd.CollectionTools2.asSet;
import static com.braintribe.utils.lcd.CollectionTools2.newLinkedList;
import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.gwt.customization.client.tests.model.defaultMethods.DefaultMethodsAbstractEntity;
import com.braintribe.gwt.customization.client.tests.model.defaultMethods.DefaultMethodsEntity;
import com.braintribe.gwt.customization.client.tests.model.defaultMethods.DefaultMethodsSubEntity;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMetaModel;

/**
 * @author peter.gazdik
 */
public class DefaultMethodsTest extends AbstractGwtTest {

	@Override
	protected void tryRun() throws Exception {
		GmMetaModel metaModel = generateModel();

		ensureModelTypes(metaModel);

		testDefaultMethods(DefaultMethodsEntity.class.getName());
		testDefaultMethods(makeSignatureDynamic(DefaultMethodsEntity.class.getName()));

		testDefaultMethodsSub(DefaultMethodsSubEntity.class.getName());
		testDefaultMethodsSub(makeSignatureDynamic(DefaultMethodsSubEntity.class.getName()));
	}

	private void testDefaultMethods(String typeSignature) throws Exception {
		EntityType<GenericEntity> et = typeReflection.getEntityType(typeSignature);

		testDefaultMethods(et, "plain", et.createPlain());
		testDefaultMethods(et, "enhanced", et.create());
	}

	private void testDefaultMethods(EntityType<GenericEntity> et, String type, GenericEntity ge) throws Exception {
		logTestingEntity(et, type);

		DefaultMethodsEntity entity = (DefaultMethodsEntity) ge;

		assertEqual(ge, "abstractDescription", type, entity.abstractDescription(), DefaultMethodsAbstractEntity.ABSTRACT_DESCRIPTION);
		assertEqual(ge, "description", type, entity.description(), DefaultMethodsEntity.DESCRIPTION);
		assertEqual(ge, "identityHash", type, entity.identityHash(), System.identityHashCode(entity));

		List<GenericEntity> list = newList();
		entity.addToList(list);
		assertEqual(ge, "addToList()", type, list, asList(ge));

		LinkedList<GenericEntity> linkedList = newLinkedList();
		entity.addToList(linkedList);
		assertEqual(ge, "addToList", type, linkedList, asList(ge, ge));
	}

	private void assertEqual(GenericEntity ge, String method, String type, Object actual, Object expected) {
		if (actual.equals(expected)) {
			log("    method '" + method + "' [OK]");

		} else {
			EntityType<GenericEntity> et = ge.entityType();

			logError("Default method problem [" + type + "]. Value associated with " + et.getTypeSignature() + "." + method +
					" is wrong Expected: " + expected + ", actual: " + actual);
		}
	}

	private void testDefaultMethodsSub(String typeSignature) {
		EntityType<GenericEntity> et = typeReflection.getEntityType(typeSignature);

		testDefaultMethodsSub(et, "plain", et.createPlain());
		testDefaultMethodsSub(et, "enhanced", et.create());
	}

	private void testDefaultMethodsSub(EntityType<GenericEntity> et, String type, GenericEntity ge) {
		logTestingEntity(et, type);

		DefaultMethodsSubEntity entity = (DefaultMethodsSubEntity) ge;

		assertEqual(ge, "abstractDescription", type, entity.abstractDescription(), DefaultMethodsAbstractEntity.ABSTRACT_DESCRIPTION);
		assertEqual(ge, "description", type, entity.description(), DefaultMethodsSubEntity.DESCRIPTION);
		assertEqual(ge, "identityHash", type, entity.identityHash(), System.identityHashCode(entity));

		List<GenericEntity> list = newList();
		entity.addToList(list);
		assertEqual(ge, "addToList(List<>)", type, list, asList(ge));

		LinkedList<GenericEntity> linkedList = newLinkedList();
		entity.addToList(linkedList);
		assertEqual(ge, "addToList(LinkedList<>)", type, linkedList, asList(ge, ge));

		Set<GenericEntity> set = newSet();
		entity.addTo(set);
		assertEqual(ge, "addToSet", type, set, asSet(ge, ge));
	}

	private void logTestingEntity(EntityType<GenericEntity> et, String type) {
		log("Testing '" + et.getTypeSignature() + "'[" + type + "]");
	}

	private GmMetaModel generateModel() {
		log("generating meta model");
		
		GmMetaModel metaModel = modelForTypes(DefaultMethodsEntity.T, DefaultMethodsSubEntity.T);

		Map<String, GmEntityType> gmTypes = indexEntityTypes(metaModel);

		GmEntityType dme = gmTypes.get(DefaultMethodsEntity.class.getName());
		GmEntityType dmse = gmTypes.get(DefaultMethodsSubEntity.class.getName());

		GmEntityType dynamicDme = createDynamicSubType(dme);
		GmEntityType dynamicDmse = createDynamicSubType(dmse);

		metaModel.getTypes().add(dynamicDme);
		metaModel.getTypes().add(dynamicDmse);

		return metaModel;
	}


}
