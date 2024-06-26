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
package com.braintribe.gwt.gmview.metadata.client;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class I18NBooleanCodec implements Codec<Boolean, String> {
	
	@Override
	public Boolean decode(String encodedValue) throws CodecException {
		return LocalizedText.INSTANCE.trueText().equals(encodedValue);
	}
	
	@Override
	public String encode(Boolean value) throws CodecException {
		if(value)
			return LocalizedText.INSTANCE.trueText();
		else
			return LocalizedText.INSTANCE.falseText(); 
	}
	
	@Override
	public Class<Boolean> getValueClass() {
		return Boolean.class;
	}

}
