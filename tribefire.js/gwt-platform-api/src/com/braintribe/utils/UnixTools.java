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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.stream.Stream;

import com.braintribe.exception.Exceptions;

/**
 * @author peter.gazdik
 */
public class UnixTools {

	private static final String DEFAULT_SH_PERMISSIONS = "rwxr--r--";

	/**
	 * Finds all "*.sh" files in given directory and sets the default sh permissions, which allow the owner to do
	 * anything and others to read the file.
	 * <p>
	 * This might seem a little random, but as of now, there are two usage of this class, and both use this method.
	 */
	public static void setDefaultShPermissions(File baseDir) {
		File[] shFiles = baseDir.listFiles(f -> f.getName().endsWith(".sh"));

		setUnixFilePermissions(Stream.of(shFiles), DEFAULT_SH_PERMISSIONS);
	}

	public static void setUnixFilePermissions(Stream<File> files, String permissions) {
		Set<PosixFilePermission> _permissions = PosixFilePermissions.fromString(permissions);

		files.forEach(f -> setUnixFilePermissions(f, _permissions));
	}

	public static void setUnixFilePermissions(File file, String permissions) {
		setUnixFilePermissions(file, PosixFilePermissions.fromString(permissions));
	}

	public static void setUnixFilePermissions(File file, Set<PosixFilePermission> permissions) {
		try {
			Files.setPosixFilePermissions(file.toPath(), permissions);

		} catch (Exception e) {
			throw Exceptions.unchecked(e, "Error while setting file permissions for " + file.getAbsolutePath());
		}
	}

}
