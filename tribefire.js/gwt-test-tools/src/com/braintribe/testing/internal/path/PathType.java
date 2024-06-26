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
package com.braintribe.testing.internal.path;

import java.io.File;
import java.nio.file.Path;

/**
 * ATTENTION: this class will be moved soon, which means the package name will change!<br>
 * <p>
 * Specifies whether a path is (supposed to be) a file or directory.
 *
 * @author michael.lafite
 */
public enum PathType {
	/**
	 * Indicates that a path represents a regular file.
	 */
	File,
	/**
	 * Indicates that a path represents a directory.
	 */
	Directory,
	/**
	 * Indicates that the type of a path doesn't matter. This obviously doesn't work in combination with methods that
	 * are supposed to actually create a path on the file system, but it can be used with methods that only build the
	 * path or {@link PathExistence check for path existence}.
	 */
	Any;

	/**
	 * Returns the {@link PathType} for the given <code>path</code>.
	 *
	 * @throws IllegalArgumentException
	 *             if the <code>path</code> doesn't exist or if it does exist but the type cannot be determined (e.g.
	 *             for special links, etc.).
	 */
	public static PathType get(Path path) {
		File file = path.toFile();
		PathType result = null;
		if (file.exists()) {
			if (file.isFile()) {
				result = PathType.File;
			} else if (file.isDirectory()) {
				result = PathType.Directory;
			} else {
				throw new IllegalArgumentException("Cannot get type for path " + path.toString() + ", since it is not a regular file or directory!");
			}
		} else {
			throw new IllegalArgumentException("Cannot get type for path " + path.toString() + ", since it doesn't exist!");
		}
		return result;
	}
}
