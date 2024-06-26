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
package com.braintribe.console;

import com.braintribe.console.output.ConsoleOutput;
import com.braintribe.console.output.ConsoleOutputContainer;
import com.braintribe.console.output.ConsoleText;

public class PlainSysoutConsole implements Console {
	public static PlainSysoutConsole INSTANCE = new PlainSysoutConsole();

	private static void _out(ConsoleOutput output, boolean linebreak) {
		_out(output);
		if (linebreak)
			System.out.println();
	}

	private static void _out(ConsoleOutput output) {
		switch (output.kind()) {
			case container:
				ConsoleOutputContainer container = (ConsoleOutputContainer) output;
				int size = container.size();
				for (int i = 0; i < size; i++) {
					_out(container.get(i));
				}
				break;
			case text:
				ConsoleText text = (ConsoleText) output;
				System.out.print(text.getText());
				break;
			default:
				break;

		}
	}

	@Override
	public Console print(ConsoleOutput output) {
		_out(output, false);
		return this;
	}

	@Override
	public Console print(String text) {
		System.out.print(text);
		return this;
	}

	@Override
	public Console println(String text) {
		System.out.println(text);
		return this;
	}

	@Override
	public Console println(ConsoleOutput output) {
		_out(output, true);
		return this;
	}
}
