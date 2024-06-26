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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

public class FolderSize {
	private final AtomicLong size = new AtomicLong(0);
	private final AtomicLong numFiles = new AtomicLong(0);
	private final AtomicLong numFolders = new AtomicLong(0);
	private final AtomicLong numSymlinks = new AtomicLong(0);
	private final AtomicLong numOthers = new AtomicLong(0);

	/**
	 * Attempts to calculate the size of a file or directory by counting the number of transitively contained files grouped by their types as well as
	 * summing up their content in bytes. Symbolic links are not followed but still counted.
	 * <p>
	 * Since the operation is non-atomic, the returned value may be inaccurate. However, this method is quick and does its best.
	 */
	public static FolderSize of(Path path) {
		FolderSize folderInfo = new FolderSize();

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					folderInfo.size.addAndGet(attrs.size());

					if (attrs.isRegularFile()) {
						folderInfo.numFiles.incrementAndGet();
					} else if (attrs.isSymbolicLink()) {
						folderInfo.numSymlinks.incrementAndGet();
					} else if (attrs.isOther()) {
						folderInfo.numOthers.incrementAndGet();
					} else {
						throw new IllegalStateException("Visited file of unexpected type at " + file.toAbsolutePath());
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
					folderInfo.numFolders.incrementAndGet();

					// Ignore errors traversing a folder
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		return folderInfo;
	}

	/**
	 * Size of all transitively contained files together in bytes.
	 */
	public long getSize() {
		return size.get();
	}

	/**
	 * Number of all transitively contained folders including itself.
	 */
	public long getNumFolders() {
		return numFolders.get();
	}

	/**
	 * Number of all transitively contained "regular" files.
	 *
	 * @see BasicFileAttributes#isRegularFile()
	 */
	public long getNumFiles() {
		return numFiles.get();
	}

	/**
	 * Number of all transitively contained symbolic links.
	 */
	public long getNumSymlinks() {
		return numSymlinks.get();
	}

	/**
	 * Number of all transitively contained files that are neither "regular", directories or symbolic links.
	 */
	public long getNumOthers() {
		return numOthers.get();
	}

	/**
	 * Number of all transitively contained files including "regular", directories, symbolic links and eventual other types of files.
	 */
	public long getNumTotal() {
		return getNumFolders() + getNumFiles() + getNumSymlinks() + getNumOthers();
	}

}
