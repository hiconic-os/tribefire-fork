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

import com.braintribe.codec.CodecException;
import com.braintribe.gwt.genericmodel.client.codec.jse.JseCodec;
import com.braintribe.gwt.genericmodel.client.codec.jse.JseScriptFunctions;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.meta.GmMetaModel;
import com.google.gwt.core.client.JavaScriptObject;

import jsinterop.annotations.JsMethod;

/**
 * @author peter.gazdik
 */
public class TfJsItwTools {

	/**
	 * Expects a function compatible with the {@link JseCodec}, i.e. one that takes ("$", "P", "_") where:
	 * <ul>
	 * <li>$ comes from {@link JseScriptFunctions#create()}
	 * <li>P will be an empty instance - it contains types and properties and was introduced to support fragmentation - processing first fragment
	 * would put the values into P, next fragments can read from it.
	 * <li>_ - something with proxy
	 * </ul>
	 */
	@JsMethod(namespace = GmCoreApiInteropNamespaces.internal)
	public static void ensureModel(JavaScriptObject modelAssembler) throws CodecException {
		GmMetaModel model = JseCodec.decodeFunction(modelAssembler);

		GMF.getTypeReflection().deploy(model);
	}

}
