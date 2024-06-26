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
package tribefire.cortex.services.process_engine_example.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.UUID;

import com.braintribe.codec.string.DateCodec;
import com.braintribe.logging.Logger;
import com.braintribe.model.goofy.GoofyProcess;
import tribefire.extension.process.api.TransitionProcessor;
import tribefire.extension.process.api.TransitionProcessorContext;

import tribefire.cortex.services.process_engine_example.Tokens;

/**
 * @author pit
 *
 */
public class OutputProcessor implements TransitionProcessor<GoofyProcess> {
	private static Logger logger = Logger.getLogger(OutputProcessor.class);

	private DateCodec dateCodec = new DateCodec("dd.MM.yyyy");
	private File outputDirectory;

//	@Required
	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	@Override
	public void process(TransitionProcessorContext<GoofyProcess> context) {

		GoofyProcess process = context.getProcess();

		Properties properties = new Properties();
		try {

			properties.setProperty("date", dateCodec.encode(process.getDate()));
			properties.setProperty("name", process.getName());
			properties.setProperty("number", process.getNumber().toString());
			properties.setProperty("hash", process.getHash());
			
			if (outputDirectory == null) {
				logger.info("No output folder provided. Skipping output");
				return;
			}
			
			Writer writer = new OutputStreamWriter(new FileOutputStream(new File(outputDirectory, UUID.randomUUID().toString())), "UTF-8");
			try {
				properties.store(writer, "no comment says Goofy");
			} finally {
				writer.close();
			}			
		} catch (Exception e) {
			throw new GoofyProcessingException(e);
		} finally {
			context.continueWithState(Tokens.finalize);
		}

	}

}
