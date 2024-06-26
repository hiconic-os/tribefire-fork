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
package tribefire.extension.elastic.elasticsearch.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.elasticsearch.cli.Terminal;

// TODO: check migration from 2.2.1 - changed class completely
public class BufferTerminal extends Terminal {

	protected StringWriter stringWriter = new StringWriter();
	protected PrintWriter printWriter = new PrintWriter(this.stringWriter);

	public BufferTerminal() {
		super(System.lineSeparator());
	}

	protected BufferTerminal(String lineSeparator) {
		super(lineSeparator);
	}

	public String getText() {
		return this.stringWriter.toString();
	}

	@Override
	public String readText(String arg0) {
		return null;
	}

	@Override
	public char[] readSecret(String arg0) {
		return null;
	}

	@Override
	public PrintWriter getWriter() {
		return printWriter;
	}

}
