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
package com.braintribe.model.processing.meta.editor;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.meta.info.GmEnumConstantInfo;
import com.braintribe.model.meta.info.GmEnumTypeInfo;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * @author peter.gazdik
 */
@JsType (namespace = GmCoreApiInteropNamespaces.metadata)
@SuppressWarnings("unusable-by-js")
public interface EnumTypeMetaDataEditor {

	/** return the underlying {@link GmEnumType} */
	GmEnumType getEnumType();

	/** Configures MD for current enum type. */
	EnumTypeMetaDataEditor configure(Consumer<GmEnumTypeInfo> consumer);

	/** Configures MD for given constant. */
	@JsMethod (name = "configureByConstant")
	EnumTypeMetaDataEditor configure(String constant, Consumer<GmEnumConstantInfo> consumer);

	/** Configures MD for given constant. */
	@JsMethod (name = "configureByEnum")
	EnumTypeMetaDataEditor configure(Enum<?> constant, Consumer<GmEnumConstantInfo> consumer);

	/** Configures MD for given constant. */
	@JsMethod (name = "configureByEnumInfo")
	EnumTypeMetaDataEditor configure(GmEnumConstantInfo constant, Consumer<GmEnumConstantInfo> consumer);

	/** Adds MD to {@link GmEnumTypeInfo#getMetaData()} */
	EnumTypeMetaDataEditor addMetaData(MetaData... mds);

	/** Removes MD from {@link GmEnumTypeInfo#getMetaData()} */
	EnumTypeMetaDataEditor removeMetaData(Predicate<? super MetaData> filter);

	/** Adds MD to {@link GmEnumTypeInfo#getEnumConstantMetaData()} */
	EnumTypeMetaDataEditor addConstantMetaData(MetaData... mds);

	/** Removes MD from {@link GmEnumTypeInfo#getEnumConstantMetaData()} */
	EnumTypeMetaDataEditor removeConstantMetaData(Predicate<? super MetaData> filter);

	/** Adds MD to {@link GmEnumConstantInfo#getMetaData()}, for constant/constantOverride of this enum. */
	@JsMethod (name = "addConstantMetaDataByConstant")
	EnumTypeMetaDataEditor addConstantMetaData(String constant, MetaData... mds);

	/** Removes MD from {@link GmEnumConstantInfo#getMetaData()}, for constant/constantOverride of this enum. */
	@JsMethod (name = "removeConstantMetaDataByConstant")
	EnumTypeMetaDataEditor removeConstantMetaData(String constant, Predicate<? super MetaData> filter);

	/** Adds MD to {@link GmEnumConstantInfo#getMetaData()}, for constant/constantOverride of this enum. */
	@JsMethod (name = "addConstantMetaDataByEnum")
	EnumTypeMetaDataEditor addConstantMetaData(Enum<?> constant, MetaData... mds);

	/** Removes MD from {@link GmEnumConstantInfo#getMetaData()}, for constant/constantOverride of this enum. */
	@JsMethod (name = "removeConstantMetaDataByEnum")
	EnumTypeMetaDataEditor removeConstantMetaData(Enum<?> constant, Predicate<? super MetaData> filter);

	/** Adds MD to {@link GmEnumConstantInfo#getMetaData()}, for constant/constantOverride of this enum. */
	@JsMethod (name = "addConstantMetaDataByEnumInfo")
	EnumTypeMetaDataEditor addConstantMetaData(GmEnumConstantInfo gmConstantInfo, MetaData... mds);

	/** Removes MD from {@link GmEnumConstantInfo#getMetaData()}, for constant/constantOverride of this enum. */
	@JsMethod (name = "removeConstantMetaDataByEnumInfo")
	EnumTypeMetaDataEditor removeConstantMetaData(GmEnumConstantInfo gmConstantInfo, Predicate<? super MetaData> filter);

}
