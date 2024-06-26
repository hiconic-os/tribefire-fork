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
package com.braintribe.codec.string;

import java.math.BigDecimal;

import com.braintribe.codec.Codec;

/**
 * A codec for {@link BigDecimal}s.
 * 
 * @author michael.lafite
 */
public class BigDecimalCodec implements Codec<BigDecimal, String> {

	@Override
	public BigDecimal decode(String valueAsString) {
		if (valueAsString != null) {
			String valueAsTrimmedString = valueAsString.trim();
			if (valueAsTrimmedString.length() > 0) {
				return new BigDecimal(valueAsTrimmedString);
			}
		}
		return null;
	}

	@Override
	public String encode(BigDecimal obj) {
		if (obj != null) {
			return obj.toString();
		}
		return "";
	}

	@Override
	public Class<BigDecimal> getValueClass() {
		return BigDecimal.class;
	}
}
