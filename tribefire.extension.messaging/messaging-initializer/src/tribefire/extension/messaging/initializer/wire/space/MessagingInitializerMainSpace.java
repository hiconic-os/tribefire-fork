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
package tribefire.extension.messaging.initializer.wire.space;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.messaging.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.messaging.initializer.wire.contract.MessagingInitializerContract;
import tribefire.extension.messaging.initializer.wire.contract.MessagingInitializerMainContract;
import tribefire.extension.messaging.initializer.wire.contract.MessagingInitializerModuleDefaultsContract;
import tribefire.extension.messaging.initializer.wire.contract.RuntimePropertiesContract;
import tribefire.extension.messaging.model.meta.MessagingProperty;
import tribefire.extension.messaging.model.meta.MessagingTypeSignature;
import tribefire.extension.messaging.model.meta.RelatedObjectType;
import tribefire.extension.messaging.templates.wire.contract.MessagingTemplatesContract;

@Managed
public class MessagingInitializerMainSpace implements MessagingInitializerMainContract {

	@Import
	private MessagingInitializerContract initializer;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private RuntimePropertiesContract properties;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private MessagingTemplatesContract messagingTemplate;

	@Import
	private MessagingInitializerModuleDefaultsContract defaults;

	@Override
	public MessagingInitializerContract initializer() {
		return initializer;
	}

	@Override
	public ExistingInstancesContract existingInstances() {
		return existingInstances;
	}

	@Override
	public RuntimePropertiesContract properties() {
		return properties;
	}

	@Override
	public CoreInstancesContract coreInstances() {
		return coreInstances;
	}

	@Override
	public MessagingTemplatesContract messagingTemplate() {
		return messagingTemplate;
	}

	@Override
	public MessagingInitializerModuleDefaultsContract defaults() {
		return defaults;
	}

	@Override
	@Managed
	public MessagingTypeSignature testTypeSignatureMd(ManagedGmSession session) {
		MessagingTypeSignature bean = session.create(MessagingTypeSignature.T);
		bean.setIdObjectType(RelatedObjectType.REQUEST);
		return bean;
	}

	@Override
	@Managed
	public MessagingProperty diffPropertyMdRequest(ManagedGmSession session, EntityType<?> requestType, EntityType<?> loadedObjectType) {
		MessagingProperty md = session.create(MessagingProperty.T);
		md.setGlobalId("wire://AdxInitializerWireModule/AdxInitializerDataSpace/" + requestType.getTypeName() + "/" + loadedObjectType.getTypeName());
		md.setGetterEntityType(requestType.getTypeSignature());
		md.setLoadedObjectType(loadedObjectType.getTypeSignature());
		return md;
	}

}
