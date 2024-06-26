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

import java.util.List;

import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.MinLength;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * The {@link Envelop} holds multiple {@link Message}s. It can globally set for all the attached {@link Message}s the
 * information from {@link HasMessageInformation}
 * 
 * <ul>
 * <li>{@link HasMessageInformation#getTimestamp()}</li>
 * <li>{@link HasMessageInformation#getNanoTimestamp()}</li>
 * <li>{@link HasMessageInformation#getContext()}</li>
 * </ul>
 * 
 * The {@link Envelop} itself won't be sent to the underlying messaging system but each {@link Message} itself - the
 * {@link Envelop} is only a container for easier sending multiple messages
 */
public interface Envelop extends StandardStringIdentifiable, HasMessageInformation {

	EntityType<Envelop> T = EntityTypes.T(Envelop.class);

	String messages = "messages";

	@Mandatory
	@MinLength(1)
	@Name("Messages")
	@Description("List of 'messages'")
	List<Message> getMessages();
	void setMessages(List<Message> messages);

	// -----------------------------------------------------------------------
	// DEFAULT METHODS
	// -----------------------------------------------------------------------

	static Envelop create(Message message) {
		Envelop envelop = Envelop.T.create();
		envelop.getMessages().add(message);
		return envelop;
	}
}
