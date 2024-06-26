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
package tribefire.cortex.check.processing;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import com.braintribe.model.deploymentapi.check.data.CheckBundlesResponse;

public class CheckBundlesWarnMarshallerTest {

	@Test
	public void testOneWarning() throws Exception {
		CheckBundlesResponseHtmlMarshaller marshaller = new CheckBundlesResponseHtmlMarshaller();
		
		CheckBundlesResponse res = CbmResultUtils.oneOkOneWarn();
		
		File f = new File("./res/one-ok-one-warn-results.html");
		if (f.exists())
			f.delete();
		
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		marshaller.marshall(fos, res);
	}
	
	@Test
	public void testMultipleFailsAndErrors() throws Exception {
		CheckBundlesResponseHtmlMarshaller marshaller = new CheckBundlesResponseHtmlMarshaller();
		
		CheckBundlesResponse res = CbmResultUtils.multipleFailsAndWarns();
		
		File f = new File("./res/multiple-errors.html");
		if (f.exists())
			f.delete();
		
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		marshaller.marshall(fos, res);
	}
	
	@Test
	public void oneFailManyOk() throws Exception {
		CheckBundlesResponseHtmlMarshaller marshaller = new CheckBundlesResponseHtmlMarshaller();
		
		CheckBundlesResponse flatResults = CbmResultUtils.oneFailManyOk();
		
		File f = new File("./res/one-fail-many-ok.html");
		if (f.exists())
			f.delete();
		
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		marshaller.marshall(fos, flatResults);
	}

	
}
