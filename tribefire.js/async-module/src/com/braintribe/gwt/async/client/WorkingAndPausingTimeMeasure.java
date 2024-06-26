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
package com.braintribe.gwt.async.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class WorkingAndPausingTimeMeasure {
	private static WorkingAndPausingTimeMeasure instance = new WorkingAndPausingTimeMeasure();
	//private boolean pause;
	private long start = System.currentTimeMillis();
	/*private long retrospectionFrameSize = 2000;
	private long pauseInFrame;
	private long workInFrame;*/
	
	public static WorkingAndPausingTimeMeasure getInstance() {
		return instance;
	}
	
	private RepeatingCommand entryCommand = new RepeatingCommand() {
		
		@Override
		public boolean execute() {
			start = System.currentTimeMillis();
			return true;
		}
	};
	
	private RepeatingCommand finallyCommand = new RepeatingCommand() {
		
		@Override
		public boolean execute() {
			//long end = System.currentTimeMillis();
			//long delta = end - start;
			//TODO: why is this always returning true?
			return true;
		}
	};
	
	private WorkingAndPausingTimeMeasure() {
		Scheduler.get().scheduleEntry(entryCommand);
		Scheduler.get().scheduleFinally(finallyCommand);
	}
	
	public long getMilliesSinceEntry() {
		return System.currentTimeMillis() - start;
	}
	
	public boolean isRunningLongerThan(long ms) {
		return getMilliesSinceEntry() > ms;
	}
	
	public LoopBreakerCheck createLoopBreakerCheck(long maxTime, long doCheckOnlyNthTime) {
		return new LoopBreakerCheck(maxTime, doCheckOnlyNthTime);
	}
	
	public class LoopBreakerCheck {
		private long maxTime;
		private long doCheckOnlyNthTime;
		private int counter = 0;
		
		public LoopBreakerCheck(long maxTime, long doCheckOnlyNthTime) {
			super();
			this.maxTime = maxTime;
			this.doCheckOnlyNthTime = doCheckOnlyNthTime;
		}

		public boolean shouldBreak() {
			counter++;
			if (counter == doCheckOnlyNthTime) {
				counter = 0;
				return isRunningLongerThan(maxTime);
			}
			else
				return false;
		}
	}

}
