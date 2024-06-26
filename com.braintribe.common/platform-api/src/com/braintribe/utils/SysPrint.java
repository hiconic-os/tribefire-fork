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
package com.braintribe.utils;

import com.braintribe.utils.lcd.NullSafe;

/**
 * DO NOT REFACTOR !!!
 *
 * Simple tool to make debugging easier.
 *
 * Print to console with the file/line information, using the same format as a stacktrace, thus your IDE makes the text different color and clickable.
 *
 * Eclipse hint: For easy use add this class to your favorites, just start typing the name of the method (sp...) and code-complete.
 *
 * DO NOT REFACTOR - refactoring could break this, as the number of frames between the caller of this class and {@link Thread#getStackTrace()} (which
 * is called from this class) is important!
 *
 * @author peter.gazdik
 */
public class SysPrint {

	/**
	 * {@code
		 StackTraceElement[0]: Thread.getStackTrace()
		 StackTraceElement[1]: SysPrint.location()
		 StackTraceElement[2]: SysPrint.spOut/spErr/callerLocation
		 StackTraceElement[3]: >> Caller <<
	 * }
	 */
	private static final int CALLER_POSITION_WHEN_RESOLVING_LOCATION = 3;

	public static void spOut(Object o) {
		System.out.println(location(0) + o);
	}

	public static void spErr(Object o) {
		System.err.println(location(0) + o);
	}

	public static void spOut(int stackShift, Object o) {
		System.out.println(location(stackShift) + o);
	}

	public static void spErr(int stackShift, Object o) {
		System.err.println(location(stackShift) + o);
	}

	public static String callerLocation(int stackShift) {
		return location(stackShift);
	}

	private static String location(int stackShift) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement caller = stackTrace[CALLER_POSITION_WHEN_RESOLVING_LOCATION + stackShift];

		String classOrFile = NullSafe.get(caller.getFileName(), caller.getClassName());
		String lineOrMethod = caller.getLineNumber() >= 0 ? "" + caller.getLineNumber() : caller.getMethodName();

		return "(" + classOrFile + ":" + lineOrMethod + ") ";
	}

}
