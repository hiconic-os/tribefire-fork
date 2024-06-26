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
package com.braintribe.common.lcd;

import java.util.ArrayList;
import java.util.List;

public class StackTraceCodec {

	public final static StackTraceCodec INSTANCE = new StackTraceCodec();

	private static final char vs = ':';
	private static final char ls = '\n';

	private StackTraceCodec() {
	}

	public String encode(StackTraceElement[] stackTraceElems) {

		if (stackTraceElems == null || stackTraceElems.length == 0) {
			return null;
		}

		StringBuilder result = new StringBuilder(stackTraceElems.length * 200);

		for (StackTraceElement elem : stackTraceElems) {

			if (elem == null || elem.getClassName() == null || elem.getMethodName() == null) {
				continue;
			}

			// @formatter:off
			result
				.append(elem.getClassName())
				.append(vs)
				.append(elem.getMethodName())
				.append(vs)
				.append(elem.getFileName())
				.append(vs)
				.append(elem.getLineNumber())
				.append(ls);
			// @formatter:on

		}

		return result.toString();
	}

	public StackTraceElement[] decode(String string) {

		if (string == null || string.isEmpty()) {
			return null;
		}

		String[] stringElems = string.split(String.valueOf(ls));

		if (stringElems == null || stringElems.length == 0) {
			return null;
		}

		List<StackTraceElement> resultElems = new ArrayList<StackTraceElement>(stringElems.length);

		String elemPattern = "\\" + vs;

		for (String elem : stringElems) {

			if (elem == null) {
				continue;
			}

			String[] elemParts = elem.split(elemPattern);

			if (elemParts.length < 4) {
				continue;
			}

			resultElems.add(new StackTraceElement(elemParts[0], elemParts[1], convertFileName(elemParts[2]), convertLineNumber(elemParts[3])));

		}

		if (resultElems.size() > 0) {
			return resultElems.toArray(new StackTraceElement[resultElems.size()]);
		}

		return null;

	}

	private static String convertFileName(String string) {

		// the file name is the only property of a StackTraceElement which can be null.

		if ("null".equals(string)) {
			return null;
		}

		return string;

	}

	private static int convertLineNumber(String string) {

		if (string == null || string.trim().isEmpty()) {
			return -1;
		}

		try {
			return Integer.parseInt(string);
		} catch (Throwable t) {
			return -1;
		}

	}

}
