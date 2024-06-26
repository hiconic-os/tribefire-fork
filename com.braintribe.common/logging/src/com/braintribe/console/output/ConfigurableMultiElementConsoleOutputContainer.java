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

import java.util.ArrayList;
import java.util.List;

import com.braintribe.console.ConsoleOutputs;

public class ConfigurableMultiElementConsoleOutputContainer implements ConfigurableConsoleOutputContainer {
	private List<ConsoleOutput> elements = new ArrayList<>();
	private int style;
	private boolean resetPosition;
	
	@Override
	public ConfigurableConsoleOutputContainer setStyle(int style) {
		this.style = style;
		return this;
	}
	
	@Override
	public ConfigurableConsoleOutputContainer append(ConsoleOutput output) {
		elements.add(output);
		return this;
	}
	
	@Override
	public ConfigurableConsoleOutputContainer append(CharSequence text) {
		return append(ConsoleOutputs.text(text));
	}
	
	@Override
	public ConfigurableConsoleOutputContainer resetPosition(boolean resetPosition) {
		this.resetPosition = resetPosition;
		return this;
	}

	@Override
	public int getStyle() {
		return style;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public ConsoleOutput get(int i) {
		return elements.get(i);
	}
	
	@Override
	public boolean resetPosition() {
		return resetPosition;
	}
}
