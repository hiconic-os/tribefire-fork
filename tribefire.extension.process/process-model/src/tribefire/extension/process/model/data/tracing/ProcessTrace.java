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
package tribefire.extension.process.model.data.tracing;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.process.model.data.details.ProcessTraceDetails;

@SelectiveInformation("${event} [${state}]")
public interface ProcessTrace extends GenericEntity {

	EntityType<ProcessTrace> T = EntityTypes.T(ProcessTrace.class);

	String state = "state";
	String fromState = "fromState";
	String toState = "toState";
	String msg = "msg";
	String restart = "restart";
	String exceptionTrace = "exceptionTrace";
	String event = "event";
	String date = "date";
	String kind = "kind";
	String initiator = "initiator";
	String details = "details";

	@Priority(10)
	String getState();
	void setState(String state);
	
	@Priority(9)
	String getFromState();
	void setFromState(String fromState);

	@Priority(8)
	String getToState();
	void setToState(String toState);

	@Priority(7)
	String getMsg();
	void setMsg(String msg);
	
	@Priority(6)
	boolean getRestart();
	void setRestart(boolean restart);
	
	@Priority(5)
	ExceptionTrace getExceptionTrace();
	void setExceptionTrace(ExceptionTrace exceptionTrace);
	
	@Priority(4)
	String getEvent();
	void setEvent(String event);
	
	@Priority(3)
	Date getDate();
	void setDate(Date date);

	@Priority(2)
	TraceKind getKind();
	void setKind(TraceKind kind);
	
	@Priority(1)
	String getInitiator();
	void setInitiator(String initiator);
	
	@Priority(0.9)
	ProcessTraceDetails getDetails();
	void setDetails(ProcessTraceDetails details);
	
}
