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

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThrowableContext extends Throwable {

	private static final long serialVersionUID = 1L;

	private Deque<Entry> entries = new ArrayDeque<>();

	public void add(CallStackFrame frame, String msg) {
		Entry entry = new Entry(frame, msg);
		entries.addLast(entry);
	}
	
	public ThrowableContext() {
		super("", null, false, false);
	}
	
	@Override
	public String getMessage() {
		return Stream.concat(
				Stream.of("The following lines are showing context messages per stack frame:"),
				entries.stream().map(Entry::toString)
				)
				.collect(Collectors.joining("\n\t\tat "));
	}
	
	public Collection<Entry> getEntries() {
		return entries;
	}
	
	public static class Entry {
		private CallStackFrame frame;
		private String msg;
		
		public Entry(CallStackFrame frame, String msg) {
			super();
			this.frame = frame;
			this.msg = msg;
		}
		
		public String getMsg() {
			return msg;
		}
		
		public CallStackFrame getFrame() {
			return frame;
		}
		
		@Override
		public String toString() {
			return frame.stackTraceElement.toString() + "[" + frame.stackIndex + "]: "  + msg;
		}
	}
}
