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
package com.braintribe.web.multipart.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Random;

import com.braintribe.utils.IOTools;

class MultipartTestUtils {
	private static String[] words = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

	private MultipartTestUtils() {
		// Prevent from instantiating the class
	}

	static enum PartStreamingMethod {
		chunked, contentLengthAware, raw
	}

	static void writeRandomData(OutputStream out, int byteCount) throws IOException {
		byte buffer[] = IOTools.BUFFER_SUPPLIER_8K.get();

		Random r = new Random(0);

		int bufferBytesUsed = 0;
		for (int i = 0; i < byteCount; i++) {
			buffer[bufferBytesUsed++] = (byte) r.nextInt(255);

			if (bufferBytesUsed == IOTools.SIZE_8K) {
				out.write(buffer);
				bufferBytesUsed = 0;
			}
		}

		if (bufferBytesUsed > 0) {
			out.write(buffer, 0, bufferBytesUsed);
		}

	}

	static void writeRandomText(int minfileSize, String boundary, OutputStream out)
			throws IOException, UnsupportedEncodingException, FileNotFoundException {
		Random random = new Random(System.currentTimeMillis());
		int amountWritten = 0;
		int lineWordsWritten = 0;
		int boundariesWritten = 0;
		try (Writer writer = new OutputStreamWriter(out, "UTF-8")) {
			while (amountWritten < minfileSize) {

				if (lineWordsWritten > 20) {
					writer.write('\n');
					lineWordsWritten = 0;
				} else if (lineWordsWritten > 0) {
					writer.write(' ');
				}

				String word;

				if (random.nextInt(100) == 1) {
					int boundaryChars = boundariesWritten++ % (boundary.length() - 2);
					word = "\r\n" + boundary.substring(0, boundaryChars);
				} else {
					int index = random.nextInt(words.length);
					word = words[index];
				}

				writer.write(word);
				amountWritten += word.length();
				lineWordsWritten++;
			}
		}
	}

	static void writeRandomDataFile(File generatedFile, int byteCount) {
		try (OutputStream out = new FileOutputStream(generatedFile)) {
			writeRandomData(out, byteCount);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
