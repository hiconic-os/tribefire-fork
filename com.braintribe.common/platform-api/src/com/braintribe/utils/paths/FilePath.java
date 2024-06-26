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
package com.braintribe.utils.paths;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

/**
 * Immutable wrapper around a {@link File} which offers flexibility for deriving related Files (e.g. parents and children).
 *
 * @author peter.gazdik
 */
public interface FilePath {

	static FilePath of(File file) {
		return new FilePathImpl(file);
	}

	static FilePath of(String pathname) {
		return new FilePathImpl(pathname);
	}

	FilePath child(String... childSequence);

	FilePath parent();

	/**
	 * Returns the {@link FilePath} that denotes the n-th parent of this {@link FilePath}. 0 means current path, n-th parent means (n-1)-st parent of
	 * this path's parent.
	 */
	FilePath nthParent(int n);

	FilePath sybling(String siblingName);

	File toFile();
	URL toURL();
	Path toPath();
}
