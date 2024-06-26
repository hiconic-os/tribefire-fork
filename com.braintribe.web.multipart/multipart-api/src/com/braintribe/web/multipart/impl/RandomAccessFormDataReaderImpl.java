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
package com.braintribe.web.multipart.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.web.multipart.api.MalformedMultipartDataException;
import com.braintribe.web.multipart.api.PartReader;
import com.braintribe.web.multipart.api.RandomAccessFormDataReader;

public class RandomAccessFormDataReaderImpl implements RandomAccessFormDataReader {

	private final Map<String, List<PartReader>> parts = new HashMap<>();
	private final SequentialFormDataReaderImpl dataStreaming;
	private boolean complete;

	public RandomAccessFormDataReaderImpl(InputStream in, byte[] boundary) {
		dataStreaming = new SequentialFormDataReaderImpl(in, boundary);
	}
	
	public RandomAccessFormDataReaderImpl(InputStream in, byte[] boundary, boolean autoCloseInput) {
		dataStreaming = new SequentialFormDataReaderImpl(in, boundary, autoCloseInput);
	}

	protected void ensureComplete() throws MalformedMultipartDataException, IOException {
		if (!complete) {
			PartReader partStreaming = null;
			PartReader previousPartStreaming = null;

			while ((partStreaming = dataStreaming.next()) != null) {
				if (previousPartStreaming != null) {
					previousPartStreaming.backup();
				}

				registerPart(partStreaming);

				previousPartStreaming = partStreaming;
			}

			complete = true;
		}
	}

	@Override
	public Set<String> getPartNames() throws MalformedMultipartDataException, IOException {
		ensureComplete();
		return parts.keySet();
	}

	@Override
	public Collection<PartReader> getAllParts(String name) throws MalformedMultipartDataException, IOException {
		ensureComplete();
		List<PartReader> partStreamings = parts.get(name);

		if (partStreamings != null) {
			return partStreamings;
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Allows to access the part in lazy streaming way. If the order of the random access is like the order of the parts
	 * in the # original stream the processing can happen without temporary storage
	 *
	 */
	@Override
	public PartReader getFirstPart(String name) throws MalformedMultipartDataException, IOException {
		List<PartReader> partStreamings = parts.get(name);

		if (partStreamings != null) {
			return partStreamings.get(0);
		}

		PartReader partStreaming = null;

		while ((partStreaming = dataStreaming.next()) != null) {
			if (partStreaming.getName().equals(name)) {
				registerPart(partStreaming);
				return partStreaming;
			} else {
				partStreaming.backup();
				registerPart(partStreaming);
			}
		}

		complete = true;

		return null;
	}

	protected void registerPart(PartReader partStreaming) {
		String name = partStreaming.getName();
		List<PartReader> partStreamings = parts.get(name);

		if (partStreamings == null) {
			partStreamings = new ArrayList<>();
			parts.put(name, partStreamings);
		}

		partStreamings.add(partStreaming);
	}

	@Override
	public void close() throws Exception {
		dataStreaming.close();
	}

}
