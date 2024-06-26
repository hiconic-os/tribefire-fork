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
package com.braintribe.model.processing.worker.api;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.PreliminaryEntityReference;

public interface Worker {

	/**
	 * Start the work. This method must return immediately (hence, it must not block). This can be achieved by either forking a separate thread (via
	 * the worker context) or start an asynchronous processing by other means.
	 * 
	 * @param workerContext
	 *            The context that the worker is allowed to use to either access a session or start separate threads,
	 * @throws WorkerException
	 *             Thrown in the event of an error.
	 */
	void start(WorkerContext workerContext) throws WorkerException;

	/**
	 * Instructs the worker to stop the current task. If any threads have been started via the thread context, they would be automatically stopped
	 * later (if they are cancelable, which means that it either repeatedly checks {@link Thread#isInterrupted()} or catching an
	 * {@link InterruptedException} during a blocking operation that supports this exception).
	 */
	void stop(WorkerContext workerContext) throws WorkerException;

	/**
	 * Provides a unique identification object that is used, for example, for leadership management.
	 * 
	 * @return A GenericEntity that identifies this worker.
	 */
	default GenericEntity getWorkerIdentification() {
		if (isSingleton())
			throw new UnsupportedOperationException("'getWorkerIdentification()' must be implemented explicitly for singleton workers! Worker: " + this);

		// TODO this used to be HardwiredWorker, but that is no tf.cortex.
		PreliminaryEntityReference ref = PreliminaryEntityReference.T.create();
		ref.setId(getClass().getName() + "-defaultId");
		
		return ref;
	}

	/**
	 * Tells the system whether this worker can be instantiated (and thus executed) multiple times for parallel processing and/or clustering.
	 * 
	 * @return <code>true</code> iff this worker can be executed only once.
	 */
	default boolean isSingleton() {
		return false;
	}

}
