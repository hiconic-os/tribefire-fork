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
package tribefire.extension.messaging.model;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Abstract
public interface HasMessageInformation extends GenericEntity {

	EntityType<HasMessageInformation> T = EntityTypes.T(HasMessageInformation.class);

	String timestamp = "timestamp";
	String nanoTimestamp = "nanoTimestamp";
	String context = "context";

	@Name("Timestamp")
	@Description("Timestamp as a Date")
	Date getTimestamp();
	void setTimestamp(Date timestamp);

	@Name("Nano Timestamp")
	@Description("Timestamp as a long in ns")
	Long getNanoTimestamp();
	void setNanoTimestamp(Long nanoTimestamp);

	@Name("Context")
	@Description("Context which links multiple messages together")
	String getContext();
	void setContext(String context);
}
