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

/**
 * {@link #getSeparator() Provides} the plattform dependent line separator. The main purpose of this class is to separate GWT incompatible code so
 * that it can easily be replaced (i.e. emulated) in GWT. Please just use {@link com.braintribe.utils.lcd.CommonTools#LINE_SEPARATOR} instead.
 *
 * @author michael.lafite
 */
public final class LineSeparatorProvider {

	private static final String SEPARATOR = System.getProperty("line.separator");

	private LineSeparatorProvider() {
		// no instantiation required
	}

	/**
	 * Returns the platform dependent line separator.
	 */
	public static String getSeparator() {
		return SEPARATOR;
	}

}
