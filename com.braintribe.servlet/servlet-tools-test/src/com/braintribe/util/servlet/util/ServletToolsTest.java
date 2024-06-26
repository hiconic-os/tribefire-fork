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
package com.braintribe.util.servlet.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.braintribe.util.servlet.remote.FakeHttpServletRequest;

public class ServletToolsTest {

	@Test
	public void testAcceptHeaders() throws Exception {
	
		String[] accepts = new String[] {"a/b, c/d", "e/f"};
		FakeHttpServletRequest request = createRequest(accepts);
		List<String> types = ServletTools.getAcceptedMimeTypes(request);
		compareTypes(new String[] {"a/b", "c/d", "e/f"}, types);
		
		accepts = new String[] {"a/b", "c/d", "e/f"};
		request = createRequest(accepts);
		types = ServletTools.getAcceptedMimeTypes(request);
		compareTypes(new String[] {"a/b", "c/d", "e/f"}, types);

		accepts = new String[] {"a/b", "c/d", "e/f; q=0.5"};
		request = createRequest(accepts);
		types = ServletTools.getAcceptedMimeTypes(request);
		compareTypes(new String[] {"a/b", "c/d", "e/f"}, types);

		accepts = new String[] {"c/d; q=0.5, e/f; q=0.3", "a/b; q=1"};
		request = createRequest(accepts);
		types = ServletTools.getAcceptedMimeTypes(request);
		compareTypes(new String[] {"a/b", "c/d", "e/f"}, types);

		accepts = new String[] {"c/d; q=0.5, e/f", "a/b; q=1"};
		request = createRequest(accepts);
		types = ServletTools.getAcceptedMimeTypes(request);
		compareTypes(new String[] {"e/f", "a/b", "c/d"}, types);

	}
	
	private static void compareTypes(String[] expected, List<String> actual) {
		assertThat(actual.size()).isEqualTo(expected.length);
		for (int i=0; i<expected.length; ++i) {
			assertThat(actual.get(i)).isEqualTo(expected[i]);
		}
	}
	
	private static FakeHttpServletRequest createRequest(String... accepts) {
		FakeHttpServletRequest request = new FakeHttpServletRequest(null);
		for (String a : accepts) {
			request.addHeader("Accept", a);
		}
		return request;
	}
	
	@Test
	public void testParameters() throws Exception {
		FakeHttpServletRequest request = new FakeHttpServletRequest(null);
		request.addParameter("hello", "world");
		request.addParameters("key", new String[] {"value1", "value2"});
		
		assertThat(ServletTools.getSingleParameter(request, "hello", null)).isEqualTo("world");
		assertThat(ServletTools.getSingleParameter(request, "key", null)).isEqualTo("value1");
	}
}
