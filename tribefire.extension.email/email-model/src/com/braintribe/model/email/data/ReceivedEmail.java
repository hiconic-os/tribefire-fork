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
package com.braintribe.model.email.data;

import java.util.Date;

import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

@SelectiveInformation("Received Email")
public interface ReceivedEmail extends Email {

	final EntityType<ReceivedEmail> T = EntityTypes.T(ReceivedEmail.class);

	String eml = "eml";
	String receivedDate = "receivedDate";
	
	@Name("Received Date")
	Date getReceivedDate();
	void setReceivedDate(Date receivedDate);

	@Name("EML Resource")
	Resource getEml();
	void setEml(Resource eml);

}
