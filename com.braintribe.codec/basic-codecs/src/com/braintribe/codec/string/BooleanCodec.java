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
/**
 * 
 */
package com.braintribe.codec.string;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class BooleanCodec implements Codec<Boolean, String> {
	
	private String trueStr;
	private String falseStr;
	
	public BooleanCodec() {
		this(Boolean.TRUE.toString(), Boolean.FALSE.toString());
	}
	public BooleanCodec(String trueStr, String falseStr) {
		this.trueStr = trueStr;
		this.falseStr = falseStr;
	}

	@Override
	public Boolean decode(String strValue) throws CodecException {
		if (strValue == null || strValue.trim().length() == 0) return null;
		else {
			if (strValue.equalsIgnoreCase(trueStr)) return Boolean.TRUE;
			else if (strValue.equalsIgnoreCase(falseStr)) return Boolean.FALSE;
			else throw new CodecException("invalid encoded boolean " + strValue);
		}
	}

	@Override
	public String encode(Boolean obj) {
		if (obj != null) 
			return obj? trueStr: falseStr;
		else 
			return "";
	}
	
	@Override
	public Class<Boolean> getValueClass() {
	    return Boolean.class;
	}
}
