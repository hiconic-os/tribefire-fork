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
package com.braintribe.codec.marshaller.html;

public class HtmlMarshallerFreemarkerSupport {

	public String toString(Object object) {
		return object != null ? "\"" + object.toString() + "\"": "(null)";
	}
	
	public String getPackageName(String typeSignature) {
		int lastDotIndex = typeSignature.lastIndexOf('.');
		
		if (lastDotIndex == -1) {
			return null;
		}
		
		return typeSignature.substring(0, lastDotIndex);
	}
	
	public String getShortName(String typeSignature) {
		int lastDotIndex = typeSignature.lastIndexOf('.');
		
		if (lastDotIndex == -1)
			return typeSignature;
		
		return typeSignature.substring(lastDotIndex + 1);
	}
	
}
