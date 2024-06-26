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
package com.braintribe.codec.context;

import java.util.EmptyStackException;
import java.util.Stack;


public class CodingContext {
	private static ThreadLocal<Stack<CodingContext>> threadLocal = new ThreadLocal<Stack<CodingContext>>();
	
	@SuppressWarnings("unchecked")
	public static <T extends CodingContext> T get() {
		Stack<CodingContext> stack = threadLocal.get();
		return stack != null? (T)stack.peek(): null;
	}
	
	public static <T extends CodingContext> void push(T codingContext) {
		Stack<CodingContext> stack = threadLocal.get();
		
		if (stack == null) {
			stack = new Stack<CodingContext>();
			threadLocal.set(stack);
		}
		
		stack.push(codingContext);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends CodingContext> T pop() {
		Stack<CodingContext> stack = threadLocal.get();
		
		if (stack != null) {
			T entry = (T)stack.pop();
			if (stack.isEmpty()) {
				threadLocal.remove();
			}
			return entry;
		}
		else {
			throw new EmptyStackException();
		}
	}
}
