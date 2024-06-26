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
package com.braintribe.util.servlet.stream;

import org.junit.Test;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

public class OnDemandOpeningOutputStreamTest {

	@Test
	public void testOpenStream() throws Exception {

		FakeServletResponse response = new FakeServletResponse();
		OnDemandOpeningOutputStream odoos = new OnDemandOpeningOutputStream(response);
		
		assertThat(response.getOuputStreamOpened()).isFalse();
		
		odoos.write(13);
		
		assertThat(response.getOuputStreamOpened()).isTrue();
		assertThat(response.internalGetOutputStream().getData()).isEqualTo(new byte[] {13});
		assertThat(response.internalGetOutputStream().isClosed()).isFalse();
		
		odoos.close();
		
		assertThat(response.internalGetOutputStream().isClosed()).isTrue();
	}

	@Test
	public void testOpenStreamByteArray() throws Exception {

		FakeServletResponse response = new FakeServletResponse();
		OnDemandOpeningOutputStream odoos = new OnDemandOpeningOutputStream(response);
		
		assertThat(response.getOuputStreamOpened()).isFalse();
		
		String text = "Hello, world!";
		byte[] data = text.getBytes("UTF-8");
		odoos.write(data);
		
		assertThat(response.getOuputStreamOpened()).isTrue();
		assertThat(response.internalGetOutputStream().getData()).isEqualTo(data);
		assertThat(response.internalGetOutputStream().isClosed()).isFalse();
		
		odoos.close();
		
		assertThat(response.internalGetOutputStream().isClosed()).isTrue();
	}
	
	@Test
	public void testOpenStreamByteArrayWithParams() throws Exception {

		FakeServletResponse response = new FakeServletResponse();
		OnDemandOpeningOutputStream odoos = new OnDemandOpeningOutputStream(response);
		
		assertThat(response.getOuputStreamOpened()).isFalse();
		
		String text = "Hello, world!";
		byte[] data = text.getBytes("UTF-8");
		odoos.write(data, 0, data.length);
		
		assertThat(response.getOuputStreamOpened()).isTrue();
		assertThat(response.internalGetOutputStream().getData()).isEqualTo(data);
		assertThat(response.internalGetOutputStream().isClosed()).isFalse();
		
		odoos.close();
		
		assertThat(response.internalGetOutputStream().isClosed()).isTrue();
	}
	
	@Test
	public void testOpenStreamFlush() throws Exception {

		FakeServletResponse response = new FakeServletResponse();
		OnDemandOpeningOutputStream odoos = new OnDemandOpeningOutputStream(response);
		
		assertThat(response.getOuputStreamOpened()).isFalse();
		odoos.flush();
		
		assertThat(response.getOuputStreamOpened()).isTrue();
		assertThat(response.internalGetOutputStream().getData()).isEqualTo(new byte[0]);
		assertThat(response.internalGetOutputStream().isClosed()).isFalse();
		
		odoos.close();
		
		assertThat(response.internalGetOutputStream().isClosed()).isTrue();
	}
	
	@Test
	public void testOpenStreamClose() throws Exception {

		FakeServletResponse response = new FakeServletResponse();
		OnDemandOpeningOutputStream odoos = new OnDemandOpeningOutputStream(response);
		
		assertThat(response.getOuputStreamOpened()).isFalse();
		odoos.close();
		
		assertThat(response.getOuputStreamOpened()).isTrue();
		assertThat(response.internalGetOutputStream().getData()).isEqualTo(new byte[0]);
		assertThat(response.internalGetOutputStream().isClosed()).isTrue();
		
		odoos.close();
		
		assertThat(response.internalGetOutputStream().isClosed()).isTrue();
	}
	
	@Test
	public void testOpenStreamString() throws Exception {

		FakeServletResponse response = new FakeServletResponse();
		OnDemandOpeningOutputStream odoos = new OnDemandOpeningOutputStream(response);
		
		assertThat(response.getOuputStreamOpened()).isFalse();
		
		String text = "Hello, world!"; //Not interested in the encoding here; not point of this test
		odoos.print(text);
		
		assertThat(response.getOuputStreamOpened()).isTrue();
		assertThat(response.internalGetOutputStream().getData()).isEqualTo(text.getBytes("UTF-8"));
		assertThat(response.internalGetOutputStream().isClosed()).isFalse();
		
		odoos.close();
		
		assertThat(response.internalGetOutputStream().isClosed()).isTrue();
	}
	
}
