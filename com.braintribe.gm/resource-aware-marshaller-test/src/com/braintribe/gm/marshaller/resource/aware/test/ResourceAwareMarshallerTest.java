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
package com.braintribe.gm.marshaller.resource.aware.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.braintribe.codec.marshaller.yaml.YamlMarshaller;
import com.braintribe.gm.marshaller.resource.aware.ResourceAwareMarshaller;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.SimpleIcon;
import com.braintribe.model.user.User;
import com.braintribe.utils.lcd.CollectionTools2;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.api.StreamPipes;

public class ResourceAwareMarshallerTest {
	@Test
	public void testMarshaller() throws IOException {
		byte[] data1 = new byte[1024];
		byte[] data2 = new byte[4096];
		byte[] data3 = new byte[1024*1024];
		
		Random random = new Random();
		random.nextBytes(data1);
		random.nextBytes(data2);
		random.nextBytes(data3);
		
		User user1 = createUser("user1", data1);
		User user2 = createUser("user2", data2);
		User user3 = createUser("user3", data3);

		List<User> users = CollectionTools2.asList(user1, user2, user3);
		
		ResourceAwareMarshaller marshaller = new ResourceAwareMarshaller();
		marshaller.setGmDataMimeType("application/yaml");
		marshaller.setMarshaller(new YamlMarshaller());
		
		StreamPipe testPipe = StreamPipes.simpleFactory().newPipe("test");
		
		try (OutputStream out = testPipe.acquireOutputStream()) {
			marshaller.marshall(out, users);
		}
		
		List<User> actualUsers;
		
		try (InputStream in = testPipe.openInputStream()) {
			actualUsers = (List<User>) marshaller.unmarshall(in);
		}
		
		assertThat(actualUsers).hasSize(users.size());
		
		byte[][] datas = new byte[][]{data1, data2, data3};
		
		for (int i = 0; i < users.size(); i++) {
			byte[] data = datas[i];
			SimpleIcon icon = (SimpleIcon) actualUsers.get(i).getPicture();
			
			Resource image = icon.getImage();

			assertThat(image.openStream()).hasSameContentAs(new ByteArrayInputStream(data));
		}
	}

	private User createUser(String userName, byte[] data1) {
		Resource image1 = Resource.createTransient(() -> new ByteArrayInputStream(data1));
		SimpleIcon icon1 = SimpleIcon.T.create();
		icon1.setImage(image1);
		
		User user1 = User.T.create();
		user1.setName(userName);
		user1.setPicture(icon1);
		return user1;
	}
}
