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
package com.braintribe.model.goofy;

import java.util.Date;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

import tribefire.extension.process.model.data.Process;

public interface GoofyProcess extends Process {
	
	final EntityType<GoofyProcess> T = EntityTypes.T(GoofyProcess.class);

	boolean getCleared();
	void setCleared(boolean name);
	
	String getName();
	void setName(String name);
	
	Date getDate();
	void setDate(Date date);
	
	Double getNumber();
	void setNumber(Double number);
	
	String getState();
	void setState(String state);
	
	String getHash();
	void setHash(String hash);
	
	Resource getResource();
	void setResource(Resource Resource);
	
}
