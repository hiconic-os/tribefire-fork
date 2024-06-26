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
package com.braintribe.model.access.smood.collaboration.deployment;

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static java.util.Collections.emptyList;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Objects;

import com.braintribe.logging.Logger;
import com.braintribe.model.accessapi.AccessRequest;
import com.braintribe.model.processing.accessrequest.api.AccessRequestContext;
import com.braintribe.model.processing.resource.persistence.BinaryPersistenceEventSource;
import com.braintribe.model.processing.resource.persistence.BinaryPersistenceListener;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resourceapi.persistence.BinaryPersistenceRequest;
import com.braintribe.model.resourceapi.persistence.DeleteBinary;
import com.braintribe.model.resourceapi.persistence.StoreBinary;

/**
 * @author peter.gazdik
 */
public class TestFileSystemBinaryProcessor implements BinaryPersistenceEventSource {

	private static final Logger log = Logger.getLogger(TestFileSystemBinaryProcessor.class);

	private List<BinaryPersistenceListener> persistenceListeners = emptyList();

	@Override
	public synchronized void addPersistenceListener(BinaryPersistenceListener listener) {
		Objects.requireNonNull(listener, "listener must not be null");

		List<BinaryPersistenceListener> newListeners = newList(persistenceListeners);
		newListeners.add(listener);

		persistenceListeners = newListeners;
	}

	@Override
	public synchronized void removePersistenceListener(BinaryPersistenceListener listener) {
		Objects.requireNonNull(listener, "listener must not be null");

		List<BinaryPersistenceListener> newListeners = newList(persistenceListeners);
		newListeners.remove(listener);

		persistenceListeners = newListeners;
	}

	public void onStore(String accessId, Resource resource) {
		StoreBinary request = StoreBinary.T.create();
		request.setDomainId(accessId);
		request.setCreateFrom(resource);

		notifyListenersOnStore(newAccessRequestContext(request), resource);
	}

	private void notifyListenersOnStore(AccessRequestContext<StoreBinary> context, Resource managedResource) {
		for (BinaryPersistenceListener listener : persistenceListeners) {
			try {
				listener.onStore(context, context.getOriginalRequest(), managedResource);

			} catch (Exception e) {
				log.warn("BinaryPersistenceListener threw an exception on store event.", e);
				e.printStackTrace();
			}
		}
	}

	public void onDelete(String accessId, Resource resource) {
		DeleteBinary request = DeleteBinary.T.create();
		request.setDomainId(accessId);
		request.setResource(resource);

		notifyListenersOnDelete(newAccessRequestContext(request));
	}

	private void notifyListenersOnDelete(AccessRequestContext<DeleteBinary> context) {
		for (BinaryPersistenceListener listener : persistenceListeners) {
			try {
				listener.onDelete(context, context.getOriginalRequest());

			} catch (Exception e) {
				log.warn("BinaryPersistenceListener threw an exception on store event.", e);
				e.printStackTrace();
			}
		}
	}

	private <P extends AccessRequest> AccessRequestContext<P> newAccessRequestContext(BinaryPersistenceRequest request) {
		return (AccessRequestContext<P>) Proxy.newProxyInstance( //
				getClass().getClassLoader(), //
				new Class<?>[] { AccessRequestContext.class }, //
				(proxy, method, args) -> handleInvoke(method, request));
	}

	private Object handleInvoke(Method method, BinaryPersistenceRequest request) {
		if (method.getName().equals("getOriginalRequest"))
			return request;
		else
			throw new UnsupportedOperationException("Method " + method.getName() + " is not supported by this proxy!");
	}

}
