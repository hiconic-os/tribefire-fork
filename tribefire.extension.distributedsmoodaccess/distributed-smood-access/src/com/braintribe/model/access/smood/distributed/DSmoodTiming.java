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
package com.braintribe.model.access.smood.distributed;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.braintribe.utils.lcd.StringTools;

public class DSmoodTiming {

	protected AtomicInteger totalQueryCount = new AtomicInteger(0);
	protected AtomicLong totalQueryTime = new AtomicLong(0); 
	protected AtomicLong totalQueryDbOverheadTime = new AtomicLong(0);
	
	protected AtomicInteger totalSelectQueryCount = new AtomicInteger(0);
	protected AtomicLong totalSelectQueryTime = new AtomicLong(0); 
	protected AtomicLong totalSelectQueryDbOverheadTime = new AtomicLong(0);
	
	protected AtomicInteger totalPropertyQueryCount = new AtomicInteger(0);
	protected AtomicLong totalPropertyQueryTime = new AtomicLong(0); 
	protected AtomicLong totalPropertyQueryDbOverheadTime = new AtomicLong(0);
	
	protected AtomicInteger totalApplyManipulationCount = new AtomicInteger(0);
	protected AtomicLong totalApplyManipulationTime = new AtomicLong(0);
	protected AtomicLong totalApplyManipulationDbGetOverheadTime = new AtomicLong(0);
	protected AtomicLong totalApplyManipulationDbWriteOverheadTime = new AtomicLong(0);
	protected AtomicLong totalApplyManipulationDbLockOverheadTime = new AtomicLong(0);
	protected AtomicLong totalApplyManipulationDbUnlockOverheadTime = new AtomicLong(0);
	
	protected String accessId = null;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Distributed Smood Statistics: ");
		if (this.accessId != null) {
			sb.append(this.accessId);
		}
		sb.append("\n");
		sb.append("Entity Queries:\n");
		sb.append("  ");
		sb.append(totalQueryCount);
		sb.append(" executions\n");
		if (totalQueryCount.longValue() > 0) {
			sb.append("  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalQueryTime.longValue() / totalQueryCount.longValue()));
			sb.append(" average total processing time\n  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalQueryDbOverheadTime.longValue() / totalQueryCount.longValue()));
			sb.append(" average DB overhead time\n");
		}
		sb.append("Select Queries:\n");
		sb.append("  ");
		sb.append(totalSelectQueryCount);
		sb.append(" executions\n");
		if (totalSelectQueryCount.longValue() > 0) {
			sb.append("  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalSelectQueryTime.longValue() / totalSelectQueryCount.longValue()));
			sb.append(" average total processing time\n  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalSelectQueryDbOverheadTime.longValue() / totalSelectQueryCount.longValue()));
			sb.append(" average DB overhead time\n");
		}
		sb.append("Property Queries:\n");
		sb.append("  ");
		sb.append(totalPropertyQueryCount);
		sb.append(" executions\n");
		if (totalPropertyQueryCount.longValue() > 0) {
			sb.append("  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalPropertyQueryTime.longValue() / totalPropertyQueryCount.longValue()));
			sb.append(" average total processing time\n  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalPropertyQueryDbOverheadTime.longValue() / totalPropertyQueryCount.longValue()));
			sb.append(" average DB overhead time\n");
		}
		sb.append("Apply Manipulations:\n");
		sb.append("  ");
		sb.append(totalApplyManipulationCount);
		sb.append(" executions\n");
		if (totalApplyManipulationCount.longValue() > 0) {
			sb.append("  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalApplyManipulationTime.longValue() / totalApplyManipulationCount.longValue()));
			sb.append(" average total processing time\n  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalApplyManipulationDbGetOverheadTime.longValue() / totalApplyManipulationCount.longValue()));
			sb.append(" average DB read overhead time\n  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalApplyManipulationDbWriteOverheadTime.longValue() / totalApplyManipulationCount.longValue()));
			sb.append(" average DB Write overhead time\n  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalApplyManipulationDbLockOverheadTime.longValue() / totalApplyManipulationCount.longValue()));
			sb.append(" average Lock overhead time\n  ");
			sb.append(StringTools.prettyPrintMilliseconds(totalApplyManipulationDbUnlockOverheadTime.longValue() / totalApplyManipulationCount.longValue()));
			sb.append(" average Unlock overhead time\n");
		}
		return sb.toString();
	}
	
}
