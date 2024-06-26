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
package com.braintribe.codec.marshaller.sax;

import org.xml.sax.Attributes;

import com.braintribe.codec.marshaller.api.MarshallException;

class MapEntryDecoder extends Decoder {
	public Object key;
	public Object value;
	
	@Override
	public void begin(DecodingContext context, Attributes attributes)
			throws MarshallException {
	}
	
	@Override
	public void end(DecodingContext context) throws MarshallException {
	}

	@Override
	public void onDescendantEnd(DecodingContext context, Decoder decoder)
			throws MarshallException {
		if (decoder instanceof MapKeyDecoder) {
			key = ((MapKeyDecoder)decoder).value;
		}
		else {
			value = ((MapValueDecoder)decoder).value;
		}
	}
	
	@Override
	public void appendCharacters(char[] characters, int s, int l) {
		// TODO Auto-generated method stub
		
	}
}
