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
package tribefire.extension.messaging.model.meta;

import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.EntityTypeMetaData;

/**
 * Contains information required to inform Messaging aspect whether the id(s) should be loaded from 'request' or
 * 'expert's response'
 */
public interface MessagingTypeSignature extends EntityTypeMetaData {
	EntityType<MessagingTypeSignature> T = EntityTypes.T(MessagingTypeSignature.class);

	/* Type of object containing identification to load the object state using Property from MessagingPropertyMd
	 * (Request/Response) */
	@Mandatory
	RelatedObjectType getIdObjectType();
	void setIdObjectType(RelatedObjectType idObjectType);
}
