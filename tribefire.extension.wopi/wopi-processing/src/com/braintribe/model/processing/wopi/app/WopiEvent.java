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
package com.braintribe.model.processing.wopi.app;

import java.util.regex.Pattern;

/**
 * 
 * Getting information if file/folder/Content/Children based on URL information
 */
public enum WopiEvent {

	/**
	 * Provides access to information about a file and allows for file-level operations.
	 */
	FILES("^\\/files\\/(?<id>[^\\/]+)$"),

	/**
	 * Provides access to information about a folder and allows for folder level operations.
	 */
	FOLDERS("^\\/folders\\/(?<id>[^\\/]+)$"),

	/**
	 * Provides access to operations that get and update the contents of a file.
	 */
	CONTENTS("^\\/files\\/(?<id>[^\\/]+)\\/contents$"),

	/**
	 * Provides access to the files and folders in a folder.
	 */
	CHILDREN("^\\/folders\\/(?<id>[^\\/]+)\\/children$"),

	// -------------

	RESOURCE("^/tf-resource.*$");

	private final Pattern pattern;

	private WopiEvent(String regex) {
		pattern = Pattern.compile(regex);
	}

	public Pattern pattern() {
		return pattern;
	}

	@Override
	public String toString() {
		return String.format("%s(\"%s\")", super.toString(), pattern);
	}

	public static WopiEvent eventOf(String input) {
		for (WopiEvent event : values()) {
			if (event.pattern.matcher(input).matches()) {
				return event;
			}
		}
		return null;
	}
}