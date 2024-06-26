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
package com.braintribe.model.processing.wopi.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.worker.api.Worker;
import com.braintribe.model.processing.worker.api.WorkerContext;
import com.braintribe.model.processing.worker.api.WorkerException;
import com.braintribe.model.wopi.service.integration.RemoveAllWopiSessions;
import com.braintribe.utils.StringTools;
import com.braintribe.utils.date.NanoClock;

/**
 * {@link Worker} to cleanup expired/closed WopiSession
 * 
 *
 */
public class CleanupWopiSessionWorker implements Worker, Runnable {

	private static final Logger logger = Logger.getLogger(CleanupWopiSessionWorker.class);

	private com.braintribe.model.wopi.service.CleanupWopiSessionWorker deployable;

	private Supplier<PersistenceGmSession> sessionSupplier;

	public static boolean run = true;

	private Future<?> workerFuture;

	@Override
	public void run() {

		try {
			Thread.sleep(deployable.getIntervalInMs());
		} catch (InterruptedException e) {
			logger.info(() -> "Got interrupted. Ceasing cleaning up 'WopiSession's");
			run = false;
		}

		while (run) {

			Instant start = NanoClock.INSTANCE.instant();
			try {
				logger.debug(() -> "Triggering cleaning up 'WopiSession's");

				RemoveAllWopiSessions request = RemoveAllWopiSessions.T.create();
				request.setForceRemove(false);
				request.setContext(deployable.getContext());

				PersistenceGmSession session = sessionSupplier.get();
				request.eval(session).get();

				logger.debug(() -> "Successfully cleaned up 'WopiSession's in " + StringTools.prettyPrintDuration(start, true, ChronoUnit.MILLIS));
			} catch (Exception e) {
				logger.error(() -> "Error while trying to clean up 'WopiSession's after "
						+ StringTools.prettyPrintDuration(start, true, ChronoUnit.MILLIS), e);
			} finally {
				try {
					Thread.sleep(deployable.getIntervalInMs());
				} catch (InterruptedException e) {
					logger.info(() -> "Got interrupted. Ceasing cleaning up 'WopiSession's operations.");
					run = false;
				}
			}
		}

	}

	@Override
	public GenericEntity getWorkerIdentification() {
		return deployable;
	}

	@Override
	public void start(WorkerContext workerContext) throws WorkerException {
		run = true;
		workerFuture = workerContext.submit(this);
	}

	@Override
	public void stop(WorkerContext workerContext) throws WorkerException {
		run = false;
		if (workerFuture != null) {
			workerFuture.cancel(true);
		}
		workerFuture = null;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setDeployable(com.braintribe.model.wopi.service.CleanupWopiSessionWorker deployable) {
		this.deployable = deployable;
	}
	@Configurable
	@Required
	public void setSessionSupplier(Supplier<PersistenceGmSession> sessionSupplier) {
		this.sessionSupplier = sessionSupplier;
	}

}
