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
package com.braintribe.util.servlet.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import javax.servlet.http.Part;

/**
 * <p>
 * Supplies the content of the given {@link Part} as an {@link InputStream}
 */
public class PartInputStreamSupplier {

	private Part part;

	public PartInputStreamSupplier(Part part) {
		this.part = part;
	}

	public InputStream get() {
		try {
			return part.getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to get the contents of multipart part [" + part.getName() + "]: " + e.getMessage(), e);
		}
	}

}
