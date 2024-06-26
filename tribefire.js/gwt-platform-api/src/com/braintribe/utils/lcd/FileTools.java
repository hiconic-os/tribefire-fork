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
package com.braintribe.utils.lcd;

import java.io.File;
import java.util.regex.Matcher;

import com.braintribe.common.lcd.Numbers;
import com.braintribe.common.lcd.Pair;

/**
 * This class provides utility methods related to files, file names, paths, etc.
 *
 * @author michael.lafite
 */
public class FileTools {

	protected FileTools() {
		// nothing to do
	}

	/**
	 * Returns the file extension (i.e. the part of <code>fileNameAndExtension</code> after the last "<code>.</code>")
	 * or an empty string, if there is no extension.
	 *
	 * @see FileTools#getNameWithoutExtension(String)
	 */
	public static String getExtension(final String fileNameAndExtension) {
		final int index = fileNameAndExtension.lastIndexOf('.');
		if (index >= Numbers.ZERO) {
			return fileNameAndExtension.substring(index + 1);
		} else {
			return "";
		}
	}

	/**
	 * Returns the name without the extension (i.e. the part until the last "<code>.</code>") or the whole name (if
	 * there is no "<code>.</code>") or an empty string (if the last "<code>.</code>" is the first character).
	 *
	 * @see #getExtension(String)
	 */
	public static String getNameWithoutExtension(final String fileNameAndExtension) {
		final int index = fileNameAndExtension.lastIndexOf('.');
		if (index >= Numbers.ZERO) {
			return fileNameAndExtension.substring(0, index);
		} else {
			return fileNameAndExtension;
		}
	}

	/**
	 * Returns the file {@link #getNameWithoutExtension(String) name} and {@link #getExtension(String) extension}.
	 */
	public static Pair<String, String> getNameAndExtension(final String fileNameAndExtension) {
		return new Pair<String, String>(getNameWithoutExtension(fileNameAndExtension), getExtension(fileNameAndExtension));
	}

	/**
	 * Appends the specified <code>suffix</code> to the <code>fileName</code> (or file path). If the file name has an
	 * extension, the suffix is added before the extension.
	 */
	public static String appendSuffixBeforeExtension(final String fileName, final String suffixToAppend) {
		String fileNameWithoutExtension = getNameWithoutExtension(fileName);
		String extension = getExtension(fileName);
		String result = fileNameWithoutExtension + suffixToAppend + (CommonTools.isEmpty(extension) ? "" : "." + extension);
		return result;
	}

	/**
	 * Adds a '.' to the extension if it is not already there.
	 *
	 * @param ext
	 *            The extension with or without a dot.
	 * @return The extension with a dot or null, if the parameter is null.
	 */
	public static String addDotToExtension(String ext) {
		if (ext == null) {
			return null;
		}

		if (ext.startsWith(".")) {
			return ext;
		}

		return "." + ext;
	}

	public static String getName(String path) {
		if (path == null) {
			return null;
		}
		int indexSlash = path.lastIndexOf('/');
		int indexBackslash = path.lastIndexOf('\\');
		int index = Math.max(indexSlash, indexBackslash);
		if (index != -1 && index < path.length() - 1) {
			String name = path.substring(index + 1);
			return name;
		} else {
			return path;
		}
	}

	/**
	 * Transforms given file path to a one valid in current environment (OS). Basically it replaces all the '\' and '/'
	 * characters with File.separatorChar. We assume path is not <tt>null</tt>.
	 */
	public static String sanitizePath(String path) {
		return path.replaceAll("[\\\\/]", Matcher.quoteReplacement(File.separator));
	}

}
