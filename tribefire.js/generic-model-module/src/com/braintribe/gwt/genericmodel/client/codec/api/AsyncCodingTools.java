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
package com.braintribe.gwt.genericmodel.client.codec.api;

import java.util.function.Function;

import com.braintribe.codec.CodecException;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.proxy.ProxyContext;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.processing.async.api.AsyncCallback;
import com.google.gwt.core.client.Scheduler;

public abstract class AsyncCodingTools {
	
	public static <T> void completeAssembly(final GmDecodingContext gmDecodingContext, final T assembly, final Future<T> future) {
		final Function<Object, GmMetaModel> intrinsicModelExtractor = gmDecodingContext.getIntrinsicModelExtractor();
		
		com.braintribe.processing.async.api.AsyncCallback<Void> finalStep = AsyncCallback.of( //
				value -> {
					ProxyContext proxyContext = gmDecodingContext.getProxyContext();
					if (proxyContext != null && intrinsicModelExtractor != null)
						proxyContext.resolveProxiesAndApply(AsyncCallback.of(v -> scheduleSuccessNotification(assembly, future), future::onFailure));
					else {
						// TODO: when the proxyContext is null, nothing was done. The client was waiting forever. Please
						// investigate if there is no further things to be done here.
						scheduleSuccessNotification(assembly, future);
					}
				}, future::onFailure);
		
		if (intrinsicModelExtractor == null)
			finalStep.onSuccess(null);
		else {
			GmMetaModel model;
			try {
				model = intrinsicModelExtractor.apply(assembly);
			} catch (RuntimeException e) {
				future.onFailure(new CodecException("Error while extracting intrinsic model for deployment", e));
				return;
			}
			GMF.getTypeReflection().deploy(model, finalStep);
		}
	}
	
	private static <X> void scheduleSuccessNotification(final X assembly, final Future<X> future) {
		Scheduler.get().scheduleDeferred(() -> future.onSuccess(assembly));
	}
	
	public static <T> T completeAssembly(final GmDecodingContext gmDecodingContext, final T assembly) throws CodecException {
		Function<Object, GmMetaModel> intrinsicModelExtractor = gmDecodingContext.getIntrinsicModelExtractor();
		
		if (intrinsicModelExtractor == null)
			return assembly;
		
		try {
			GmMetaModel model = intrinsicModelExtractor.apply(assembly);
			GMF.getTypeReflection().deploy(model);
		} catch (Exception e) {
			throw new CodecException("Error while trying to deploy assembly intrinsic model", e);
		}

		ProxyContext proxyContext = gmDecodingContext.getProxyContext();
		if (proxyContext != null)
			proxyContext.resolveProxiesAndApply();
		
		return assembly;
	}
}
