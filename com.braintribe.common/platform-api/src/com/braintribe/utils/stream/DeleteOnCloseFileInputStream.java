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
package com.braintribe.utils.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.braintribe.logging.Logger;

public class DeleteOnCloseFileInputStream extends FileInputStream {

	private static Logger logger = Logger.getLogger(DeleteOnCloseFileInputStream.class);

	protected File theFile;
	protected boolean deleteParentFolder = false;

	public DeleteOnCloseFileInputStream(File file) throws FileNotFoundException {
		super(file);
		this.theFile = file;
	}

	public DeleteOnCloseFileInputStream(File file, boolean deleteParentFolderIfEmpty) throws FileNotFoundException {
		super(file);
		this.theFile = file;
		this.deleteParentFolder = deleteParentFolderIfEmpty;
	}

	@Override
	public void close() throws IOException {
		try {
			super.close();
		} catch (Exception e) {
			throw new IOException("Error while closing input stream from file " + theFile, e);
		} finally {
			deleteOnClose();
		}
	}

	private void deleteOnClose() {
		try {
			if (theFile != null && theFile.exists()) {

				int retries = 3;
				boolean trace = logger.isTraceEnabled();

				boolean deleteSuccess = this.deleteFile(this.theFile);

				if (!deleteSuccess) {
					logger.warn("Could not delete file: " + theFile.getAbsolutePath() + " after " + retries + " tries. Must be cleaned manually.");
				} else {
					if (this.deleteParentFolder) {
						if (trace) {
							logger.trace("Configured to delete containing folder if it is empty.");
						}

						File parent = this.theFile.getParentFile();
						if (parent != null) {
							File[] content = parent.listFiles();
							if (content != null && content.length > 0) {
								if (trace) {
									logger.trace("The parent folder " + parent.getAbsolutePath() + " is not empty");
								}
							} else {
								deleteSuccess = this.deleteFile(parent);
								if (!deleteSuccess) {
									logger.warn("Could not delete the parent folder: " + parent.getAbsolutePath() + " after " + retries
											+ " tries. Must be cleaned manually.");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error while trying to delete " + theFile, e);
		}
	}

	protected boolean deleteFile(File file) {
		int retries = 3;
		boolean trace = logger.isTraceEnabled();

		boolean fileExists = true;
		for (int i = 0; i < retries; i++) {
			file.delete();
			if (!file.exists()) {
				if (trace) {
					logger.trace("Successfully deleted file: " + file.getAbsolutePath());
				}
				fileExists = false;
				break;
			} else {
				if (trace) {
					logger.trace("Could not delete file: " + file.getAbsolutePath() + " in try: " + (i + 1));
				}
			}
		}
		return !fileExists;
	}
}
