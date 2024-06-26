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
package com.braintribe.logging.juli.handlers.util;

import java.io.IOException;
import java.io.Writer;

/**
 * This implementation of a Writer does absolutely nothing. Every method invocation will be ignored. It also does not perform parameter validity
 * checks. All data sent to this Writer is lost. This class may come handy when an interface requires a Writer but the output is of no interest.
 */
public class NullWriter extends Writer {

	public static final NullWriter INSTANCE = new NullWriter();

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		// /dev/null
	}

	@Override
	public void flush() throws IOException {
		// /dev/null
	}

	@Override
	public void close() throws IOException {
		// /dev/null
	}

	@Override
	public void write(int c) throws IOException {
		// /dev/null
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		// /dev/null
	}

	@Override
	public void write(String str) throws IOException {
		// /dev/null
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		// /dev/null
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		// /dev/null
		return this;
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		// /dev/null
		return this;
	}

	@Override
	public Writer append(char c) throws IOException {
		// /dev/null
		return this;
	}

}
