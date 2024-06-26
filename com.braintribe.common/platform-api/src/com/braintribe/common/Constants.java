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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.braintribe.utils.MapTools;

/**
 * This class may contain Java-only (i.e. GWT incompatible) code. For further information please see {@link com.braintribe.common.lcd.Constants}.
 */
public final class Constants extends com.braintribe.common.lcd.Constants {

	private Constants() {
		// no instantiation required
	}

	/**
	 * The line separators for Windows on (\r\n), Unix/Linux/MacOS (\n) and old MacOS (\r).
	 */
	public static final List<String> LINE_SEPARATORS = Collections.unmodifiableList(Arrays.asList("\r\n", "\n", "\r"));

	/**
	 * Maps from {@link #LINE_SEPARATORS line separator} to the respective regex.
	 */
	public static final Map<String, String> LINE_SEPARATORS_TO_REGEXES = Collections
			.unmodifiableMap(MapTools.getStringMap("\r\n", "\\r\\n", "\n", "\\n", "\r", "\\r"));
}
