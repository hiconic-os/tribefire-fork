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
package tribefire.extension.xml.schemed.test.processing;

import java.io.File;

import org.junit.experimental.categories.Category;

import com.braintribe.console.ConsoleConfiguration;
import com.braintribe.console.PlainSysoutConsole;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.resource.Resource;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.utils.stream.ReferencingFileInputStream;

import tribefire.extension.xml.schemed.processing.XsdAnalyzingProcessor;
import tribefire.extension.xml.schemed.service.AnalyzeXsd;
import tribefire.extension.xml.schemed.service.AnalyzedXsd;
import tribefire.extension.xml.schemed.test.commons.xsd.test.util.TestUtil;

@Abstract
@Category(KnownIssue.class)
public class AbstractTest {
	protected static File contents = new File( "res");

	
	protected static void before(File output) {
		TestUtil.ensure(output);
		ConsoleConfiguration.install( PlainSysoutConsole.INSTANCE);
	}
	
	protected AnalyzedXsd runTest( File input, File output, String xsdName, String packageName, String modelName) {
		AnalyzeXsd request = AnalyzeXsd.T.create();
		
		File inputFile = new File( input, xsdName);
		request.setSchema( Resource.createTransient(() -> new ReferencingFileInputStream( inputFile)));
		request.setOutputDir( output.getAbsolutePath());
		
		request.setTopPackageName(packageName);
		request.setSkeletonModelName( modelName);
		
		request.setExchangePackageOutput(true);
		//request.setJarOutput(true);
		
		XsdAnalyzingProcessor processor = new XsdAnalyzingProcessor();
	
		AnalyzedXsd response = processor.analyzeXsdFile((ServiceRequestContext) null, request);
		
		return response;

	}
}
