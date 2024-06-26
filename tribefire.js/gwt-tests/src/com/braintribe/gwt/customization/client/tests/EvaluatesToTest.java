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
import static com.braintribe.utils.lcd.CollectionTools2.newLinkedSet;
import static com.braintribe.utils.lcd.CollectionTools2.sort;

import java.util.List;
import java.util.Set;

import com.braintribe.gwt.customization.client.tests.model.evaluatesTo.BoundWildcardEvalEntity;
import com.braintribe.gwt.customization.client.tests.model.evaluatesTo.EvalEntity;
import com.braintribe.gwt.customization.client.tests.model.evaluatesTo.ListEvalEntity;
import com.braintribe.gwt.customization.client.tests.model.evaluatesTo.SubTypeEvalRequest;
import com.braintribe.gwt.customization.client.tests.model.evaluatesTo.SubTypeEvalResult;
import com.braintribe.gwt.customization.client.tests.model.evaluatesTo.UnboundWildcardEvalEntity;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.GmfException;
import com.braintribe.model.generic.reflection.BaseType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.SimpleType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.util.meta.NewMetaModelGeneration;

/**
 * @author peter.gazdik
 */
public class EvaluatesToTest extends AbstractGwtTest {

	private final NewMetaModelGeneration mmg = new NewMetaModelGeneration();

	@Override
	protected void tryRun() throws GmfException {
		GmMetaModel metaModel = generateModel();

		makeSignaturesDynamic(metaModel);
		ensureModelTypes(metaModel);

		testEvaluatesToSetCorrectly(EvalEntity.class.getName(), SimpleType.TYPE_STRING);
		testEvaluatesToSetCorrectly(UnboundWildcardEvalEntity.class.getName(), BaseType.INSTANCE);
		testEvaluatesToSetCorrectly(ListEvalEntity.class.getName(), GMF.getTypeReflection().getListType(SimpleType.TYPE_STRING));
		testEvaluatesToSetCorrectly(BoundWildcardEvalEntity.class.getName(), GenericEntity.T);

		metaModel = generateModelWithSubTypeAsResult(metaModel);
		makeSignaturesDynamic(metaModel);
		ensureModelTypes(metaModel);

		testEvaluatesToSetCorrectly_Dynamic(SubTypeEvalRequest.class.getName(), SubTypeEvalResult.T);
	}

	private void testEvaluatesToSetCorrectly(String typeSignature, GenericModelType type) {
		testEvaluatesToHelper(typeSignature, type);
		testEvaluatesToHelper(makeSignatureDynamic(typeSignature), type);
	}

	private void testEvaluatesToSetCorrectly_Dynamic(String typeSignature, EntityType<?> type) {
		testEvaluatesToHelper(typeSignature, type);
		testEvaluatesToHelper(makeSignatureDynamic(typeSignature), typeReflection.getEntityType(makeSignatureDynamic(type.getTypeSignature())));
	}

	private void testEvaluatesToHelper(String typeSignature, GenericModelType type) {
		EntityType<GenericEntity> et = typeReflection.getEntityType(typeSignature);

		if (et.getEvaluatesTo() != type)
			logError("Wrong evaluatesTo for: " + typeSignature + " Expected: " + type + ", actual: " + et.getEvaluatesTo());
		else
			log("    evaluatesTo for: '" + typeSignature + "' [OK]");
	}

	private GmMetaModel generateModel() {
		log("generating model");

		return mmg.buildMetaModel("test.gwt.EvaluatesToTestModel",
				asList(EvalEntity.T, BoundWildcardEvalEntity.T, ListEvalEntity.T, UnboundWildcardEvalEntity.T));
	}

	private GmMetaModel generateModelWithSubTypeAsResult(GmMetaModel parentModel) {
		log("generating model with sub-type as eval result");

		GmMetaModel result = mmg.buildMetaModel("test.gwt.ExtendedEvaluatesToTestModel", asList(SubTypeEvalRequest.T), asList(parentModel));
		// Ordering by signature means order is: SubTypeEvalRequest, SubTypeEvalResult
		result.setTypes(sortBySignature(result.getTypes()));

		return result;
	}

	private Set<GmType> sortBySignature(Set<GmType> types) {
		List<GmType> typesInRequestResultOrder = sort(types, (et1, et2) -> et1.getTypeSignature().compareTo(et2.getTypeSignature()));

		return newLinkedSet(typesInRequestResultOrder);
	}

}
