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
package com.braintribe.gwt.tribefirejs.client.remote;

import java.util.function.Supplier;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.persistence.AccessDescriptor;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.usersession.UserSession;
import com.braintribe.processing.async.api.JsPromise;

import jsinterop.annotations.JsType;

@JsType(namespace = GmCoreApiInteropNamespaces.remote)
@SuppressWarnings("unusable-by-js")
public interface ServicesSession extends ServicesConnection {

	String sessionId();

	UserSession userSession();

	ModelAccessoryBuilder modelAccessory(GmMetaModel model);

	JsPromise<Supplier<PersistenceGmSession>> accessSessionFactory(String accessId);
	SessionFactoryBuilder accessSessionFactoryBuilder(AccessDescriptor accessDescriptor);

	JsPromise<Supplier<PersistenceGmSession>> serviceSessionFactory(String domainId);
	SessionFactoryBuilder serviceSessionFactoryBuilder(String domainId, GmMetaModel model);

	<T> JsPromise<T> decodeJse(String jseValue);

}
