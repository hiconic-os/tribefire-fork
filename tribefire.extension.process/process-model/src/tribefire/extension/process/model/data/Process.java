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
package tribefire.extension.process.model.data;

import java.util.Date;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.process.model.data.tracing.ProcessTrace;

/**
 * base class for all processes that can be handled by the process engine<br/><br/>
 * 
 * the current {@link ProcessTrace}<br/>
 * a set of {@link ProcessTrace} (all traces of the process)<br/>
 * 
 * 
 * @author Pit, Dirk
 *
 */
@Abstract
public interface Process extends GenericEntity {

	EntityType<Process> T = EntityTypes.T(Process.class);
	
	String lastTransit = "lastTransit";
	String overdueAt = "overdueAt";
	String trace = "trace";
	String traces = "traces";
	String restartCounters = "restartCounters";
	String activity = "activity";

	Date getLastTransit();
	void setLastTransit(Date lastTransit);
	
	Date getOverdueAt();
	void setOverdueAt(Date overdueAt);
	
	ProcessTrace getTrace();
	void setTrace( ProcessTrace trace);
	
	Set<ProcessTrace> getTraces();
	void setTraces( Set<ProcessTrace> traces);
	
	Set<RestartCounter> getRestartCounters();
	void setRestartCounters(Set<RestartCounter> restartCounters);
	
	ProcessActivity getActivity();
	void setActivity(ProcessActivity activity);
	
}
