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

public class SingleElementConsoleOutputContainer implements ConsoleOutputContainer {
	private ConsoleOutput element;
	private int style;
	private boolean resetPosition;
	
	public SingleElementConsoleOutputContainer(int style, ConsoleOutput element) {
		super();
		this.style = style;
		this.element = element;
	}
	
	public SingleElementConsoleOutputContainer(int style, ConsoleOutput element, boolean resetPosition) {
		super();
		this.element = element;
		this.style = style;
		this.resetPosition = resetPosition;
	}

	@Override
	public int getStyle() {
		return style;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public ConsoleOutput get(int i) {
		return element;
	}
	
	@Override
	public boolean resetPosition() {
		return resetPosition;
	}
}
