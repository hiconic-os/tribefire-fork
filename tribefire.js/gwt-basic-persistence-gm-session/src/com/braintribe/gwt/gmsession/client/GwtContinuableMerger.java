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
package com.braintribe.gwt.gmsession.client;

import java.util.function.Function;

import com.braintribe.gwt.logging.client.Profiling;
import com.braintribe.gwt.logging.client.ProfilingHandle;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.util.HistorySuspension;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.session.impl.managed.IdentityCompetence;
import com.braintribe.model.processing.session.impl.managed.merging.ContinuableMerger;
import com.braintribe.processing.async.api.AsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class GwtContinuableMerger<M> extends ContinuableMerger<M> {

	private static Logger logger = Logger.getLogger(GwtContinuableMerger.class);

	private final long workerSliceThresholdInMs = 100;
	
	public GwtContinuableMerger(IdentityCompetence identityCompetence, boolean adopt) {
		super(identityCompetence, adopt);
	}
	
	public GwtContinuableMerger(IdentityCompetence identityCompetence, HistorySuspension historySuspension, boolean adopt){
		super(identityCompetence,historySuspension,adopt);
	}
	
	public GwtContinuableMerger(IdentityCompetence identityCompetence, HistorySuspension historySuspension, boolean adopt, Function<GenericEntity, GenericEntity> envelopeFactory){
		super(identityCompetence,historySuspension,adopt, envelopeFactory);
	}

	public GwtContinuableMerger(IdentityCompetence identityCompetence, HistorySuspension historySuspension, boolean adopt,
			Function<GenericEntity, GenericEntity> envelopeFactory, boolean transferTransientProperties) {

		super(identityCompetence,historySuspension,adopt, envelopeFactory, transferTransientProperties);
	}
	
	@Override
	public void merge(M data, final AsyncCallback<M> asyncCallback) {
		initialize(data);

		final ProfilingHandle profilingHandle = Profiling.start(GwtContinuableMerger.class, "Entity merge", true);

		// start worker "thread"
		Scheduler.get().scheduleIncremental(new RepeatingCommand() {

			boolean trace = logger.isTraceEnabled();
			protected int workSliceCount = 0;
			protected int stepCount = 0;

			@Override
			public boolean execute() {
				try {					
					workSliceCount++;

					suspendHistory();
					try{
						long s = System.currentTimeMillis();
						while (currentStep != null) {

							stepCount++;
							long start = System.currentTimeMillis();

							currentStep.doStep();

							if (trace) {
								long diff = System.currentTimeMillis() - start;
								if (diff > workerSliceThresholdInMs) {
									logger.trace("The step #"+stepCount+" of type "+currentStep.getClass()+" took "+diff+" ms");
								}
							}

							currentStep = currentStep.next;

							long delta = System.currentTimeMillis() - s;
							if (delta > workerSliceThresholdInMs) {
								if (trace)
									logger.trace("Interrupting step "+workSliceCount+" after "+delta+" ms");
								return true;
							}
						}

						if (trace)
							logger.trace("Done with processing after "+(System.currentTimeMillis()-s)+" ms (steps: "+stepCount+")");

					} finally {
						resumeHistory();
					}

					if (trace)
						logger.trace("Entity merging required "+workSliceCount+" slices.");
					profilingHandle.stop();

					try {
						asyncCallback.onSuccess(mergedData);
					} catch (Exception e) {
						logger.error("error during callback processing", e);
					}

					if (trace)
						logger.trace("Done with async callback after entity merging.");
					
					return false;
				} catch (Exception e) {
					profilingHandle.stop();
					asyncCallback.onFailure(new GmSessionException("error while merging data",e));
					return false;
				}
			}
		});
	}
}
