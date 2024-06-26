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
package com.braintribe.common;

/**
 * This exists only to allow dumb copying of code for JVM (i.e. copying with this being used doesn't lead to compilation errors).
 * <p>
 * This cannot be implemented in GWT as it needs access to current thread's stacktrace.
 * 
 * @author peter.gazdik
 */
public class DeprecatedCode {

	public static void logWarn() {
		// NO OP
	}

	public static void printWarn() {
		// NO OP
	}

}
