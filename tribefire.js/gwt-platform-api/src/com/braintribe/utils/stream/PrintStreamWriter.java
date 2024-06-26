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
package com.braintribe.utils.stream;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

public class PrintStreamWriter extends Writer {
	private PrintStream stream;
	private boolean ignoreClose;
	
	public PrintStreamWriter(PrintStream stream, boolean ignoreClose) {
		super();
		this.stream = stream;
		this.ignoreClose = ignoreClose;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		stream.append(new String(cbuf, off, len));
	}
	
	@Override
	public void write(char[] cbuf) throws IOException {
		stream.print(cbuf);
	}
	
	@Override
	public void write(String str) throws IOException {
		stream.print(str);
	}
	
	@Override
	public void write(String str, int off, int len) throws IOException {
		stream.append(str, off, off + len);
	}

	@Override
	public void flush() throws IOException {
		stream.flush();
	}

	@Override
	public void close() throws IOException {
		
		if (!ignoreClose)
			stream.close();
		else
			flush();
	}

}
