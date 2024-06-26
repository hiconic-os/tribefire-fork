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

import java.io.IOException;
import java.io.UncheckedIOException;

public class AppendableConsole extends AbstractAnsiConsole {
	private Appendable appendable;

	public AppendableConsole(Appendable appendable, boolean ansiConsole, boolean resetStyles) {
		super(ansiConsole, resetStyles);
		this.appendable = appendable;
	}
	
	@Override
	protected void _out(CharSequence text, boolean linebreak) {
		try {
			appendable.append(text);
			if (linebreak)
				appendable.append("\n");
			
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
