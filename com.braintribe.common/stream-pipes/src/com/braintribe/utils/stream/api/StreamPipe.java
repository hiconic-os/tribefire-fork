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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A StreamPipe intelligently manages a buffer for you that you can write to and read from later on or at the same time.
 * Depending on its implementation this buffer could be a temporary file, in memory or a mix of both.
 * <p>
 * A StreamPipe allows for multiple {@link #openInputStream() input streams} (for reading) and a non blocking
 * {@link #openOutputStream() output stream} (for writing). The resources used by this pipe's buffer (i.e. files or
 * memory) are automatically freed when no further reference is held to them or when exiting the JVM. As references are
 * considered the pipe itself and its streams. They can also be freed manually and instantly by calling
 * {@link #close()}.
 * <p>
 * A monitor object is used to tightly couple write and read operations to avoid a trade-off between responsiveness and
 * wasted CPU cycles.
 * 
 * @see StreamPipeFactory
 * 
 * @author Neidhart Orlich
 * @author Dirk Scheffler
 */
public interface StreamPipe extends AutoCloseable {

	PipeStatus getStatus();

	/**
	 * Opens an input stream to the pipe backup. This method can be called multiple times and will always start a new
	 * input stream for the data.
	 */
	InputStream openInputStream() throws IOException;

	/**
	 * Convenience method that opens the output stream of this pipe and feeds it with provided {@link InputStream} using
	 * a parallel thread.
	 * 
	 * @param in
	 *            {@link InputStream} that contains the data you want to feed to the pipe.
	 */
	void feedFrom(InputStream in);

	/**
	 * Opens or simply returns the already opened OutputStream. You should be aware to use it properly as this always
	 * returns the one and only OutputStream that pipe can have.
	 */
	OutputStream acquireOutputStream();

	/**
	 * Opens the output stream that is to be used to fill the pipe.
	 * 
	 * @throws IllegalStateException
	 *             when an output was already opened on this pipe.
	 */
	OutputStream openOutputStream();

	boolean wasOutputStreamOpened();
	
	void notifyError(Throwable t);

	/**
	 * Immediately frees all resources (files, in memory buffers, ...) that are used by this instance. A closed pipe
	 * can't be used any more.
	 * <p>
	 * If any In- or OutputStreams of this pipe are still opened, they are invalidated so that they can't corrupt any
	 * data and depending on the implementation an {@link IllegalStateException} might be thrown as well.
	 */
	@Override
	// Removing declared Exception
	void close();

}