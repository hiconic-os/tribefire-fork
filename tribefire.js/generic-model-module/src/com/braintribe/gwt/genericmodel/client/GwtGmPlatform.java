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
package com.braintribe.gwt.genericmodel.client;

import java.util.function.Function;

import com.braintribe.gwt.genericmodel.client.reflect.AbstractGwtGenericModelTypeReflection;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.AbstractGmPlatform;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.google.gwt.core.client.GWT;

public class GwtGmPlatform extends AbstractGmPlatform {

	private static AbstractGwtGenericModelTypeReflection typeReflection;

	@Override
	public GenericModelTypeReflection getTypeReflection() {
		if (typeReflection == null) {
			typeReflection = GWT.create(GenericModelTypeReflection.class);
		}

		return typeReflection;
	}

	@Override
	public void initialize() {
		typeReflection.initialize();
	}

	@Override
	public boolean isSingleThreaded() {
		return true;
	}

	// @formatter:off
	@Override
	public String newUuid() {
		return S4() + S4() + "-" + S4() + "-4" + 
				S4().substring(0,3) + "-" + S4() + "-" + S4() + S4() + S4();
	}

	public static native String S4() /*-{
		return (((1+Math.random())*0x10000)|0).toString(16).substring(1); 
	}-*/;
	// @formatter:on

	@Override
	public <T extends GenericEntity> void registerStringifier(EntityType<T> baseType, Function<T, String> stringifier) {
		// ignore
	}

	@Override
	public String stringify(GenericEntity entity) {
		return entity.toString();
	}

}
