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
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.proxy.ProxyContext;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.meta.GmMetaModel;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GmDecodingContext {
	GenericModelType resolveType(String type);
	void ensureTypes(Set<String> types, AsyncCallback<Void> callback);
	void ensureTypes(Set<String> types) throws CodecException;
	boolean isLenientDecode();
	ProxyContext getProxyContext();
	Function<Object, GmMetaModel> getIntrinsicModelExtractor();
	GenericEntity create(EntityType<?> entityType);
}
