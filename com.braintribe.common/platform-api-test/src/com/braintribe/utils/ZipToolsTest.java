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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

import com.braintribe.common.lcd.Numbers;
import com.braintribe.exception.Exceptions;

public class ZipToolsTest {

	@Test
	public void testNormalZip() throws Exception {

		String testString = "Test String";

		File zipFile = File.createTempFile("test", ".zip");
		File tempDir = null;
		try {
			try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {

				ZipEntry e = new ZipEntry("test/mytest.txt");
				out.putNextEntry(e);

				byte[] data = testString.getBytes(StandardCharsets.UTF_8);
				out.write(data, 0, data.length);
				out.closeEntry();
			}

			tempDir = FileTools.createNewTempDir(UUID.randomUUID().toString());
			ZipTools.unzip(zipFile, tempDir);

			File testFile = new File(tempDir, "test/mytest.txt");
			String actual = IOTools.slurp(testFile, "UTF-8");

			assertThat(actual).isEqualTo(testString);

		} finally {
			FileTools.deleteFileSilently(zipFile);
			FileTools.deleteDirectoryRecursively(tempDir);
		}
	}

	@Test
	public void testUnallowedRelativePathZip() throws Exception {

		String testString = "Test String";

		File zipFile = File.createTempFile("test", ".zip");
		File tempDir = null;
		try {
			try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {

				ZipEntry e = new ZipEntry("../mytest.txt");
				out.putNextEntry(e);

				byte[] data = testString.getBytes(StandardCharsets.UTF_8);
				out.write(data, 0, data.length);
				out.closeEntry();
			}

			tempDir = FileTools.createNewTempDir(UUID.randomUUID().toString());
			try {
				ZipTools.unzip(zipFile, tempDir);
				fail("The relative path in the ZIP file should have provoked an exception.");
			} catch (Exception expected) {
				// Nothing to do; all well
			}

		} finally {
			FileTools.deleteFileSilently(zipFile);
			FileTools.deleteDirectoryRecursively(tempDir);
		}
	}

	@Test
	public void testZipBomb() throws Exception {
		int maxEntries = 10000;
		long maxUnzippedTotalLength = Numbers.MEGABYTE * maxEntries;
		long maxUnzippedEntryLength = Numbers.MEGABYTE;
		double thresholdRatio = 100d;

		AtomicInteger entries = new AtomicInteger(0);
		AtomicLong bytesRead = new AtomicLong();
		try (InputStream in = new FileInputStream("res/zip/zip-bomb-do-not-extract.zip")) {
			ZipTools.processZipSafely(in, e -> {
				System.out.println("Processing entry " + e.getName());
				try (InputStream entryInputStream = e.getInputStream()) {
					entries.incrementAndGet();
					int readBytes = 0;
					byte[] buffer = new byte[2048];
					while ((readBytes = entryInputStream.read(buffer)) != -1) {
						bytesRead.addAndGet(readBytes);
					}
				} catch (IOException ioe) {
					throw Exceptions.unchecked(ioe, "Error while reading entry " + e);
				}
			}, maxEntries, maxUnzippedTotalLength, maxUnzippedEntryLength, thresholdRatio);
		} catch (IndexOutOfBoundsException expected) {
			System.out.println("Got expected IndexOutOfBoundsException exception: " + expected.getMessage());
		}
		System.out.println("Read " + entries.get() + " entries with total bytes: " + bytesRead.get());
	}

	@Test
	public void testNormalZipSafely() throws Exception {
		int maxEntries = 10000;
		long maxUnzippedTotalLength = Numbers.MEGABYTE * maxEntries;
		long maxUnzippedEntryLength = Numbers.MEGABYTE;
		double thresholdRatio = 100d;

		AtomicInteger entries = new AtomicInteger(0);
		AtomicLong bytesRead = new AtomicLong();
		try (InputStream in = new FileInputStream("res/zip/test.zip")) {
			ZipTools.processZipSafely(in, e -> {
				System.out.println("Processing entry " + e.getName());
				try (InputStream entryInputStream = e.getInputStream()) {
					entries.incrementAndGet();
					int readBytes = 0;
					byte[] buffer = new byte[2048];
					while ((readBytes = entryInputStream.read(buffer)) != -1) {
						bytesRead.addAndGet(readBytes);
					}
				} catch (IOException ioe) {
					throw Exceptions.unchecked(ioe, "Error while reading entry " + e);
				}
			}, maxEntries, maxUnzippedTotalLength, maxUnzippedEntryLength, thresholdRatio);
		}
		System.out.println("Read " + entries.get() + " entries with total bytes: " + bytesRead.get());
	}
}
