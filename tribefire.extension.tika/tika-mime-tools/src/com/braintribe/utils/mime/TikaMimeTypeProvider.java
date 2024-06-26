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
package com.braintribe.utils.mime;

import java.io.File;
import java.util.function.Function;

import org.apache.tika.Tika;

import com.braintribe.logging.Logger;


/**
 * @deprecated
 * Use MimeTypeDetector instead.
 * 
 *
 */
@Deprecated
public class TikaMimeTypeProvider implements Function<File, String> {

	protected static Logger logger = Logger.getLogger(TikaMimeTypeProvider.class);

	protected final Tika tika = new Tika();

	@Override
	public String apply(File file) throws RuntimeException {
		try {
			return this.tika.detect(file);
		} catch (Exception e) {
			throw new RuntimeException("Error while trying to detect MIME type of " + file + " using TIKA.", e);
		}
	}
}
