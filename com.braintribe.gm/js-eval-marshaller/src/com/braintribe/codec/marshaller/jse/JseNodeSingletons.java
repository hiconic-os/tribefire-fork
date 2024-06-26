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
package com.braintribe.codec.marshaller.jse;

import com.braintribe.codec.marshaller.jse.tree.JseComment;
import com.braintribe.codec.marshaller.jse.tree.JseNode;
import com.braintribe.codec.marshaller.jse.tree.value.JseFalse;
import com.braintribe.codec.marshaller.jse.tree.value.JseNull;
import com.braintribe.codec.marshaller.jse.tree.value.JseTrue;
import com.braintribe.codec.marshaller.jse.tree.JseTmpVar;

public abstract class JseNodeSingletons {
	public static final JseNode tempVar = new JseTmpVar();
	public static final JseNode nullValue = new JseNull();
	public static final JseNode trueValue = new JseTrue();
	public static final JseNode falseValue = new JseFalse();
	public static final JseNode beginTypesComment = new JseComment("BEGIN_TYPES"); 
	public static final JseNode endTypesComment = new JseComment("END_TYPES"); 
}
