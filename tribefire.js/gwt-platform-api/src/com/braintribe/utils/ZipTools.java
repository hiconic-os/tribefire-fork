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

import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.braintribe.exception.Exceptions;

/**
 * @author peter.gazdik
 */
public class ZipTools {

	public static void unzip(File zipFile, File targetDir) {
		unzip(zipFile, targetDir, null);
	}

	public static void unzip(File zipFile, File defaultTargetDir, Function<String, File> targetMapper) {
		requireNonNull(zipFile, "zipFile must not be null.");

		try (FileInputStream fis = new FileInputStream(zipFile)) {
			unzip(fis, defaultTargetDir, targetMapper, zipFile.getAbsolutePath());

		} catch(Exception e) {
			throw Exceptions.unchecked(e, "Error while unpacking zip: " + zipFile.getAbsolutePath());
		}
	}

	public static void unzip(InputStream in, File targetDir, Function<String, File> mapper) {
		unzip(in, targetDir, mapper, null);
	}
	
	public static void unzip(InputStream in, File defaultTargetDir, Function<String, File> targetMapper, String context) {
		FileTools.ensureFolderExists(requireNonNull(defaultTargetDir, "The targetDir must not be null."));

		try (ZipInputStream zin = new ZipInputStream(new BufferedInputStream(in))) {
			ZipEntry zipEntry = null;

			while ((zipEntry = zin.getNextEntry()) != null) {
				String slashedPathName = zipEntry.getName();

				File targetFile = new File(defaultTargetDir, slashedPathName);

				if (!FileTools.isInSubDirectory(defaultTargetDir, targetFile))
					throw new RuntimeException("The target file " + targetFile.getAbsolutePath() + " is not within the target folder "
							+ defaultTargetDir.getAbsolutePath() + " (entry name: " + slashedPathName + "). This is not allowed.");

				if (targetMapper != null)
					targetFile = targetMapper.apply(slashedPathName);

				if (targetFile == null)
					targetFile = new File(defaultTargetDir, slashedPathName);

				
				if (zipEntry.isDirectory()) {
					// create directory because it maybe empty and it would be an information loss otherwise
					targetFile.mkdirs();
				} else {
					targetFile.getParentFile().mkdirs();

					try (OutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile))) {
						IOTools.transferBytes(zin, out, IOTools.BUFFER_SUPPLIER_8K);
					}
				}

				zin.closeEntry();
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "Error while unpacking zip "+context);
		}
	}
}
