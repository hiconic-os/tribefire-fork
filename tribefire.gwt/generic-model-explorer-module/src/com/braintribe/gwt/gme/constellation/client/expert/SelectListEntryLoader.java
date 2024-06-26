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
package com.braintribe.gwt.gme.constellation.client.expert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.SelectListConfig;
import com.braintribe.gwt.gmview.ddsarequest.client.DdsaRequestExecution;
import com.braintribe.gwt.gmview.ddsarequest.client.DdsaRequestExecution.RequestExecutionData;
import com.braintribe.gwt.gmview.util.client.GMEUtil;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.extensiondeployment.RequestProcessing;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.notification.api.NotificationFactory;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.impl.persistence.TransientPersistenceGmSession;
import com.braintribe.model.uiservice.GetSelectList;
import com.google.gwt.core.client.Scheduler;
import com.sencha.gxt.core.shared.FastMap;

/**
 * Expert responsible for evaluating the given {@link RequestProcessing} and return the list of possible values.
 * @author michel.docouto
 *
 */
public class SelectListEntryLoader implements Function<SelectListConfig, Future<List<Object>>> {
	
	private PersistenceGmSession dataSession;
	private TransientPersistenceGmSession transientSession;
	private Supplier<? extends TransientPersistenceGmSession> transientSessionSupplier;
	private Supplier<? extends NotificationFactory> notificationFactorySupplier;
	private static Map<String, List<Object>> loaderCache = new FastMap<>();
	
	/**
	 * Configures the required data session.
	 */
	@Required
	public void setDataSession(PersistenceGmSession dataSession) {
		this.dataSession = dataSession;
	}
	
	/**
	 * Configures the required transient session.
	 */
	@Required
	public void setTransientSession(TransientPersistenceGmSession transientSession) {
		this.transientSession = transientSession;
	}
	
	/**
	 * Configures the required supplier for transient sessions.
	 */
	@Required
	public void setTransientSessionSupplier(Supplier<? extends TransientPersistenceGmSession> transientSessionSupplier) {
		this.transientSessionSupplier = transientSessionSupplier;
	}
	
	/**
	 * Configures the required {@link NotificationFactory}.
	 */
	@Required
	public void setNotificationFactory(Supplier<? extends NotificationFactory> notificationFactorySupplier) {
		this.notificationFactorySupplier = notificationFactorySupplier;
	}

	@Override
	public Future<List<Object>> apply(SelectListConfig selectListConfig) {
		Future<List<Object>> future = new Future<>();
		
		RequestProcessing requestProcessing = selectListConfig.getRequestProcessing();
		GenericEntity parentEntity = selectListConfig.getParentEntity();
		
		GetSelectList request = GetSelectList.T.create();
		String domainId = GMEUtil.getDomainId(requestProcessing, dataSession);
		String serviceId = GMEUtil.getServiceId(requestProcessing);
		request.setDomainId(domainId);
		request.setServiceId(serviceId);
		request.setProperty(selectListConfig.getPropertyName());
		if (parentEntity != null) {
			request.setInstanceId(parentEntity.getId());
			request.setInstanceTypeSignature(parentEntity.entityType().getTypeSignature());
		}
		
		String cacheKey;
		if (selectListConfig.isDisableCache())
			cacheKey = null;
		else {
			cacheKey = getCacheKey(request);
			List<Object> result = loaderCache.get(cacheKey);
			if (result != null) {
				Scheduler.get().scheduleDeferred(() -> future.onSuccess(result));
				return future;
			}
		}
		
		RequestExecutionData data = new RequestExecutionData(request, dataSession, transientSession, null, transientSessionSupplier,
				notificationFactorySupplier);
		
		/*Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				List<Object> result = new ArrayList<>();
				result.add("text/plain");
				result.add("text/txt");
				result.add("text/pdf");
				
				if (!request.getDisableCache())
					loaderCache.put(getCacheKey(request), result);
				future.onSuccess(result);
			}
		});*/
		
		Future<List<Object>> execution = DdsaRequestExecution.executeRequest(data);
		execution.andThen(result -> {
			List<Object> noDuplicates;
			if (result == null)
				noDuplicates = null;
			else
				noDuplicates = result.stream().distinct().collect(Collectors.toList());

			if (!selectListConfig.isDisableCache())
				loaderCache.put(cacheKey, noDuplicates);
			future.onSuccess(noDuplicates);
		}).onError(future::onFailure);
		
		return future;
	}
	
	private String getCacheKey(GetSelectList getSelectList) {
		StringBuilder string = new StringBuilder();
		string.append(getSelectList.getDomainId()).append("_").append(getSelectList.getServiceId()).append("_").append(getSelectList.getProperty());
		Object instanceId = getSelectList.getInstanceId();
		if (instanceId != null)
			string.append("_").append(instanceId);
		String instanceTypeSignature = getSelectList.getInstanceTypeSignature();
		if (instanceTypeSignature != null)
			string.append("_").append(instanceTypeSignature);
		
		return string.toString();
	}

}
