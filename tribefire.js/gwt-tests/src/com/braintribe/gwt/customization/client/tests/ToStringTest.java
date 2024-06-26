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

import java.util.Map;

import com.braintribe.gwt.customization.client.tests.model.tostring.NoStringEntity;
import com.braintribe.gwt.customization.client.tests.model.tostring.ToStringEntity;
import com.braintribe.gwt.customization.client.tests.model.tostring.ToStringSubEntity;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.GmfException;
import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMetaModel;

/**
 * @author peter.gazdik
 */
public class ToStringTest extends AbstractGwtTest {

	@Override
	protected void tryRun() throws GmfException {
		GmMetaModel metaModel = generateModel();

		ensureModelTypes(metaModel);

		testToStringMethods(ToStringEntity.class.getName());
		testToStringMethods(makeSignatureDynamic(ToStringEntity.class.getName()));

		testToStringMethodsSub(ToStringSubEntity.class.getName());
		testToStringMethodsSub(makeSignatureDynamic(ToStringSubEntity.class.getName()));
	}

	private void testToStringMethods(String typeSignature) {
		EntityType<StandardStringIdentifiable> et = typeReflection.getEntityType(typeSignature);
		testToStringMethods("plain", et.createPlain());
		testToStringMethods("enhanced", et.create());
	}

	private void testToStringMethods(String type, StandardStringIdentifiable ge) {
		initAndLogTestingEntity(ge, type);
		assertEqual(ge, "toString", type, ge.toString(), "ToString");
		assertEqual(ge, "toSelectiveInformation", type, ge.toSelectiveInformation(), "Selective");

		ge.setId(99L);
		assertEqual(ge, "toString (persistent)", type, ge.toString(), "ToString");
		assertEqual(ge, "toSelectiveInformation (persistent)", type, ge.toSelectiveInformation(), "Selective");
	}

	private void testToStringMethodsSub(String typeSignature) {
		EntityType<GenericEntity> et = typeReflection.getEntityType(typeSignature);
		testToStringMethodsSub("plain", et.createPlain());
		testToStringMethodsSub("enhanced", et.create());
	}

	private void testToStringMethodsSub(String type, GenericEntity ge) {	
		initAndLogTestingEntity(ge, type);
		assertEqual(ge, "toString", type, ge.toString(), "ToStringSub");
		assertEqual(ge, "toSelectiveInformation", type, ge.toSelectiveInformation(), "SelectiveSub");
	}

	private void assertEqual(GenericEntity ge, String method, String type, String actual, String expected) {
		expected = prefixFor(ge) + " " + expected;
		
		if (actual.equals(expected)) {
			log("    method '" + method + "' [OK]");

		} else {
			EntityType<GenericEntity> et = ge.entityType();

			logError(method + " problem [" + type + "]. Wrong Value for type " + et.getTypeSignature() + ". Expected: " + expected + ", actual: "
					+ actual);
		}
	}

	private String prefixFor(GenericEntity entity) {
		EntityType<GenericEntity> et = entity.entityType();
		return et.getTypeSignature() + " " + et.getShortName() + " " + idOrRid(entity) + " " + entity.runtimeId() + " ${N/A}";
	}

	private Object idOrRid(GenericEntity entity) {
		Object id = entity.getId();
		return id != null ? id : entity.runtimeId();
	}

	private void initAndLogTestingEntity(GenericEntity entity, String type) {
		entity.setId("id-" + type);
		log("Testing '" + entity.entityType().getTypeSignature() + "'[" + type + "]");
	}

	private GmMetaModel generateModel() {
		log("generating meta model");
		
		GmMetaModel metaModel = modelForTypes(ToStringEntity.T, ToStringSubEntity.T, NoStringEntity.T);

		Map<String, GmEntityType> gmTypes = indexEntityTypes(metaModel);

		GmEntityType tse = gmTypes.get(ToStringEntity.class.getName());
		GmEntityType tsse = gmTypes.get(ToStringSubEntity.class.getName());
		
		GmEntityType nse = gmTypes.get(NoStringEntity.class.getName());

		GmEntityType dynamicTse = createDynamicSubType(tse);
		GmEntityType dynamicTsse = createDynamicSubType(tsse);

		// just so that we also test the method is taken from supertype other than the first one
		dynamicTse.getSuperTypes().add(0, nse);
		dynamicTsse.getSuperTypes().add(0, nse);
		
		metaModel.getTypes().add(dynamicTse);
		metaModel.getTypes().add(dynamicTsse);

		return metaModel;
	}


}
