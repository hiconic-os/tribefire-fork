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
package com.braintribe.common.uncheckedcounterpartexceptions;

import java.io.File;

import com.braintribe.common.lcd.uncheckedcounterpartexceptions.UncheckedIOException;

/**
 * An {@link UncheckedIOException} that is thrown, because a file (unexpectedly) already exists.
 *
 * @author michael.lafite
 *
 * @deprecated This seems to be too specific, and since UncheckedIOException is deprecated anyway, and the java equivalent has no String constructor,
 *             there doesn't seem to be an easy way to update this without causing some compatibility problem.
 */
@Deprecated
public class FileAlreadyExistsException extends UncheckedIOException {

	private static final long serialVersionUID = -2207912790179439754L;

	protected File file = null;

	public FileAlreadyExistsException(final String message) {
		super(message);
	}

	public FileAlreadyExistsException(final File file) {
		super("File " + file.getAbsolutePath() + " already exists!");
		this.file = file;
	}

	public File getFile() {
		return this.file;
	}
}
