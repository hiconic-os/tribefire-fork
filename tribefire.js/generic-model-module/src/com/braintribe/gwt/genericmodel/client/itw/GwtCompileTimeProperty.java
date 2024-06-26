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
package com.braintribe.gwt.genericmodel.client.itw;

import static com.braintribe.gwt.genericmodel.client.itw.ScriptOnlyItwTools.eval;

import java.util.function.Supplier;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;

/**
 * @author peter.gazdik
 */
public class GwtCompileTimeProperty extends GwtScriptProperty {

	private EntityType<?> declaringType;
	private Supplier<?> initializerSupplier;

	public GwtCompileTimeProperty(String propertyName, boolean nullable, boolean confidential) {
		super(propertyName, nullable, confidential);
		setAccessors();
	}

	public void configure(GenericModelType type, EntityType<?> declaringType, Object initializer) {
		configure(type, declaringType, null);
		setInitializer(initializer);
	}

	public void configure(GenericModelType type, EntityType<?> declaringType, Supplier<?> initializerSupplier) {
		setPropertyType(type);
		this.declaringType = declaringType;
		this.initializerSupplier = initializerSupplier;
	}

	@Override
	public EntityType<?> getDeclaringType() {
		return declaringType;
	}

	@Override
	public Object getInitializer() {
		if (initializer == null && initializerSupplier != null) {
			initializer = initializerSupplier.get();
			initializerSupplier = null;
		}

		return initializer;
	}

	private void setAccessors() {
		String getDirectSource = "(function(e){return e." + getFieldName() + ";})";
		String setDirectSource = "(function(e,v){e." + getFieldName() + "=v;})";

		ScriptOnlyItwTools.setProperty(this, RuntimeMethodNames.instance.propertyGetDirectUnsafe(), eval(getDirectSource));
		ScriptOnlyItwTools.setProperty(this, RuntimeMethodNames.instance.propertySetDirectUnsafe(), eval(setDirectSource));
	}

}
