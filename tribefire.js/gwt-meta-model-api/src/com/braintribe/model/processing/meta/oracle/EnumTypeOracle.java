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
package com.braintribe.model.processing.meta.oracle;

import java.util.List;
import java.util.stream.Stream;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.meta.info.GmEnumTypeInfo;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * @author peter.gazdik
 */
@JsType(namespace = GmCoreApiInteropNamespaces.model)
@SuppressWarnings("unusable-by-js")
public interface EnumTypeOracle extends TypeOracle {

	GmEnumType asGmEnumType();

	List<GmEnumTypeInfo> getGmEnumTypeInfos();

	EnumTypeConstants getConstants();

	EnumConstantOracle getConstant(String constantName);

	@JsMethod(name="getConstantViaGmEnumConstant")
	EnumConstantOracle getConstant(GmEnumConstant constant);

	@JsMethod(name="getConstantViaEnumConstant")
	EnumConstantOracle getConstant(Enum<?> enumValue);

	EnumConstantOracle findConstant(String constantName);

	@JsMethod(name="findConstantViaGmEnumConstant")
	EnumConstantOracle findConstant(GmEnumConstant constant);

	@JsMethod(name="findConstantViaEnumConstant")
	EnumConstantOracle findConstant(Enum<?> enumValue);

	/** returns all property MD from all GmEnumTypeInfos (those returned via getGmEnumTypeInfos()) */
	Stream<MetaData> getConstantMetaData();

	/** Qualified version of {@link #getConstantMetaData()} */
	Stream<QualifiedMetaData> getQualifiedConstantMetaData();

}
