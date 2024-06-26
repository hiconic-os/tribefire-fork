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
package com.braintribe.model.resource.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import com.braintribe.model.generic.annotation.GmSystemInterface;
import com.braintribe.model.generic.session.InputStreamProvider;
import com.braintribe.model.generic.session.OutputStreamer;
import com.braintribe.model.resource.Resource;

/**
 * A streamable source that can be kept as a {@link Resource}. Being streamable allows to access the InputStream and OutputStream.
 * 
 *
 */

@GmSystemInterface
public interface StreamableSource {

	InputStreamProvider inputStreamProvider();

	default InputStream openStream() {
		InputStreamProvider inputStreamProvider = inputStreamProvider();

		if (inputStreamProvider != null) {
			try {
				return inputStreamProvider.openInputStream();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		} else {
			throw new IllegalStateException("TransientSource has no input stream provider:" + this);
		}
	}

	default void writeToStream(OutputStream outputStream) {
		InputStreamProvider inputStreamProvider = inputStreamProvider();

		if (inputStreamProvider != null) {
			try {
				if (inputStreamProvider instanceof OutputStreamer) {
					((OutputStreamer) inputStreamProvider).writeTo(outputStream);
				} else {
					byte buffer[] = new byte[0x10000]; // 16kB buffer
					int bytesRead = 0;
					try (InputStream in = inputStreamProvider.openInputStream()) {
						while ((bytesRead = in.read(buffer)) != -1)
							outputStream.write(buffer, 0, bytesRead);
					}
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		} else {
			throw new IllegalStateException("TransientSource has no input stream provider:" + this);
		}
	}
}
