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
package com.braintribe.web.multipart.api;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public interface RandomAccessFormDataReader extends FormDataReader {
	/**
	 * This method gives overview over all existing parts names which involves to read and cache the complete
	 * outstanding parts in temporary file system storage. This can be supoptimial in cases where temporary storage
	 * should be avoided if possible
	 *
	 * @return
	 */
	public Set<String> getPartNames() throws MalformedMultipartDataException, IOException;

	/**
	 * This method returns all parts for the given name which involves to read and cache the complete outstanding parts
	 * in temporary file system storage. This can be supoptimial in cases where temporary storage should be avoided if
	 * possible
	 *
	 * @return
	 */
	public Collection<PartReader> getAllParts(String name) throws MalformedMultipartDataException, IOException;

	/**
	 * Allows to access the part in lazy streaming way. If the order of the random access is like the order of the parts
	 * in the # original stream the processing can happen without temporary storage
	 *
	 * @param name
	 * @return
	 * @throws MalformedMultipartDataException
	 * @throws IOException
	 */
	PartReader getFirstPart(String name) throws MalformedMultipartDataException, IOException;
}
