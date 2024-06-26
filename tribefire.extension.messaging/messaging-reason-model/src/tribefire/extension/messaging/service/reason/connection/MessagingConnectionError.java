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
package tribefire.extension.messaging.service.reason.connection;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.messaging.service.reason.MessagingReason;

/**
 * Base for all errors related to the attached messaging system
 * 
 * Indicates that an error related to the attached messaging system appears.
 */
@Abstract
public interface MessagingConnectionError extends MessagingReason {

	EntityType<MessagingConnectionError> T = EntityTypes.T(MessagingConnectionError.class);

	String type = "type";
	String details = "details";

	@Mandatory
	@Name("Type")
	@Description("The type of the error that appeared")
	String getType();
	void setType(String type);

	@Mandatory
	@Name("Details")
	@Description("Detailed information")
	String getDetails();
	void setDetails(String details);

	// -----------------------------------------------------------------------
	// DEFAULT METHODS
	// -----------------------------------------------------------------------

	default void addThrowableInformation(Throwable t) {
		String name = t.getClass().getName();
		String message = t.getMessage();
		setType(name);
		setDetails(message);
	}
}