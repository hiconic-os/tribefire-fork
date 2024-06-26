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
package com.braintribe.gwt.genericmodel.client.reflect;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.reflection.BaseType;
import com.braintribe.model.generic.reflection.BooleanType;
import com.braintribe.model.generic.reflection.DateType;
import com.braintribe.model.generic.reflection.DecimalType;
import com.braintribe.model.generic.reflection.DoubleType;
import com.braintribe.model.generic.reflection.EssentialTypes;
import com.braintribe.model.generic.reflection.FloatType;
import com.braintribe.model.generic.reflection.IntegerType;
import com.braintribe.model.generic.reflection.LongType;
import com.braintribe.model.generic.reflection.StringType;

import jsinterop.annotations.JsProperty;

public interface SimpleTypesMixin {
	
	@JsProperty(name="string", namespace = GmCoreApiInteropNamespaces.type)
	StringType stringType = EssentialTypes.TYPE_STRING;
	
	@JsProperty(name="boolean", namespace = GmCoreApiInteropNamespaces.type)
	BooleanType booleanType = EssentialTypes.TYPE_BOOLEAN;
	
	@JsProperty(name="decimal", namespace = GmCoreApiInteropNamespaces.type)
	DecimalType decimalType = EssentialTypes.TYPE_DECIMAL;
	
	@JsProperty(name="double", namespace = GmCoreApiInteropNamespaces.type)
	DoubleType doubleType = EssentialTypes.TYPE_DOUBLE;
	
	@JsProperty(name="float", namespace = GmCoreApiInteropNamespaces.type)
	FloatType floatType = EssentialTypes.TYPE_FLOAT;
	
	@JsProperty(name="integer", namespace = GmCoreApiInteropNamespaces.type)
	IntegerType integerType = EssentialTypes.TYPE_INTEGER;
	
	@JsProperty(name="long", namespace = GmCoreApiInteropNamespaces.type)
	LongType longType = EssentialTypes.TYPE_LONG;
	
	@JsProperty(name="base", namespace = GmCoreApiInteropNamespaces.type)
	BaseType baseType = EssentialTypes.TYPE_OBJECT;
	
	@JsProperty(name="date", namespace = GmCoreApiInteropNamespaces.type)
	DateType dateType = EssentialTypes.TYPE_DATE;
	
}