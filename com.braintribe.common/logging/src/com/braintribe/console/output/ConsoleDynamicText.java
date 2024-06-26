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
package com.braintribe.console.output;

import java.io.IOException;
import java.util.function.Consumer;

public class ConsoleDynamicText implements ConsoleText {
	private Consumer<Appendable> writer;
	
	public ConsoleDynamicText(Consumer<Appendable> writer) {
		super();
		this.writer = writer;
	}

	@Override
	public CharSequence getText() {
		StringBuilder builder = new StringBuilder();
		writer.accept(builder);
		return builder;
	}
	
	@Override
	public void append(Appendable appendable) throws IOException {
		writer.accept(appendable);
	}
}
