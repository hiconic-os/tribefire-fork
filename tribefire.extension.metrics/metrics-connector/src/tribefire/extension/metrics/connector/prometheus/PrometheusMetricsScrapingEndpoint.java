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
package tribefire.extension.metrics.connector.prometheus;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;

import io.micrometer.prometheus.PrometheusMeterRegistry;

public class PrometheusMetricsScrapingEndpoint extends HttpServlet implements LifecycleAware {

	private static Logger logger = Logger.getLogger(PrometheusMetricsScrapingEndpoint.class);

	private static final long serialVersionUID = -651627373901583008L;

	private tribefire.extension.metrics.model.deployment.connector.PrometheusMetricsConnector deployable;

	private PrometheusMeterRegistry prometheusMeterRegistry;

	// -----------------------------------------------------------------------
	// LIFECYCLEAWARE
	// -----------------------------------------------------------------------

	@Override
	public void postConstruct() {
		logger.info(() -> "Starting: '" + PrometheusMetricsScrapingEndpoint.class.getSimpleName() + "' of '" + deployable.getName() + "'...");
	}

	@Override
	public void preDestroy() {
		logger.info(() -> "Stopping: '" + PrometheusMetricsScrapingEndpoint.class.getSimpleName() + "' of '" + deployable.getName() + "'!");
	}

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO: improve code quality - only for demo here
		// TODO: set to debug
		logger.debug(() -> {
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}

			return "Got request for scraping Prometheus request from: '" + ipAddress + "'";
		});

		if (prometheusMeterRegistry == null) {
			logger.debug(() -> "Prometheus Registry not initialized yet. Probably no metrics extension was added!");
			return;
		}

		String scrape = prometheusMeterRegistry.scrape();

		logger.debug(() -> "Scrape: '" + scrape + "'");

		response.setStatus(200);

		try (OutputStream os = response.getOutputStream()) {
			os.write(scrape.getBytes());
		}
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setPrometheusMeterRegistry(PrometheusMeterRegistry prometheusMeterRegistry) {
		this.prometheusMeterRegistry = prometheusMeterRegistry;
	}

	@Configurable
	@Required
	public void setDeployable(tribefire.extension.metrics.model.deployment.connector.PrometheusMetricsConnector deployable) {
		this.deployable = deployable;
	}
}