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

public class MultiElementConsoleOutputContainer implements ConsoleOutputContainer {
	private ConsoleOutput[] elements;
	private int style;
	private boolean resetPosition;

	public MultiElementConsoleOutputContainer(int style, ConsoleOutput... elements) {
		super();
		this.style = style;
		this.elements = elements;
	}
	
	public MultiElementConsoleOutputContainer(ConsoleOutput[] elements, int style, boolean resetPosition) {
		super();
		this.elements = elements;
		this.style = style;
		this.resetPosition = resetPosition;
	}

	public ConsoleOutput[] getElements() {
		return elements;
	}
	
	@Override
	public int getStyle() {
		return style;
	}

	@Override
	public int size() {
		return elements.length;
	}

	@Override
	public ConsoleOutput get(int i) {
		return elements[i];
	}
	
	@Override
	public boolean resetPosition() {
		return resetPosition;
	}
}
