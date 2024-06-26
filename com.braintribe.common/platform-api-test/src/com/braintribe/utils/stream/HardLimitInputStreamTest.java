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
package com.braintribe.utils.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.function.Supplier;

import org.junit.Test;

import com.braintribe.exception.Exceptions;
import com.braintribe.utils.IOTools;

public class HardLimitInputStreamTest {

	@Test
	public void testLimits() throws Exception {

		Supplier<ByteArrayInputStream> inSupplier = () -> {
			try {
				return new ByteArrayInputStream("hello, world".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw Exceptions.unchecked(e, "Well, this is unexpected.");
			}
		};

		try (HardLimitInputStream largerLimit = new HardLimitInputStream(inSupplier.get(), false, 1000)) {
			String result = IOTools.slurp(largerLimit, "UTF-8");
			assertThat(result).isEqualTo("hello, world");
		}

		try (HardLimitInputStream exactLimit = new HardLimitInputStream(inSupplier.get(), false, 12)) {
			String result = IOTools.slurp(exactLimit, "UTF-8");
			assertThat(result).isEqualTo("hello, world");
		}

		try (HardLimitInputStream lesserLimit = new HardLimitInputStream(inSupplier.get(), false, 11)) {
			String result = IOTools.slurp(lesserLimit, "UTF-8");
			throw new RuntimeException("This code should not have been reached.");
		} catch (IndexOutOfBoundsException expected) {
			// Ignore
		}
	}

}
