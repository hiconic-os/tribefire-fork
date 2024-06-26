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
package com.braintribe.utils.lcd;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.utils.lcd.CustomToStringBuilder.CustomStringRepresentationProvider;

/**
 * Tests for {@link CustomToStringBuilder}.
 *
 * @author michael.lafite
 */

public class CustomToStringBuilderTest {

	@Test
	public void test() {

		final CustomStringRepresentationProvider stringRepresentationProvider = (Object object) -> {
			if (object instanceof Integer) {
				return "Int(" + object + ")";
			}
			return null;
		};

		final CustomToStringBuilder builder = new CustomToStringBuilder(stringRepresentationProvider);

		Assert.assertEquals("null", builder.toString("null"));
		Assert.assertEquals("xy", builder.toString("xy"));
		Assert.assertEquals("null", builder.toString(null));
		// elements sorted by string representation
		Assert.assertEquals("[Int(1), null, xy]", builder.toString(CommonTools.getSet("xy", (Object) null, 1)));

		Assert.assertEquals("Int(123)", builder.toString(Integer.valueOf(123)));

		final Map<Long, Object> map = new HashMap<>();
		map.put(3l, "3");
		map.put(2l, null);
		map.put(1l, 1);
		Assert.assertEquals("{1=Int(1), 2=null, 3=3}", builder.toString(map));

		final CustomToStringBuilder enhancedBuilderThatSupportsArrays = new com.braintribe.utils.CustomToStringBuilder(stringRepresentationProvider);
		Assert.assertEquals("[Int(123), Int(456)]", enhancedBuilderThatSupportsArrays.toString(new Integer[] { 123, 456 }));
		Assert.assertEquals("[Int(123), Int(456)]", enhancedBuilderThatSupportsArrays.toStringVarArgs(Integer.valueOf(123), Integer.valueOf(456)));
		Assert.assertEquals("[Int(123), Int(456)]", enhancedBuilderThatSupportsArrays.toStringVarArgs(Integer.valueOf(123), Integer.valueOf(456)));
	}
}
