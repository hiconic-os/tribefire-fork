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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Improved version of {@link com.braintribe.utils.lcd.CustomToStringBuilder} that also supports arrays with {@link Class#getComponentType() primitive
 * component type} (not GWT compatible).
 *
 * @author michael.lafite
 */
@SuppressFBWarnings("NM_SAME_SIMPLE_NAME_AS_SUPERCLASS")
public class CustomToStringBuilder extends com.braintribe.utils.lcd.CustomToStringBuilder {

	private static final CustomToStringBuilder DEFAULT_INSTANCE = new CustomToStringBuilder();

	public CustomToStringBuilder() {
		this(null);
	}

	public CustomToStringBuilder(final CustomStringRepresentationProvider customStringRepresentationProvider) {
		super(customStringRepresentationProvider);
	}

	public static CustomToStringBuilder instance() {
		return DEFAULT_INSTANCE;
	}

	/**
	 * (Also) properly handles arrays with a primitive component types.
	 */
	@Override
	protected String arrayToStringAfterNullCheck(final Object array) {
		CommonTools.assertIsArray(array);
		return collectionToStringAfterNullCheck(CommonTools.arrayToList(array));
	}
}
