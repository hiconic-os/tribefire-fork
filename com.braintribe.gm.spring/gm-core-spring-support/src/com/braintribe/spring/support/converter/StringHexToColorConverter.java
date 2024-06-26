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
package com.braintribe.spring.support.converter;

import org.springframework.core.convert.converter.Converter;

import com.braintribe.model.style.Color;

public class StringHexToColorConverter implements Converter<String, Color>{
	
	@Override
	public Color convert(String source) {
		String cut = cutHex(source);
		Color c = Color.T.create();
		c.setRed(hexToR(cut));
		c.setGreen(hexToG(cut));
		c.setBlue(hexToB(cut));
		return c;
	}

	
	private int hexToR(String h) {
		return Integer.parseInt(h.substring(0,2),16);
	}
	private int hexToG(String h) {
		return Integer.parseInt(h.substring(2,4),16);
	}
	private int hexToB(String h) {
		return Integer.parseInt(h.substring(4,6),16);
	}
	
	private String cutHex(String h) {
		return (h.startsWith("#")) ? h.substring(1,7): h;
	}
	
}
