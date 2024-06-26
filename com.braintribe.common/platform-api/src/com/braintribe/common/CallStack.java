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

public class CallStack {
	public static CallStackFrame getCallerFrame(int callerOffset) {
		StackTraceElement frames[] = Thread.currentThread().getStackTrace();

		int index = 2 + callerOffset;

		if (frames == null || frames.length <= index) {
			return new CallStackFrame(new StackTraceElement("unkown", "unkown", null, -1), 0);
		}

		StackTraceElement element = frames[index];

		return new CallStackFrame(element, frames.length - index - 1);
	}
}
