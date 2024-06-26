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
package com.braintribe.model.processing.xmi.converter.experts;

import java.io.InputStream;
import java.util.function.Supplier;

import com.braintribe.exception.Exceptions;
import com.braintribe.utils.IOTools;



/**
 * lill' helper that returns the content of a template residing in the classpath 
 * @author pit
 *
 */
public class ClasspathResourceStringProvider implements Supplier<String> {
	private String key;
	private String content;
	
	/**
	 * @param key - the name of the template as it is required to find it in the CP
	 */
	public ClasspathResourceStringProvider(String key) {
		this.key = key;
	}

	@Override
	public String get() {
		// check cache 
		if (content != null) {
			return content;
		}
		// get it from the CP
		try ( InputStream in = getClass().getClassLoader().getResourceAsStream(key)) {
			content = IOTools.slurp(in, "ISO-8859-1");
			return content;
		}
		catch (Exception e) {
			throw Exceptions.unchecked(e, "cannot supply content for template [" + key + "]", IllegalStateException::new);
		}	
	}

}
