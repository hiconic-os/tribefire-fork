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
package tribefire.extension.xml.schemed.test.roundtrip.tarmed;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.KnownIssue;

@Category(KnownIssue.class)
public class TarmedInvoiceLab extends AbstractTarmedLab{

	private static final String COM_BRAINTRIBE_SCHEMEDXML_TARMED = "com.braintribe.schemedxml.tarmed";
	private static final String MAPPING_MODEL = COM_BRAINTRIBE_SCHEMEDXML_TARMED + ":TarmedFlatModel#1.0";
	private static final String XSD = "MDInvoiceRequest_400.xsd";
	private File workingDirectory = new File( tarmed, "invoice");
	
	
	@Test
	public void test_TarmedInvoice() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_TARMED, XSD, Arrays.asList("4187_10734361.xml", "4187_10734368.xml"), MAPPING_MODEL);
	}
	
}
