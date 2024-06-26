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
package com.braintribe.gwt.tribefirejs.client.tools;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;

public class LongArithmetics 
{
	
	@UnsafeNativeLong
	public static native long toLong(JavaScriptObject b)/*-{
		if(typeof b == "number"){
			var d = @java.lang.Double::new(D)(b);
			b = d.@java.lang.Double::longValue()();
		}else{
			b = b.@java.lang.Long::longValue()();
		}
			return b;
	}-*/;
	
	public static Long add(Long a, JavaScriptObject b){
		return a + toLong(b);
	}
	
	public static Long sub(Long a, JavaScriptObject b){
		return a - toLong(b);
	}
	
	public static Long mul(Long a, JavaScriptObject b){
		return a * toLong(b);
	}
	
	public static Long div(Long a, JavaScriptObject b){
		return a / toLong(b);
	}
	
	public static Long pow(Long a, JavaScriptObject b){
		return (long) Math.pow(a, toLong(b));
	}
	
	public static Long sqrt(Long a){
		return (long) Math.sqrt(a);
	}
	
	public static Long lsh(Long a, int i){
		return (long) (a << i);
	}
	
	public static Long rsh(Long a, int i){
		return (long) (a >> i);
	}
	
}
