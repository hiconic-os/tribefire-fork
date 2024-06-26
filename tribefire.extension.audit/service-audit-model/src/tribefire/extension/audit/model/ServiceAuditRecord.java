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
package tribefire.extension.audit.model;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

@SelectiveInformation("${requestType} by ${user} at ${date}")
public interface ServiceAuditRecord extends GenericEntity {

	EntityType<ServiceAuditRecord> T = EntityTypes.T(ServiceAuditRecord.class);

	String requestType = "requestType";
	
	String user = "user";
	String userIpAddress = "userIpAddress";
	String date = "date";
	String domainId = "domainId";

	String request = "request";
	String requestMimeType = "requestMimeType";
	
	String result = "result";
	String resultMimeType = "resultMimeType";
	
	String satisfied = "satisfied";
	
	String executionTimeInMs = "executionTimeInMs";
	
	String callId = "callId";
	String parentCallId = "parentCallId";
	
	String getRequestType();
	void setRequestType(String requestType);
	
	String getCallId();
	void setCallId(String callId);
	
	String getParentCallId();
	void setParentCallId(String parentCallId);

	String getUser();
	void setUser(String user);

	Date getDate();
	void setDate(Date date);

	Resource getRequest();
	void setRequest(Resource request);
	
	Resource getResult();
	void setResult(Resource result);
	
	String getUserIpAddress();
	void setUserIpAddress(String userIpAddress);

	String getDomainId();
	void setDomainId(String domainId);
	
	long getExecutionTimeInMs();
	void setExecutionTimeInMs(long ms);
	
	boolean getSatisfied();
	void setSatisfied(boolean satisfied);
}