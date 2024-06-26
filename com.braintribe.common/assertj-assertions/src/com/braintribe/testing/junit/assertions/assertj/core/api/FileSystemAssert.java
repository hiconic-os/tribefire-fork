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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import com.braintribe.utils.FileTools;

/**
 * @author peter.gazdik
 */
public class FileSystemAssert {

	private final FileSystemAssert sup;
	private final File currentFile;
	private final Path currentPath;

	public static FileSystemAssert of(File baseFolder) {
		Path absolutePath = baseFolder.getAbsoluteFile().toPath();

		FileSystemAssert result = new FileSystemAssert(null, absolutePath.getRoot());

		for (Path path : absolutePath) {
			result = result.subDirect(path);
		}

		return result;
	}

	public FileSystemAssert(File baseFolder) {
		this(null, baseFolder.getAbsoluteFile().toPath());

		isDirectory();
	}

	private FileSystemAssert(FileSystemAssert sup, Path currentPath) {
		this.sup = sup;
		this.currentPath = currentPath;
		this.currentFile = currentPath.toFile();
	}

	public File toFile() {
		return currentFile;
	}

	public Path toPath() {
		return currentPath;
	}

	public FileSystemAssert base() {
		return sup == null ? this : sup.base();
	}

	public FileSystemAssert sub(String first, String... more) {
		Path paths = Paths.get(first, more);
		return sub(paths);
	}

	private FileSystemAssert sub(Path paths) {
		FileSystemAssert result = this;

		for (Path path : paths) {
			result = subDirect(path);
		}

		return result;
	}

	private FileSystemAssert subDirect(Path path) {
		return new FileSystemAssert(this, currentPath.resolve(path));
	}

	public FileSystemAssert sup(int steps) {
		while (steps-- > 0) {
			sup();
		}
		return this;
	}

	public FileSystemAssert sup() {
		if (sup == null) {
			throw new IllegalStateException("Cannot go to the super of " + currentPath.toString());
		}

		return sup;
	}

	public FileSystemAssert isDirectory() {
		Assertions.assertThat(currentFile.isDirectory()).as("File is not a directory: " + currentFile.getAbsolutePath()).isTrue();
		return this;
	}

	public FileSystemAssert isEmptyDirectory() {
		isDirectory();
		Assertions.assertThat(currentFile.listFiles()).as("Directory is not empty: " + currentFile.getAbsolutePath()).isEmpty();
		return this;
	}

	public FileSystemAssert isNonEmptyDirectory() {
		isDirectory();
		Assertions.assertThat(currentFile.listFiles()).as("Directory is empty: " + currentFile.getAbsolutePath()).isNotEmpty();
		return this;
	}

	/** Underscore at the end indicates we move to the parent automatically after doing this check. */
	public FileSystemAssert isExistingFile_() {
		return isExistingFile().sup();
	}

	public FileSystemAssert isExistingFileWithSize_(long expectedSize) {
		return isExistingFile().hasSize_(expectedSize);
	}

	public FileSystemAssert hasSize_(long expectedSize) {
		return hasSize(expectedSize).sup();
	}

	public FileSystemAssert hasSize(long expectedSize) {
		Assertions.assertThat(currentFile.length()).as("Wrong file size.").isEqualTo(expectedSize);
		return this;
	}

	public FileSystemAssert isExistingFile() {
		Assertions.assertThat(currentFile.exists()).as("File does not exist: " + currentFile.getAbsolutePath()).isTrue();
		Assertions.assertThat(currentFile.isDirectory()).as("File exists, but is a directory: " + currentFile.getAbsolutePath()).isFalse();
		return this;
	}

	public FileSystemAssert notExists_() {
		Assertions.assertThat(currentFile.exists()).as("File should not exist: " + currentFile.getAbsolutePath()).isFalse();
		return sup();
	}

	public FileSystemAssert contains(String text) {
		return whoseContent(s -> s.contains(text));
	}

	public FileSystemAssert whoseContent(Consumer<String> textAssert) {
		isExistingFile();

		String text = FileTools.read(currentFile).asString();
		textAssert.accept(text);

		return this;
	}
}
