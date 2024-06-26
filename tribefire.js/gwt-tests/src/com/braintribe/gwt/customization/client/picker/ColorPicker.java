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

import com.google.gwt.user.client.Random;

/**
 * 
 * @author peter.gazdik
 */
public class ColorPicker extends Picker {

	protected int sub_defaultValue; 
	protected int sub_constructorStaticValue;
	protected int sub_directStaticValue = 111;
//	protected int sub_blockStaticValue;

	protected int sub_constructorDynamicValue;
	protected int sub_directDynamicValue = Random.nextInt();
//	protected int sub_blockDynamicValue;

	protected static int sub_staticDirectValue = 5555;
//	protected static int sub_staticBlockValue;

	protected static int sub_staticDynamicValue = Random.nextInt();
//	protected static int sub_staticBlockDynamicValue;

	public ColorPicker() {
		sub_constructorStaticValue = 222;
		sub_constructorDynamicValue = Random.nextInt();
	}
//
//	{
//		sub_directStaticValue = 333;
//		sub_directDynamicValue = Random.nextInt();
//	}

//	static {
//		sub_staticBlockValue = 6666;
//		sub_staticBlockDynamicValue = Random.nextInt();
//	}

	public int pickColor(String userName) {
		return userName == null ? 0 : userName.hashCode();
	}

}
