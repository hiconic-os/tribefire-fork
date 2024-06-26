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
package com.braintribe.testing.junit.assertions.assertj.core.api;

import java.io.File;

import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.internal.Objects;

import com.braintribe.logging.Logger;
import com.braintribe.utils.FileTools;

/**
 * Provides custom {@link File} assertions.
 *
 * @author michael.lafite
 */
public class ExtendedFileAssert extends AbstractFileAssert<ExtendedFileAssert> implements SharedAssert<ExtendedFileAssert, File> {

	@SuppressWarnings("unused") // may be used by SharedAssert methods via reflection
	private static Logger logger = Logger.getLogger(ExtendedStringAssert.class);

	public ExtendedFileAssert(File actual) {
		super(actual, ExtendedFileAssert.class);
	}

	/**
	 * Asserts that the actual {@link File} has the expected <code>ancestor</code>, which means its
	 * {@link File#getParentFile() parent} (or parent of that parent ...) is the specified <code>ancestor</code>.
	 */
	public ExtendedFileAssert hasAncestor(File ancestor) {
		Objects.instance().assertNotNull(info, actual);
		File currentAncestor = actual.getParentFile();
		boolean ancestorFound = false;
		while (currentAncestor != null) {
			if (currentAncestor.equals(ancestor)) {
				ancestorFound = true;
				break;
			}
			currentAncestor = currentAncestor.getParentFile();
		}

		if (!ancestorFound) {
			failWithMessage("File " + toString(actual.getAbsolutePath()) + " doesn't have ancestor " + toString(ancestor.getAbsolutePath()) + ".");
		}
		return this;
	}

	/**
	 * Asserts that the actual {@link File} {@link File#getPath() path} matches the path of the specified
	 * <code>file</code>.
	 */
	public ExtendedFileAssert hasSamePathAs(File file) {
		Objects.instance().assertNotNull(info, actual);
		if (!actual.getPath().equals(file.getPath())) {
			failWithMessage("Path " + toString(actual.getPath()) + " doesn't match expected path " + toString(file.getPath()) + ".");
		}
		return this;
	}

	/**
	 * Asserts that the actual {@link File} {@link File#getPath() path} does not match the path of the specified
	 * <code>file</code>.
	 */
	public ExtendedFileAssert doesNotHaveSamePathAs(File file) {
		Objects.instance().assertNotNull(info, actual);
		if (actual.getPath().equals(file.getPath())) {
			failWithMessage("Path " + toString(actual.getPath()) + " (unexpectedly) matches path " + toString(file.getPath()) + ".");
		}
		return this;
	}

	/**
	 * Asserts that the actual {@link File} {@link File#getAbsolutePath() absolute path} matches the absolute path of
	 * the specified <code>file</code>.
	 */
	public ExtendedFileAssert hasSameAbsolutePathAs(File file) {
		Objects.instance().assertNotNull(info, actual);
		if (!actual.getAbsolutePath().equals(file.getAbsolutePath())) {
			failWithMessage("Absolute path " + toString(actual.getAbsolutePath()) + " doesn't match expected absolute path "
					+ toString(file.getAbsolutePath()) + ".");
		}
		return this;
	}

	/**
	 * Asserts that the actual {@link File} {@link File#getPath() absolute path} does not match the absolute path of the
	 * specified <code>file</code>.
	 */
	public ExtendedFileAssert doesNotHaveSameAbsolutePathAs(File file) {
		Objects.instance().assertNotNull(info, actual);
		if (actual.getAbsolutePath().equals(file.getAbsolutePath())) {
			failWithMessage("Absolute path " + toString(actual.getAbsolutePath()) + " (unexpectedly) matches absolute path "
					+ toString(file.getAbsolutePath()) + ".");
		}
		return this;
	}

	/**
	 * Asserts that the actual {@link File} {@link File#getCanonicalPath() canonical path} matches the canonical path of
	 * the specified <code>file</code>.
	 */
	public ExtendedFileAssert hasSameCanonicalPathAs(File file) {
		Objects.instance().assertNotNull(info, actual);

		String actualCanonicalPath = FileTools.getCanonicalPath(actual);
		String expectedCanonicalPath = FileTools.getCanonicalPath(file);

		if (!actualCanonicalPath.equals(expectedCanonicalPath)) {
			failWithMessage(
					"Canonical path " + toString(actualCanonicalPath) + " doesn't match expected path " + toString(expectedCanonicalPath) + ".");
		}
		return this;
	}

	/**
	 * Asserts that the actual {@link File} {@link File#getCanonicalPath() canonical path} does not match the canonical
	 * path of the specified <code>file</code>.
	 */
	public ExtendedFileAssert doesNotHaveSameCanonicalPathAs(File file) {
		Objects.instance().assertNotNull(info, actual);

		String actualCanonicalPath = FileTools.getCanonicalPath(actual);
		String expectedCanonicalPath = FileTools.getCanonicalPath(file);

		if (actualCanonicalPath.equals(expectedCanonicalPath)) {
			failWithMessage("Canonical path " + toString(actualCanonicalPath) + " (unexpectedly) matches match path "
					+ toString(expectedCanonicalPath) + ".");
		}
		return this;
	}

}
