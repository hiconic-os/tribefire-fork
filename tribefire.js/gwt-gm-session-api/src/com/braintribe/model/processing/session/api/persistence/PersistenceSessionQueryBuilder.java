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
package com.braintribe.model.processing.session.api.persistence;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.processing.session.api.managed.SessionQueryBuilder;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(namespace=GmCoreApiInteropNamespaces.session)
@SuppressWarnings("unusable-by-js")
public interface PersistenceSessionQueryBuilder extends SessionQueryBuilder {

	@Override
	@JsMethod (name = "entityByReference")
	<T extends GenericEntity> PersistenceEntityAccessBuilder<T> entity(EntityReference entityReference);

	@Override
	@JsMethod (name = "entityByTypeAndId")
	<T extends GenericEntity> PersistenceEntityAccessBuilder<T> entity(EntityType<T> entityType, Object id);

	@Override
	@JsMethod (name = "entityByTypeIdAndPartition")
	<T extends GenericEntity> PersistenceEntityAccessBuilder<T> entity(EntityType<T> entityType, Object id, String partition);

	@Override
	@JsMethod (name = "entityBySignatureAndId")
	<T extends GenericEntity> PersistenceEntityAccessBuilder<T> entity(String typeSignature, Object id);

	@Override
	@JsMethod (name = "entityBySignatureIdAndPartition")
	<T extends GenericEntity> PersistenceEntityAccessBuilder<T> entity(String typeSignature, Object id, String partition);
	
	@Override
	<T extends GenericEntity> PersistenceEntityAccessBuilder<T> entity(T entity);
	
}
