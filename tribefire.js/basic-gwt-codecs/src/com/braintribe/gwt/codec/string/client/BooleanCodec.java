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
package com.braintribe.gwt.codec.string.client;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.ioc.client.Configurable;

public class BooleanCodec implements Codec<Boolean, String> {
	
	private String encodedTrue = Boolean.TRUE.toString();
	private String encodedFalse = Boolean.FALSE.toString();

	public BooleanCodec() {
	}
	
	public BooleanCodec(String encodedTrue, String encodedFalse) {
		super();
		this.encodedTrue = encodedTrue;
		this.encodedFalse = encodedFalse;
	}

	@Configurable
	public void setEncodedFalse(String encodedFalse) {
		this.encodedFalse = encodedFalse;
	}
	
	public void setEncodedTrue(String encodedTrue) {
		this.encodedTrue = encodedTrue;
	}
	
	@Override
	public Boolean decode(String strValue) throws CodecException  {
		if (strValue == null || strValue.trim().length() == 0) {
			return null;
		}
		else if (encodedTrue.equals(strValue)) {
			return Boolean.TRUE;
		}
		else if (encodedFalse.equals(strValue)) {
			return Boolean.FALSE;
		}
		else throw new CodecException("invalid encoded boolean value: " + strValue);
	}

	@Override
	public String encode(Boolean obj) throws CodecException {
		if (obj == null) return "";
		else return obj? encodedTrue: encodedFalse;
	}

	@Override
	public Class<Boolean> getValueClass() {
		return Boolean.class;
	}
}
