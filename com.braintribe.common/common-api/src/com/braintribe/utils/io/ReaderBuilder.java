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
import java.io.InputStream;
import java.io.UncheckedIOException;

import com.braintribe.common.lcd.function.CheckedConsumer;
import com.braintribe.common.lcd.function.CheckedFunction;

/**
 * Fluent API for reading from a file with various options and ability to eat {@link IOException}s, meaning no method throws such a method, everywhere
 * possible it is converted to an {@link UncheckedIOException}.
 * <p>
 * When reading a text file, the default encoding is UTF-8. If the file uses different encoding, specify it with
 * {@link #withCharset(java.nio.charset.Charset)} or {@link #withCharset(String)}.
 * <p>
 * <i>Examples:</i>
 * 
 * <pre>
 * List&lt;String&gt; lines = readerBuilder.withCharset("UTF-8").asLines();
 * 
 * String text = readerBuilder.buffered().withCharset(StandardCharsets.UTF_8).asString();
 * 
 * byte[] bytes = readerBuilder.asBytes();
 * 
 * SomeEntity entity = (SomeEntity) readerBuilder.fromInputStream(jsonMarshaller::unmarshall)
 * </pre>
 * 
 * @author peter.gazdik
 */
public interface ReaderBuilder extends CharsetReaderBuilder {

	byte[] asBytes();

	/**
	 * Allows the client an access to an {@link InputStream} of the underlying "resource" and propagates the result value (i.e. returns the value
	 * which is returned by the given function).
	 */
	<T> T fromInputStream(CheckedFunction<? super InputStream, T, IOException> inputStreamUser);

	/** Allows the client an access to an {@link InputStream} of the underlying "resource". */
	void consumeInputStream(CheckedConsumer<? super InputStream, IOException> inputStreamConsumer);

}
