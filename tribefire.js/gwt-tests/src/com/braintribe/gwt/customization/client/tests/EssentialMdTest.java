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

import java.util.List;

import com.braintribe.gwt.customization.client.tests.model.essentialmd.EssentialMdEntity;
import com.braintribe.gwt.customization.client.tests.model.essentialmd.EssentialMdSiblingEntity;
import com.braintribe.gwt.customization.client.tests.model.essentialmd.EssentialMdSubEntity;
import com.braintribe.model.generic.GmfException;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.util.meta.NewMetaModelGeneration;

/**
 * @author peter.gazdik
 */
public class EssentialMdTest extends AbstractGwtTest {

	private final NewMetaModelGeneration mmg = new NewMetaModelGeneration();

	private static final List<EntityType<?>> types = asList( //
			EssentialMdEntity.T, //
			EssentialMdSiblingEntity.T, //
			EssentialMdSubEntity.T //
	);

	@Override
	protected void tryRun() throws GmfException {
		GmMetaModel model = generateModel();

		makeSignaturesDynamic(model);
		ensureModelTypes(model);

		testNonConfidential(EssentialMdEntity.class.getName(), EssentialMdEntity.regularProperty);
		testConfidential(EssentialMdEntity.class.getName(), EssentialMdEntity.confidential);
		testConfidential(EssentialMdSubEntity.class.getName(), EssentialMdEntity.confidential);
		testConfidential(EssentialMdSubEntity.class.getName(), EssentialMdSubEntity.siblingConfidential);
	}

	private GmMetaModel generateModel() {
		log("generating model");

		return mmg.buildMetaModel("test.gwt.EssentialMdTestModel", types);
	}

	private void testConfidential(String typeSignature, String propertyName) {
		testConfidentialHelper(typeSignature, propertyName);
		testConfidentialHelper(makeSignatureDynamic(typeSignature), propertyName);
	}

	private void testConfidentialHelper(String typeSignature, String propertyName) {
		EntityType<?> et = typeReflection.getEntityType(typeSignature);

		Property p = et.getProperty(propertyName);

		if (p.isConfidential())
			log("    Confidential property [" + propertyName + "]: '" + typeSignature + "' [OK]");
		else
			logError("Property [" + propertyName + "] is not confidential : " + typeSignature);
	}

	private void testNonConfidential(String typeSignature, String propertyName) {
		testNonConfidentialHelper(typeSignature, propertyName);
		testNonConfidentialHelper(makeSignatureDynamic(typeSignature), propertyName);
	}

	private void testNonConfidentialHelper(String typeSignature, String propertyName) {
		EntityType<?> et = typeReflection.getEntityType(typeSignature);

		Property p = et.getProperty(propertyName);

		if (p.isConfidential())
			logError("Property [" + propertyName + "] should not be confidential: " + typeSignature);
		else
			log("    Non-confidential property [" + propertyName + "]: '" + typeSignature + "' [OK]");
	}

}
