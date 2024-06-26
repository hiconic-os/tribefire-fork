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

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

import com.braintribe.common.lcd.function.CheckedConsumer;
import com.braintribe.common.lcd.function.CheckedFunction;

/**
 * Fluent API for reading from a file with various options and ability to eat {@link IOException}s, meaning no method throws such a method, everywhere
 * possible it is converted to an {@link UncheckedIOException}.
 * 
 * @author peter.gazdik
 */
public interface CharsetReaderBuilder {

	/** Specify the character encoding. Default is UTF-8. */
	CharsetReaderBuilder withCharset(String charsetName);

	/** Specify the character encoding. Default is UTF-8. */
	CharsetReaderBuilder withCharset(Charset charset);

	String asString();

	List<String> asLines();

	Stream<String> asLineStream();

	/**
	 * Allows the client an access to an {@link Reader} of the underlying "resource" and propagates the result value (i.e. returns the value which is
	 * returned by the given function).
	 */
	<T> T fromReader(CheckedFunction<? super Reader, T, IOException> readerUser);

	/** Allows the client an access to a {@link Reader} of the underlying "resource", using UTF-8 as char encoding. */
	void consumeReader(CheckedConsumer<? super Reader, IOException> readerConsumer);

	default void toWriter(Writer writer) {
		toWriter(writer, true);
	}

	default void toWriter(Writer writer, boolean flush) {
		consumeReader(reader -> {
			final int SIZE_64K = 1 << 16;
			char[] buffer = new char[SIZE_64K];

			int c = -1;
			while ((c = reader.read(buffer)) != -1)
				writer.write(buffer, 0, c);

			if (flush)
				writer.flush();
		});

	}
}
