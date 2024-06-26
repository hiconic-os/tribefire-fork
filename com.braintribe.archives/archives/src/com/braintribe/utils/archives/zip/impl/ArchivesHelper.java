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
package com.braintribe.utils.archives.zip.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.braintribe.utils.IOTools;


/**
 * simple helper functions 
 * 
 * @author Pit
 *
 */
public class ArchivesHelper {
	/**
	 * pump from one stream to the other 
	 * @param inputStream - the {@link InputStream} to read from 
	 * @param outputStream - the {@link OutputStream} to write to 
	 * @throws IOException - thrown if anything goes wrong
	 */
	public static void pump(InputStream inputStream, OutputStream outputStream) throws IOException {
		int bufferSize = IOTools.SIZE_64K;
		byte[] buffer = new byte[bufferSize];

		int count;
		while ((count = inputStream.read(buffer)) != -1) { 
			outputStream.write(buffer, 0, count);
		}
		outputStream.flush();
	}
	
	/**
	 * pump from one ZIP stream to the other 
	 * @param inputStream - the {@link ZipInputStream} to read from 
	 * @param outputStream - the {@link ZipOutputStream} to write to 
	 * @throws IOException - thrown if anything goes wrong 
	 */
	public static void pump( ZipInputStream inputStream, ZipOutputStream outputStream) throws IOException {
		ZipEntry zipEntry = null;
		while ((zipEntry = inputStream.getNextEntry()) != null) {
			outputStream.putNextEntry(zipEntry);
			pump( (InputStream) inputStream, (OutputStream) outputStream);
		}
		outputStream.flush();
		outputStream.finish();
	}
}
