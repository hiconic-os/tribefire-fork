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
package tribefire.extension.messaging.connector.pulsar;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.naming.TopicName;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;

import tribefire.extension.messaging.model.deployment.connector.properties.PulsarProperties;

public class PulsarServerHealthChecker {
	private static final Logger logger = Logger.getLogger(PulsarServerHealthChecker.class);
	private static final String NAMESPACE = TopicName.PUBLIC_TENANT + "/" + TopicName.DEFAULT_NAMESPACE;

	private ClassLoader moduleClassLoader;

	public CheckResultEntry checkServer(PulsarProperties properties) {
		CheckResultEntry result = CheckResultEntry.T.create();
		try (PulsarAdmin client = buildAdmin(properties)) {
			List<String> topics = client.topics().getList(NAMESPACE);
			List<String> clusters = client.clusters().getClusters();
			result.setCheckStatus(CheckStatus.ok);
			result.setDetails("Server access is ok. Clusters size: " + clusters.size() + "\n" + "Accessible topics: " + String.join(", ", topics));
			return result;
		} catch (PulsarClientException | PulsarAdminException | IllegalArgumentException e) {
			logger.error("Pulsar is not available.");
			result.setCheckStatus(CheckStatus.fail);
			result.setMessage("Pulsar cluster unreachable!");
			result.setDetails(e.getMessage());
			return result;
		}
	}

	private PulsarAdmin buildAdmin(PulsarProperties properties) throws PulsarClientException {
		ClassLoader origClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			if (moduleClassLoader != null) {
				Thread.currentThread().setContextClassLoader(moduleClassLoader);
			}
		//@formatter:off
		return PulsarAdmin.builder()
				.serviceHttpUrl(properties.getWebServiceUrl())
				.connectionTimeout(properties.getConnectionTimeout(), TimeUnit.SECONDS)
				.requestTimeout(properties.getOperationTimeout(), TimeUnit.SECONDS)
				.build();
        //@formatter:on
		} finally {
			Thread.currentThread().setContextClassLoader(origClassLoader);
		}
	}

	@Configurable
	@Required
	public PulsarServerHealthChecker setModuleClassLoader(ClassLoader moduleClassLoader) {
		this.moduleClassLoader = moduleClassLoader;
		return this;
	}
}
