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

import java.util.Set;
import java.util.function.Function;

import com.braintribe.codec.CodecException;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.proxy.ProxyContext;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.meta.GmMetaModel;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultDecodingContext implements GmDecodingContext {
	protected static GenericModelTypeReflection typeReflection = GMF.getTypeReflection();
	private ProxyContext proxyContext;
	private Function<Object, GmMetaModel> intrinsicModelExtractor;
	
	public DefaultDecodingContext() {
		super();
	}
	
	@Override
	public GenericModelType resolveType(String type) {
		return typeReflection.getType(type);
	}

	@Override
	public void ensureTypes(Set<String> types, AsyncCallback<Void> callback) {
		callback.onSuccess(null);
	}

	@Override
	public void ensureTypes(Set<String> types) throws CodecException {
		// NOOP
	}

	@Override
	public boolean isLenientDecode() {
		return false;
	}
	
	public void setProxyContext(ProxyContext proxyContext) {
		this.proxyContext = proxyContext;
	}
	
	@Override
	public ProxyContext getProxyContext() {
		return proxyContext;
	}

	public void setIntrinsicModelExtractor(Function<Object, GmMetaModel> intrinsicModelExtractor) {
		this.intrinsicModelExtractor = intrinsicModelExtractor;
	}

	@Override
	public Function<Object, GmMetaModel> getIntrinsicModelExtractor() {
		return intrinsicModelExtractor;
	}
	
	@Override
	public GenericEntity create(EntityType<?> entityType) {
		return entityType.createRaw();
	}
}
