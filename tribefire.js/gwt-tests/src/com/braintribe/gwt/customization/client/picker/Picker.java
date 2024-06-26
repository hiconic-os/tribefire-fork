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
package com.braintribe.gwt.customization.client.picker;

import com.braintribe.gwt.customization.client.api.IPicker;
import com.google.gwt.user.client.Random;

/**
 * 
 * @author peter.gazdik
 */
public class Picker implements IPicker {

	protected int defaultValue; 
	protected int constructorStaticValue;
	protected int directStaticValue = 111;
	protected int blockStaticValue;

	protected int constructorDynamicValue;
	protected int directDynamicValue = Random.nextInt();
	protected int blockDynamicValue;

	protected static int staticDirectValue = 5555;
	protected static int staticBlockValue;

	protected static int staticDynamicValue = Random.nextInt();
	protected static int staticBlockDynamicValue;

	public Picker() {
		constructorStaticValue = 222;
		constructorDynamicValue = Random.nextInt();
	}

	public Picker(int constructorStaticValue) {
		this.constructorStaticValue = constructorStaticValue;
		this.constructorDynamicValue = Random.nextInt();
	}
	
	{
		blockStaticValue = 333;
		blockDynamicValue = Random.nextInt();
	}

	static {
		staticBlockValue = 6666;
		staticBlockDynamicValue = Random.nextInt();
	}

	@Override
	public String pickerType() {
		return "Picker";
	}

}
