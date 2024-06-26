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
package com.braintribe.utils.file.copy;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @author peter.gazdik
 */
public interface FileCopying {

	/** Default copying filter which results in all files being. */
	BiPredicate<File, File> COPY_ALL = (s, t) -> false;

	// ###############################################
	// ## . . . . . . . Configuration . . . . . . . ##
	// ###############################################

	/**
	 * There are three filters to configure - one for {@link #filterFiles(BiPredicate) files only} , one for {@link #filterDirs(BiPredicate) dirs
	 * only}, and this one which is relevant for both.
	 * <p>
	 * Each filter is tested for a (source, target) pair before <tt>source</tt> would be copied to <tt>target</tt>. If it matches (i.e. evaluates to
	 * <tt>true</tt>), the file or dir will not be copied.
	 * <p>
	 * Note that each of these three methods may be called at most once, i.e. it is not possible to overwrite already configured filter. The second
	 * call leads to a runtime exception as that is considered an error in client code.
	 */
	FileCopying filter(BiPredicate<File, File> fileOrDirFilter);

	/** @see #filter(BiPredicate) */
	FileCopying filterFiles(BiPredicate<File, File> fileFilter);

	/** @see #filter(BiPredicate) */
	FileCopying filterDirs(BiPredicate<File, File> dirFilter);

	/**
	 * Registers a callback which is called with the source and target file after source to target copying happens. Note that symbolic links are also
	 * announced this way, but folders are not.
	 */
	FileCopying onFileCopied(BiConsumer<File, File> sourceAndTargetFileConsumer);

	FileCopying onFileSkipped(BiConsumer<File, File> sourceAndTargetFileConsumer);

	// ###############################################
	// ## . . . . . . . . Execution . . . . . . . . ##
	// ###############################################

	/** Final method that executes the actual copying based on specified parameters. */
	void please();

}
