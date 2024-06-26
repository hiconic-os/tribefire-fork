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
package com.braintribe.utils.file.compare;

import static com.braintribe.utils.lcd.CollectionTools2.newLinkedMap;

import java.io.File;
import java.util.Map;

/**
 * @author peter.gazdik
 */
public class FileNameBasedFolderComparator {

	/** @return {@link FolderComparison} of given two folders based on the names of files in those folders only. */
	public static FolderComparison compare(File leftDir, File rightDir) {
		FileNameBasedFolderComparator instance = new FileNameBasedFolderComparator(leftDir, rightDir);
		return instance.compare();
	}

	private final Map<String, File> leftFiles;
	private final Map<String, File> rightFiles;

	private final FolderComparison result = new FolderComparison();

	public FileNameBasedFolderComparator(File leftDir, File rightDir) {
		result.leftDir = leftDir;
		result.rightDir = rightDir;

		this.leftFiles = index(leftDir);
		this.rightFiles = index(rightDir);
	}

	private FolderComparison compare() {
		for (File leftFile : leftFiles.values()) {
			File rightFile = rightFiles.remove(leftFile.getName());

			if (rightMatchesLeft(leftFile, rightFile)) {
				result.leftToRight.put(leftFile, rightFile);
			} else {
				result.onlyLeft.add(leftFile);
			}
		}

		result.onlyRight.addAll(rightFiles.values());

		return result;
	}

	private boolean rightMatchesLeft(File leftFile, File rightFile) {
		return rightFile != null && leftFile.isDirectory() == rightFile.isDirectory();
	}

	private Map<String, File> index(File dir) {
		Map<String, File> files = newLinkedMap();

		for (File file : dir.listFiles()) {
			files.put(file.getName(), file);
		}

		return files;
	}

}
