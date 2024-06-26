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

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.reason.UnsatisfiedMaybeTunneling;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.MinLength;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

import tribefire.extension.messaging.service.reason.validation.MandatoryNotSatisfied;

/**
 * The actual message which should be sent. If the key is not set it will be enriched with a unique key by the
 * underlying implementation.
 * 
 */
public interface Message extends StandardStringIdentifiable, HasMessageInformation, HasResourceMapping {

	EntityType<Message> T = EntityTypes.T(Message.class);

	String resourceBinaryPersistence = "resourceBinaryPersistence";
	String values = "values";
	String key = "key";
	String topics = "topics";

	@Mandatory
	@Name("Resource Binary Persistence")
	@Description("Describes persistence rules for the attached resources")
	ResourceBinaryPersistence getResourceBinaryPersistence();
	void setResourceBinaryPersistence(ResourceBinaryPersistence resourceBinaryPersistence);

	@Mandatory
	@MinLength(1)
	@Name("Values")
	@Description("The actual message")
	Map<String, GenericEntity> getValues();
	void setValues(Map<String, GenericEntity> values);

	@Name("Key")
	@Description("The message key")
	GenericEntity getKey();
	void setKey(GenericEntity key);

	@Name("Topics")
	@Description("Topics the message will be sent")
	Set<String> getTopics();
	void setTopics(Set<String> topics);

	// -----------------------------------------------------------------------
	// DEFAULT METHODS
	// -----------------------------------------------------------------------
	default Resource getPersistedResourceSilent(Object id) {
		return this.getResourceMapping().get(id);
	}

	default Resource getPersistedResourceReasoned(Object id) {
		//@formatter:off
		return Optional.ofNullable(this.getResourceMapping().get(id))
				.orElseThrow(()-> new UnsatisfiedMaybeTunneling(Reasons.build(MandatoryNotSatisfied.T)
						                                      .text("No resource was mapped for resource '" + id + "'").toMaybe()));
		//@formatter:on
	}

	default boolean shouldPersist(Resource resource) {
		return this.getResourceBinaryPersistence().shouldPersist(resource.isTransient());
	}

	// Builder
	default Message topic(Set<String> topic) {
		this.setTopics(topic);
		return this;
	}

	default Message values(Map<String, GenericEntity> values) {
		this.setValues(values);
		return this;
	}
	default Message option(ResourceBinaryPersistence option) {
		this.setResourceBinaryPersistence(option);
		return this;
	}

	default void addValue(String key, GenericEntity value) {
		this.getValues().put(key, value);
	}
}
