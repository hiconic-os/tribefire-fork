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
package com.braintribe.common.attribute.common;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

import com.braintribe.common.attribute.TypeSafeAttribute;
import com.braintribe.utils.collection.impl.AttributeContexts;

/**
 * <p>
 * A {@link TypeSafeAttribute} for specifying the caller environment.
 * 
 * @author christina.wilpernig
 */
public interface CallerEnvironment extends TypeSafeAttribute<CallerEnvironment> {

	boolean isLocal();

	File currentWorkingDirectory();

	static File resolveRelativePath(String path) {
		File file = canonicalFileFor(path);

		if (file.isAbsolute())
			return file;

		return new File(getCurrentWorkingDirectory(), path);
	}

	static File canonicalFileFor(String path) {
		try {
			return new File(path).getCanonicalFile();
		} catch (IOException e) {
			throw new UncheckedIOException("Cannot get canonical file for path: [" + path + "]", e);
		}
	}

	static File getCurrentWorkingDirectory() {
		Optional<CallerEnvironment> ce = AttributeContexts.peek().findAttribute(CallerEnvironment.class);

		if (ce.isPresent()) {
			return ce.get().currentWorkingDirectory();
		} else {
			return new File(System.getProperty("user.dir"));
		}
	}

}
