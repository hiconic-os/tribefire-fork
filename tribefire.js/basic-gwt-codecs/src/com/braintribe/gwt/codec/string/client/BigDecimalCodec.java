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

import java.math.BigDecimal;

import com.braintribe.codec.Codec;
import com.braintribe.gwt.ioc.client.Configurable;

/**
 * A codec for {@link BigDecimal}s.
 * 
 * @author michael.lafite
 */
public class BigDecimalCodec implements Codec<BigDecimal, String> {
	
	private boolean useCommaAsDecimalSeparator = false;
	
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
		String result = "";
		if (obj != null) {
			result = obj.toString();
			int pointPosition = result.indexOf(".");
			if (pointPosition == -1) {
				result += ".";
				pointPosition = result.length() - 1;
			}
			
			int afterSeparatorSize = result.substring(pointPosition + 1).length();
			if (afterSeparatorSize == 0) {
				result += "00";
			} else if (afterSeparatorSize == 1) {
				result += "0";
			}
			
			if (useCommaAsDecimalSeparator) {
				result = result.replace('.', ',');
			}
		}
		
		return result;
	}

	@Override
	public Class<BigDecimal> getValueClass() {
		return BigDecimal.class;
	}
	
	/**
	 * If true, the Codec will use a comma as the decimal separator.
	 * Defaults to false.
	 */
	@Configurable
	public void setUseCommaAsDecimalSeparator(boolean useCommaAsDecimalSeparator) {
		this.useCommaAsDecimalSeparator = useCommaAsDecimalSeparator;
	}
}
