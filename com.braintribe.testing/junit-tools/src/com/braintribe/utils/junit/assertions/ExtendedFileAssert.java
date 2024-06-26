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
package com.braintribe.utils.junit.assertions;

import java.io.File;
import java.io.UncheckedIOException;

import org.fest.assertions.Assertions;
import org.fest.assertions.FileAssert;

import com.braintribe.utils.FileTools;

/**
 * An extension of {@link FileAssert} to provide further assertions not (yet) provided by FEST.
 *
 * @see #hasContent(String)
 * @see #hasContent(String, String)
 *
 *
 */
public class ExtendedFileAssert extends FileAssert {

	public ExtendedFileAssert(final File actual) {
		super(actual);
	}

	/**
	 * Asserts that the file content is as expected, shortcut for calling {@link #hasContent(String, String)} with <code>encoding=UTF-8</code>
	 */
	public ExtendedFileAssert hasContent(final String expected) {
		return hasContent(expected, "UTF-8");
	}

	/**
	 * Compares the content of the file under assertion to an expected String.
	 * <p/>
	 * The file-content is read using {@link FileTools#readStringFromFile(File, String)} with the specified encoding. The actual comparison of content
	 * is performed using {@link Assertions#assertThat(String)}, to re-use the diff provided in the failure-messages of that method.
	 */
	public ExtendedFileAssert hasContent(final String expected, final String encoding) {
		final String fileContent = readFileContent(encoding);
		Assertions.assertThat(fileContent).describedAs("File content differs from expected content").isEqualTo(expected);

		return (ExtendedFileAssert) this.myself;
	}

	private String readFileContent(final String encoding) {
		final String fileContent = "";
		try {
			return FileTools.readStringFromFile(this.actual, encoding);
		} catch (final UncheckedIOException e) {
			fail("Cannot check content, error while reading file '" + this.actual + "'.", e);
		}
		return fileContent;
	}
}
