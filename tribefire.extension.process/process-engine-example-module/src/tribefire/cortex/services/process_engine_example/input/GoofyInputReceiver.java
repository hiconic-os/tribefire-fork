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
package tribefire.cortex.services.process_engine_example.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.goofy.GoofyProcess;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.FileUploadSource;

import tribefire.cortex.services.process_engine_example.Tokens;

public class GoofyInputReceiver implements Consumer<File> {
	private static Logger logger = Logger.getLogger(GoofyInputReceiver.class);
	private Supplier<PersistenceGmSession> sessionProvider;

	@Required
	public void setSessionProvider(Supplier<PersistenceGmSession> sessionProvider) {
		this.sessionProvider = sessionProvider;
	}

	@Override
	public void accept(File file) throws RuntimeException {
		logger.info("Receiving file!");

		try {

			/* quick and dirty preread to get the parameters */

			Properties properties = new Properties();

			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			in.close();

			String multiplicatorAsString = properties.getProperty("multiplicator");
			String bulkAsString = properties.getProperty("bulk");

			int multiplicator = multiplicatorAsString != null ? Integer.parseInt(multiplicatorAsString) : 1;
			int bulk = bulkAsString != null ? Integer.parseInt(bulkAsString) : 1;

			in = new FileInputStream(file);

			try {
				PersistenceGmSession session = sessionProvider.get();

				Resource resource = session.resources().create().sourceType(FileUploadSource.T).name(file.getName()).store(in);
				resource.setMimeType("application/goofy");
				int b = 0;
				for (int m = 0; m < multiplicator; b++, m++) {

					GoofyProcess goofyProcess = session.create(GoofyProcess.T);
					goofyProcess.setResource(resource);
					goofyProcess.setState(Tokens.decode);

					if ((b + 1) % bulk == 0) {
						session.commit();
					}
				}
				if (b % bulk != 0) {
					session.commit();
				}
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("error while closing input stream for " + file);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("error while creating resource from input file " + file, e);
		}
	}
}
