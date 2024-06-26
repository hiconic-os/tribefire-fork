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
package tribefire.extension.xml.schemed.test.roundtrip.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.experimental.categories.Category;

import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.archives.Archives;
import com.braintribe.utils.archives.ArchivesException;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.test.commons.commons.roundtrip.RoundTripRequest;
import tribefire.extension.xml.schemed.test.commons.xsd.test.util.TestUtil;

/**
 * an abstract runner for the round trip test
 * @author pit
 *
 */

@Category(KnownIssue.class)
public abstract class AbstractRoundTripLab extends AbstractXsdAnalyzerLab {
	 
	private static final String ROUNDTRIP_RUNNER_CMD_ZIP = "schemed-xsd-xml-roundtrip-runner-2.*-cmd.zip";
	private static final String COMMAND_RUNNER = "tribefire.extension.xml.schemed.test.runner.Runner";
	protected static final File contents = new File("res");
	private static final File RUNNER_CMD_DIR = new File( contents, "setup");	
	private static final File cpDir = new File( RUNNER_CMD_DIR, "cp");		
	private boolean runAsDebug = false;
	private StaxMarshaller marshaller = new StaxMarshaller();
	

	protected void runRoundTrip(File workingDirectory, String packageName, String xsdName, List<String> xmlNames, String modelName, String ... references ) {
		runRoundTrip(workingDirectory, packageName, xsdName, xmlNames, modelName, 1, 0, references);
	}
	/**
	 * @param workingDirectory
	 * @param packageName
	 * @param xsdName
	 * @param xmlName
	 * @param modelName
	 * @param references
	 */
	protected void runRoundTrip(File workingDirectory, String packageName, String xsdName, List<String> xmlNames, String modelName, int repeats, int threshold, String ... references ) {
		File input = new File( workingDirectory, "input");
		File output = new File( workingDirectory, "analyzer");
		File marshallingDirectory = new File( workingDirectory, "marshaller");
		
		TestUtil.ensure(output);
		TestUtil.ensure( marshallingDirectory);
		
		TestUtil.ensure( cpDir);
		
		//
		// run analyzer
		//
		List<String> referencedXsds = null;
		if (references != null && references.length > 0) {
			referencedXsds = Arrays.asList( references);
		}
		SchemedXmlXsdAnalyzerRequest analyzerRequest = getRequestBuilder().buildPrimerRequest(input, packageName, xsdName, referencedXsds, modelName);
		ProcessTuple processTuple = process(analyzerRequest, output);
	
		//
		// run marshaller
		//
		
		// unpack runner 
		File  cpzip = findValidRunner( RUNNER_CMD_DIR);
		try {
			Archives.zip().from(cpzip).unpack(cpDir).close();
		} catch (ArchivesException e) {
			throw new IllegalStateException("cannot unpack ["+ cpzip.getAbsolutePath() + "] to ["+ cpDir.getAbsolutePath() + "]", e);
		}

		// prepare environment  

		// add produced jar to classpath directory   
		File skeletonModelAsJar = processTuple.getSkeletonModelAsJar();
		FileTools.copyFile( skeletonModelAsJar, new File( cpDir, skeletonModelAsJar.getName()));

		// copy xsd, xml, and mapping model
		for (String xmlName : xmlNames) {
			FileTools.copyFile( new File( input, xmlName), new File( marshallingDirectory, xmlName));
		}
		FileTools.copyFile( new File( input, xsdName), new File( marshallingDirectory, xsdName));
		File mappingModelAsXml = processTuple.getMappingModelAsXml();
		FileTools.copyFile( mappingModelAsXml, new File( marshallingDirectory, mappingModelAsXml.getName()));
		
		
		// prepare test launcher 			
		TestLauncher launcher = new TestLauncher();
		launcher.setClasspathDir(cpDir);
		launcher.setCommandClass( COMMAND_RUNNER);
		
			
		// debug settings..
		if (runAsDebug) {
			List<LaunchParam> params = new ArrayList<LaunchParam>();
			params.add(new LaunchParam( "-Xdebug"));
			params.add(new LaunchParam( "-Xrunjdwp:transport=dt_socket,address=8001,server=y,suspend=y"));			
			launcher.setParams(params);
		}
		
		// prime test runner 
		RoundTripRequest roundTripRequest = RoundTripRequest.T.create();
		roundTripRequest.setWorkingDirectoryname( marshallingDirectory.getAbsolutePath());
		roundTripRequest.setXmlFilenames( xmlNames);
		roundTripRequest.setXsdFilename(xsdName);
		roundTripRequest.setModelFilename( mappingModelAsXml.getName());
		roundTripRequest.setRepeats(repeats);
		roundTripRequest.setMeasuringThreshold(threshold);
		
		File requestFile = new File( workingDirectory, "request.xml");
		try (OutputStream out = new FileOutputStream( requestFile)) {
			marshaller.marshall(out, roundTripRequest);
		}
		catch (IOException e) {
			throw new IllegalStateException( "cannot marshall the RoundTripRequest", e);
		}
		
		launcher.run(requestFile);
		
	}
	
	private File findValidRunner( File directory) {
		for (File file : directory.listFiles()) {
			if (file.getName().matches( ROUNDTRIP_RUNNER_CMD_ZIP)) {
				return file;
			}
		}
		throw new IllegalStateException("cannot find matching runner zip file");
	}
	
}
