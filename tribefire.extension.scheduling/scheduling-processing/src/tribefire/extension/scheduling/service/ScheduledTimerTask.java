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
package tribefire.extension.scheduling.service;

import java.util.TimerTask;

import com.braintribe.logging.Logger;

public class ScheduledTimerTask extends TimerTask {

	private static final Logger logger = Logger.getLogger(ScheduledTimerTask.class);

	private String scheduledId;
	private SchedulingServiceProcessor processor;

	public ScheduledTimerTask(String scheduledId, SchedulingServiceProcessor processor) {
		this.scheduledId = scheduledId;
		this.processor = processor;
	}

	@Override
	public void run() {
		try {
			processor.triggerScheduled(scheduledId);
		} catch (Exception e) {
			logger.debug(() -> "Error while triggering Scheduled task: " + scheduledId, e);
		}
	}

}
