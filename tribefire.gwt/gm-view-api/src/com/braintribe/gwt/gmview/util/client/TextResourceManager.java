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
package com.braintribe.gwt.gmview.util.client;

import com.braintribe.gwt.async.client.AsyncCallbacks;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.fileapi.client.Blob;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.ResourceSource;
import com.braintribe.model.resource.source.StringSource;
import com.braintribe.processing.async.api.AsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.xhr.client.XMLHttpRequest;

/**
 * Expert responsible for reading a {@link Resource} as text, and sending a String for writing back to the {@link Resource}.
 * @author michel.docouto
 */
public class TextResourceManager {
	
	public static Future<String> retrieveResourceContent(Resource resource) {
		Future<String> future = new Future<>();
		
		Property resourceSourceProperty = resource.entityType().getProperty("resourceSource");
		if (!GMEUtil.isPropertyAbsent(resource, resourceSourceProperty))
			getResourceContentFromResourceSource(resource, future);
		else if (!(resource.session() instanceof PersistenceGmSession))
			future.onFailure(new RuntimeException("The resource session is not a PersistenceGmSession"));
		else {
			GMEUtil.loadAbsentProperty(resource, resource.entityType(), resourceSourceProperty, (PersistenceGmSession) resource.session(), null, null,
					null).load(
							AsyncCallbacks.of(//
									success -> getResourceContentFromResourceSource(resource, future), //
									future::onFailure));
		}
		
		return future;
	}
	
	public static Future<Resource> saveResourceContent(String content, Resource resource, Property parentProperty, GenericEntity parentEntity) {
		if (!(resource.session() instanceof PersistenceGmSession)) {
			Future<Resource> future = new Future<>();
			future.onFailure(new RuntimeException("The resource session is not a PersistenceGmSession"));
			return future;
		}
		
		PersistenceGmSession session = (PersistenceGmSession) resource.session();
		if (resource.getResourceSource() == null) {
			StringSource source = session.create(StringSource.T);
			resource.setResourceSource(source);
		}
		
		if (resource.getResourceSource() instanceof StringSource) {
			StringSource source = (StringSource) resource.getResourceSource();
			source.setContent(content);
			return new Future<>(resource);
		}
		
		EntityType<? extends ResourceSource> sourceType = resource.getResourceSource() != null ? resource.getResourceSource().entityType() : null;

		Blob blob = Blob.createFromString(content, resource.getMimeType());
		Future<Resource> future = new Future<>();
		session.resources() //
		.update(resource) //
		.name(resource.getName()) //
		.sourceType(sourceType) //
		.tags(resource.getTags()) //
		.specification(resource.getSpecification()) //
		.withBlob(blob) //
		.andThen(updatedResource -> { //
			session.merge().adoptUnexposed(true).suspendHistory(true).doFor(updatedResource, AsyncCallback.of(adoptedResource -> { //
				parentProperty.set(parentEntity, adoptedResource);
				future.onSuccess(adoptedResource);
			}, future::onFailure));
		}).onError(future::onFailure);
		
		return future;
	}
	
	private static void getResourceContentFromResourceSource(Resource resource, Future<String> future) {
		ResourceSource source = resource.getResourceSource();
		if (!(source instanceof StringSource))
			handleResourceDownload(resource, future);
		else {
			String content = ((StringSource) source).getContent();
			Scheduler.get().scheduleDeferred(() -> future.onSuccess(content));
		}
	}
	
	private static void handleResourceDownload(Resource resource, Future<String> future) {
		if (!(resource.session() instanceof PersistenceGmSession)) {
			future.onFailure(new RuntimeException("The resource session is not a PersistenceGmSession"));
			return;
		}
		
		PersistenceGmSession session = (PersistenceGmSession) resource.session();
		String resourceUrl = session.resources().url(resource).download(false).asString();
		
		XMLHttpRequest request = XMLHttpRequest.create();
		request.open("get", resourceUrl);
		request.setRequestHeader("Accept", "gm/jse,text/plain");
		request.setRequestHeader("Cache-Control", "no-cache, no-store, max-age=0");
		
		request.setOnReadyStateChange(xhr -> {
			if (xhr.getReadyState() != XMLHttpRequest.DONE || xhr.getStatus() != 200)
				return;
			
			String content = xhr.getResponseText();
			future.onSuccess(content);
		});			
		request.send();
	}

}
