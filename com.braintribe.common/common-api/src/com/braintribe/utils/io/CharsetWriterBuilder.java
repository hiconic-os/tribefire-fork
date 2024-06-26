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
package com.braintribe.utils.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.stream.Stream;

import com.braintribe.common.lcd.function.CheckedConsumer;

/**
 * @see WriterBuilder
 * 
 * @author peter.gazdik
 */
public interface CharsetWriterBuilder<T> {

	/**
	 * This is a hint that the {@link Writer} passed to {@link #usingWriter(CheckedConsumer)} should not be wrapped in an extra buffer.
	 * <p>
	 * This method should be avoided, but in special situations when a concrete implementation is known, one might prefer the lowest-level writer (say
	 * {@link FileWriter}), rather than getting a {@link BufferedWriter}.
	 */
	default CharsetWriterBuilder<T> notBuffered() {
		return this;
	}

	/** Specify the character encoding. Default is UTF-8. */
	CharsetWriterBuilder<T> withCharset(String charsetName);

	/** Specify the character encoding. Default is UTF-8. */
	CharsetWriterBuilder<T> withCharset(Charset charset);

	/**
	 * Specifies the name for the underlying data.
	 * <p>
	 * The actual usage for the name is implementation specific, it can be used to derive the name of the written file, or the URL, it can be used
	 * purely for logging, or it can be completely ignored, which is the default behavior defined on this interface directly.
	 * 
	 * @param name
	 *            name to be given to the underlying data, if this method is supported
	 */
	default CharsetWriterBuilder<T> withName(String name) {
		// Optional
		return this;
	}

	T string(String string);

	default T lines(Stream<? extends CharSequence> lines) {
		return lines(((Stream<CharSequence>) (Stream<?>) lines)::iterator);
	}

	T lines(Iterable<? extends CharSequence> lines);

	/** Allows the client an access to a {@link Writer} of the underlying "resource". */
	T usingWriter(CheckedConsumer<Writer, IOException> actualWriter);

}