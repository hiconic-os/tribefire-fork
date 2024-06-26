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
package com.braintribe.model.ravenhurst.interrogation;

import java.util.Date;
import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.ravenhurst.Artifact;

public interface RavenhurstResponse extends GenericEntity {

	final EntityType<RavenhurstResponse> T = EntityTypes.T(RavenhurstResponse.class);

	String getPayload();
	void setPayload(String payload);

	List<Artifact> getTouchedArtifacts();
	void setTouchedArtifacts(List<Artifact> artifacts);

	@Initializer("now()")
	Date getResponseDate();
	void setResponseDate(Date date);

	String getErrorMsg();
	void setErrorMsg(String msg);

	boolean getIsFaulty();
	void setIsFaulty(boolean faulty);

	double getElapsedTime();
	void setElapsedTime(double time);
}
