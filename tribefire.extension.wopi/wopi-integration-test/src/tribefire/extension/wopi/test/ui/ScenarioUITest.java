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
package tribefire.extension.wopi.test.ui;

import static com.braintribe.utils.lcd.CollectionTools2.asList;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.wopi.DocumentMode;
import com.braintribe.testing.category.SpecialEnvironment;

/**
 * Complex scenario UI test that opens actual Office documents and checks if the WOPI UI opens correctly.
 * 
 *
 */
@Category(SpecialEnvironment.class)
public class ScenarioUITest extends AbstractWopiUITest {

	// -----------------------------------------------------------------------
	// TESTS
	// -----------------------------------------------------------------------

	@Test
	public void testScenarioSimple() throws Exception {
		//@formatter:off
		runParallel(asList(
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT)
			));
		//@formatter:on
	}

	@Test
	public void testScenarioMedium() throws Exception {
		//@formatter:off
		runParallel(asList(
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT)
			));
		//@formatter:on
	}

	@Test
	public void testScenarioComplex() throws Exception {
		//@formatter:off
		runParallel(asList(
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT)
				));
		//@formatter:on
	}

	@Test
	public void testScenarioUltimate() throws Exception {
		//@formatter:off
		runParallel(asList(
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT),
				
				() -> openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW),
				() -> openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT),
				() -> openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW),
				() -> openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT)
			));
		//@formatter:on
	}
}
