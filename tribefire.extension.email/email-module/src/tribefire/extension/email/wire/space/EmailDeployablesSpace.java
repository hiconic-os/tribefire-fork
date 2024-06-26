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
package tribefire.extension.email.wire.space;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.braintribe.execution.ThreadPoolBuilder;
import com.braintribe.execution.queue.LimitedQueue;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.email.EmailProcessor;
import com.braintribe.model.processing.email.HealthCheckProcessor;
import com.braintribe.model.processing.email.cache.MailerCache;
import com.braintribe.model.processing.email.connection.ImapConnectorImpl;
import com.braintribe.model.processing.email.connection.Pop3ConnectorImpl;
import com.braintribe.model.processing.email.connection.SmtpConnectorImpl;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.module.wire.contract.ModuleReflectionContract;
import tribefire.module.wire.contract.ResourceProcessingContract;
import tribefire.module.wire.contract.SystemUserRelatedContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class EmailDeployablesSpace implements WireSpace {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private ModuleReflectionContract reflection;

	@Import
	private ResourceProcessingContract resourceProcessing;

	@Import
	private SystemUserRelatedContract systemUserRelated;

	@Managed
	public SmtpConnectorImpl smtpConnector(ExpertContext<? extends com.braintribe.model.email.deployment.connection.SmtpConnector> context) {
		com.braintribe.model.email.deployment.connection.SmtpConnector deployable = context.getDeployable();

		SmtpConnectorImpl bean = new SmtpConnectorImpl();
		bean.setConnector(deployable);
		bean.setMailerCache(mailerCache());

		return bean;
	}

	@Managed
	public SmtpConnectorImpl gmailSmtpConnector(ExpertContext<com.braintribe.model.email.deployment.connection.GmailSmtpConnector> context) {
		com.braintribe.model.email.deployment.connection.GmailSmtpConnector deployable = context.getDeployable();

		SmtpConnectorImpl bean = new SmtpConnectorImpl();
		bean.setConnector(deployable);
		bean.setMailerCache(mailerCache());

		return bean;
	}

	@Managed
	public ImapConnectorImpl gmailImapConnector(ExpertContext<com.braintribe.model.email.deployment.connection.GmailImapConnector> context) {
		@SuppressWarnings("unused")
		com.braintribe.model.email.deployment.connection.GmailImapConnector deployable = context.getDeployable();

		ImapConnectorImpl bean = new ImapConnectorImpl();

		return bean;
	}

	@Managed
	public ImapConnectorImpl imapConnector(ExpertContext<com.braintribe.model.email.deployment.connection.ImapConnector> context) {
		@SuppressWarnings("unused")
		com.braintribe.model.email.deployment.connection.ImapConnector deployable = context.getDeployable();

		ImapConnectorImpl bean = new ImapConnectorImpl();

		return bean;
	}

	@Managed
	public Pop3ConnectorImpl pop3Connector(ExpertContext<com.braintribe.model.email.deployment.connection.Pop3Connector> context) {
		@SuppressWarnings("unused")
		com.braintribe.model.email.deployment.connection.Pop3Connector deployable = context.getDeployable();

		Pop3ConnectorImpl bean = new Pop3ConnectorImpl();

		return bean;
	}

	@Managed
	public EmailProcessor emailServiceProcessor() {
		EmailProcessor bean = new EmailProcessor();

		bean.setCortexSessionProvider(tfPlatform.systemUserRelated().cortexSessionSupplier());
		bean.setModuleClassLoader(reflection.moduleClassLoader());
		bean.setMailerCache(mailerCache());
		bean.setPipeStreamFactory(resourceProcessing.streamPipeFactory());
		bean.setHealthCheckExecutor(healthCheckExecutor());

		return bean;
	}

	@Managed
	public HealthCheckProcessor healthCheckProcessor(ExpertContext<com.braintribe.model.email.deployment.service.HealthCheckProcessor> context) {
		@SuppressWarnings("unused")
		com.braintribe.model.email.deployment.service.HealthCheckProcessor deployable = context.getDeployable();

		HealthCheckProcessor bean = new HealthCheckProcessor();
		bean.setSystemServiceRequestEvaluator(tfPlatform.systemUserRelated().evaluator());

		return bean;

	}

	@Managed
	private ExecutorService healthCheckExecutor() {
		int workerThreads = 5;
		//@formatter:off
		return ThreadPoolBuilder.newPool()
				.poolSize(workerThreads, workerThreads)
				.keepAliveTime(60L, TimeUnit.SECONDS)
				.workQueue(new LimitedQueue<>(1))
				.rejectionHandler(new ThreadPoolExecutor.CallerRunsPolicy()) 
				.waitForTasksToCompleteOnShutdown(true)
				.threadNamePrefix("EmailConnectionCheck")
				.description("Email Check Executor") 
				.build();
		//@formatter:on
	}

	@Managed
	private MailerCache mailerCache() {
		MailerCache bean = new MailerCache();
		return bean;
	}
}
