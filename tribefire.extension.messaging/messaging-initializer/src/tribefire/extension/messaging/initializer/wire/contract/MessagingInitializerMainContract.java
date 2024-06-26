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
package tribefire.extension.messaging.initializer.wire.contract;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.messaging.model.meta.MessagingProperty;
import tribefire.extension.messaging.model.meta.MessagingTypeSignature;
import tribefire.extension.messaging.templates.wire.contract.MessagingTemplatesContract;

public interface MessagingInitializerMainContract extends WireSpace {

	MessagingInitializerContract initializer();

	ExistingInstancesContract existingInstances();

	RuntimePropertiesContract properties();

	CoreInstancesContract coreInstances();

	MessagingInitializerModuleDefaultsContract defaults();

	MessagingTemplatesContract messagingTemplate();

	MessagingTypeSignature testTypeSignatureMd(ManagedGmSession session);

	MessagingProperty diffPropertyMdRequest(ManagedGmSession session, EntityType<?> requestType, EntityType<?> loadedObjectType);
}
