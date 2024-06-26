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
package com.braintribe.utils.stream.api;

import com.braintribe.utils.IOTools;

/**
 * A factory for {@link StreamPipe}s.
 * 
 * @see #newPipe(String)
 */
public interface StreamPipeFactory {
	/**
	 * Default size for the automatic buffers used by {@link #newPipe(String)}
	 */
	int AUTO_BUFFER_DEFAULT_SIZE = IOTools.SIZE_32K;

	/**
	 * Creates a new {@link StreamPipe} instance. In- and output streams of this pipe are internally automatically
	 * wrapped with buffered streams if and only if the data isn't already in memory. If you want to control this
	 * behavior please use {@link #newPipe(String, int)}
	 * 
	 * @see #AUTO_BUFFER_DEFAULT_SIZE
	 * 
	 * @param name
	 *            Descriptive name of the pipe. Might be used for buffer resource names or in error messages. Doesn't
	 *            need to be unique.
	 * @return a new {@link StreamPipe}
	 */
	default StreamPipe newPipe(String name) {
		return newPipe(name, AUTO_BUFFER_DEFAULT_SIZE);
	}

	/**
	 * Creates a new {@link StreamPipe} instance. In- and output streams of this pipe can internally automatically be
	 * wrapped with buffered streams if and only if the data isn't already in memory. You can control the size of these
	 * buffers with the autoBufferSize parameter.
	 * 
	 * @param name
	 *            Descriptive name of the pipe. Might be used for buffer resource names or in error messages. Doesn't
	 *            need to be unique.
	 * @param autoBufferSize
	 *            size for the automatic buffers. A value <= 0 means that no buffer will be used and buffering has to be
	 *            handled by yourself.
	 * @return a new {@link StreamPipe}
	 */
	StreamPipe newPipe(String name, int autoBufferSize);
}
