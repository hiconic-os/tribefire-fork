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

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.wopi.DocumentMode;
import com.braintribe.testing.category.SpecialEnvironment;

/**
 * Simple UI tests that opens actual Office documents and checks if the WOPI UI opens correctly.
 * 
 *
 */
@Category(SpecialEnvironment.class)
public class SimpleUITest extends AbstractWopiUITest {

	// -----------------------------------------------------------------------
	// TESTS
	// -----------------------------------------------------------------------

	@Test
	public void testDocView() throws Exception {
		openWopiDocument(DOC, DocumentMode.view, DOC_BUTTON_VIEW);
	}
	@Test(expected = AssertionError.class)
	public void testDocEdit() throws Exception {
		openWopiDocument(DOC, DocumentMode.edit, NOT_NECESSARY);
	}

	@Test
	public void testDocxView() throws Exception {
		openWopiDocument(DOCX, DocumentMode.view, DOCX_BUTTON_VIEW);
	}
	@Test
	public void testDocxEdit() throws Exception {
		openWopiDocument(DOCX, DocumentMode.edit, DOCX_BUTTON_EDIT);
	}

	@Test
	public void testXlsView() throws Exception {
		openWopiDocument(XLS, DocumentMode.view, XLS_BUTTON_VIEW);
	}
	@Test(expected = AssertionError.class)
	public void testXlsEdit() throws Exception {
		openWopiDocument(XLS, DocumentMode.edit, NOT_NECESSARY);
	}

	@Test
	public void testXlsxView() throws Exception {
		openWopiDocument(XLSX, DocumentMode.view, XLSX_BUTTON_VIEW);
	}
	@Test
	public void testXlsxEdit() throws Exception {
		openWopiDocument(XLSX, DocumentMode.edit, XLSX_BUTTON_EDIT);
	}

	@Test
	public void testPptView() throws Exception {
		openWopiDocument(PPT, DocumentMode.view, PPT_BUTTON_VIEW);
	}
	@Test(expected = AssertionError.class)
	public void testPptEdit() throws Exception {
		openWopiDocument(PPT, DocumentMode.edit, NOT_NECESSARY);
	}

	@Test
	public void testPptxView() throws Exception {
		openWopiDocument(PPTX, DocumentMode.view, PPTX_BUTTON_VIEW);
	}
	@Test
	public void testPptxEdit() throws Exception {
		openWopiDocument(PPTX, DocumentMode.edit, PPTX_BUTTON_EDIT);
	}

}
