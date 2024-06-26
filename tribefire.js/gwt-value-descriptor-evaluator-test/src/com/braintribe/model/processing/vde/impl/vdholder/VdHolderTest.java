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
package com.braintribe.model.processing.vde.impl.vdholder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.vde.evaluator.api.VdeEvaluationMode;
import com.braintribe.model.processing.vde.impl.misc.VdeTestTemplate;
import com.braintribe.model.processing.vde.test.VdeTest;

/**
 * Test for entities that have a VD set as a VD holder
 */
public class VdHolderTest extends VdeTest {

	private static final EntityType<VdeTestTemplate> et = VdeTestTemplate.T;

	private final VdeTestTemplate template = et.create();

	@Test
	public void testEvaluatesVdParams() throws Exception {
		Property prop = template.entityType().getProperty("stringParam");
		prop.setAbsenceInformation(template, GMF.absenceInformation());

		VdeTestTemplate result = evalTemplate();

		assertThat(prop.getAbsenceInformation(result)).isNotNull();
	}

	private VdeTestTemplate evalTemplate() {
		VdeTestTemplate result = (VdeTestTemplate) evaluateWithEvaluationMode(template, VdeEvaluationMode.Preliminary);
		assertThat(result).isNotNull().isNotSameAs(template);
		return result;
	}

}
