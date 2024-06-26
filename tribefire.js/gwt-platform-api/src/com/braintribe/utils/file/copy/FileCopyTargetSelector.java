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

/**
 * Second level of a three-step "wizard" to enter your parameters for copying a file or a folder. The individual steps
 * are:
 * <ol>
 * <li>{@link FileCopyingImpl#FileCopyingImpl(File)} source file selection - to pick a source file or directory, i.e.
 * what is being copied</li>
 * <li>{@link FileCopyTargetSelector} - to pick a target file or directory, i.e. the new file folder that will have the
 * same content as the source. Alternatively, when picking {@link FileCopyTargetSelector#toDir(File)} a target will be
 * created as a child of that file and it will have the same name as the source.</li>
 * <li>{@link FileCopying } - to pick any additional parameters for copying as well as providing a method to
 * {@link FileCopying#please do the actual copying}.</li>
 * </ol>
 * 
 * @author peter.gazdik
 */
public interface FileCopyTargetSelector {

	/**
	 * Sets a file or a directory which should be a copy of the source file or directory. This means they are either
	 * both files or both directories. If you want to copy your file or directory into another directory use
	 * {@link #toDir(File)} instead.
	 */
	FileCopying as(File fileOrDir);

	/**
	 * Sets a directory to with the source file or directory will be copied. This means the created copy of this the
	 * source will be a child of this directory.
	 */
	FileCopying toDir(File dir);

}
