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
package com.braintribe.model.processing.resource.persistence;

import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resourceapi.persistence.DeleteBinary;
import com.braintribe.model.resourceapi.persistence.StoreBinary;

/**
 * @author peter.gazdik
 */
public interface BinaryPersistenceListener {
	@Deprecated // TODO: Delete this method after merge of all groups was successful
	default void onStore(AccessRequestContext<StoreBinary> context, Resource storedResource) {
		onStore(context, context.getOriginalRequest(), storedResource);
	}
	
	@Deprecated // TODO: Delete this method after merge of all groups was successful
	default void onDelete(AccessRequestContext<DeleteBinary> context) {
		onDelete(context, context.getOriginalRequest());
	}

	/**
	 * Event that is called right after a new {@link Resource} is created, i.e. right before the
	 * {@link BinaryPersistence#store(AccessRequestContext)} method exits.
	 * 
	 * @param storedResource
	 *            the resource that was created, and is already attached to the correct persistence session. This is
	 *            (probably) a copy of the resource retrievable from the context's {@link StoreBinary}.
	 */
	void onStore(ServiceRequestContext context, StoreBinary request, Resource storedResource);

	/**
	 * Event that is called right after a {@link Resource} is deleted, i.e. right before the
	 * {@link BinaryPersistence#delete(AccessRequestContext)} method exits.
	 */
	void onDelete(ServiceRequestContext context, DeleteBinary request);

}
