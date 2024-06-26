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
package com.braintribe.gwt.tribefirejs.client;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;

public interface TfJsNameSpaces {
	
	String TF = GmCoreApiInteropNamespaces.gm;
	String RUNTIME = TF + ".runtime";
	String REMOTE = TF + ".remote";
	String TYPES = GmCoreApiInteropNamespaces.type;
	String DATE_TOOLS = TF + ".time";
	String I18N_TOOLS = TF + ".i18n";
	String QUERY_TOOLS = TF + ".query";	
	String ERROR_TOOLS = TF + ".error";
	String MATH_TOOLS = TF + ".math";
	String META_TOOLS = TF + ".meta";
}
