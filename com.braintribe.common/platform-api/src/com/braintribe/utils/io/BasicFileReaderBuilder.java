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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import com.braintribe.common.lcd.function.CheckedConsumer;
import com.braintribe.common.lcd.function.CheckedFunction;
import com.braintribe.utils.FileTools;

/**
 * {@link ReaderBuilder} implementation for reading a {@link File}.
 *
 * @author peter.gazdik
 */
public class BasicFileReaderBuilder implements FileReaderBuilder {

	private final File file;

	private String charsetName = StandardCharsets.UTF_8.name();

	public BasicFileReaderBuilder(File file) {
		this.file = file;
	}

	@Override
	public CharsetReaderBuilder withCharset(String charsetName) {
		this.charsetName = charsetName;
		return this;
	}

	@Override
	public CharsetReaderBuilder withCharset(Charset charset) {
		this.charsetName = charset.name();
		return this;
	}

	@Override
	public byte[] asBytes() {
		return FileTools.readBytesFromFile(file);
	}

	@Override
	public String asString() {
		return FileTools.readStringFromFile(file, charsetName);
	}

	@Override
	public Stream<String> asLineStream() {
		try {
			return Files.lines(file.toPath(), Charset.forName(charsetName));
		} catch (IOException e) {
			throw new UncheckedIOException("Unable to read lines from file: " + file.getAbsolutePath(), e);
		}
	}

	@Override
	public List<String> asLines() {
		try {
			return Files.readAllLines(file.toPath(), Charset.forName(charsetName));
		} catch (IOException e) {
			throw new UncheckedIOException("Unable to read lines from file: " + file.getAbsolutePath(), e);
		}
	}

	@Override
	public <T> T fromInputStream(CheckedFunction<? super InputStream, T, IOException> inputStreamUser) {
		try (InputStream is = inputStream()) {
			return inputStreamUser.apply(is);

		} catch (final IOException e) {
			throw new UncheckedIOException("Unable to read file: " + file.getAbsolutePath(), e);
		}
	}

	@Override
	public void consumeInputStream(CheckedConsumer<? super InputStream, IOException> inputStreamConsumer) {
		try (InputStream is = inputStream()) {
			inputStreamConsumer.accept(is);

		} catch (final IOException e) {
			throw new UncheckedIOException("Unable to read file: " + file.getAbsolutePath(), e);
		}
	}

	private InputStream inputStream() throws FileNotFoundException {
		InputStream result = new FileInputStream(file);
		result = new BufferedInputStream(result);

		return result;
	}

	@Override
	public <T> T fromReader(CheckedFunction<? super Reader, T, IOException> readerUser) {
		try (Reader reader = reader(Charset.forName(charsetName))) {
			return readerUser.apply(reader);

		} catch (final IOException e) {
			throw new UncheckedIOException("Unable to read file: " + file.getAbsolutePath(), e);
		}
	}

	@Override
	public void consumeReader(CheckedConsumer<? super Reader, IOException> readerConsumer) {
		try (Reader reader = reader(Charset.forName(charsetName))) {
			readerConsumer.accept(reader);

		} catch (final IOException e) {
			throw new UncheckedIOException("Unable to read file: " + file.getAbsolutePath(), e);
		}
	}

	private Reader reader(Charset charset) throws FileNotFoundException {
		Reader result = new InputStreamReader(new FileInputStream(file), charset);
		result = new BufferedReader(result);

		return result;
	}

}
