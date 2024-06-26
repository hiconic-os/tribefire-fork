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
package com.braintribe.gwt.gmview.client.parse.expert;

import java.util.function.Function;

import com.braintribe.gwt.codec.string.client.DoubleCodec;


/**
 * 
 */
public class DoubleParser implements Function<String, Double> {

	private DoubleCodec commaCodec = new DoubleCodec();
	private DoubleCodec dotCodec = new DoubleCodec();

	{
		commaCodec.setUseCommaAsDecimalSeparator(true);
		dotCodec.setUseCommaAsDecimalSeparator(false);
	}

	@Override
	public Double apply(String value) throws RuntimeException {
		if (ParserUtils.endsWithLetter(value) && !value.toLowerCase().endsWith("d")) {
			return null;
		}

		try {
			if (value.contains(",")) {
				return commaCodec.decode(value);
			} else {
				return dotCodec.decode(value);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
