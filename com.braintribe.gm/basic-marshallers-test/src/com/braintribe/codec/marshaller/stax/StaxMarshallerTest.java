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
package com.braintribe.codec.marshaller.stax;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmListType;

public class StaxMarshallerTest {

	@Test
	public void testEncoding() throws Exception {
		StaxMarshaller staxMarshaller = new StaxMarshaller();

		GmListType element = GmListType.T.create();
		element.setTypeSignature("list<GenericEntity>");
		element.setId("type:list<GenericEntity>");

		Set<GenericEntity> assembly = new HashSet<>();
		assembly.add(element);

		//@formatter:off
		ByteArrayOutputStream marsahllerOut = new ByteArrayOutputStream();
		staxMarshaller.marshall(
				marsahllerOut, 
				assembly, 
				GmSerializationOptions
				.deriveDefaults()
					.stabilizeOrder(true)
					.outputPrettiness(OutputPrettiness.high)
					.writeEmptyProperties(false)
					.build()
				);
		//@formatter:on

		String xml = marsahllerOut.toString();
		System.out.println(xml);

		staxMarshaller.unmarshall(new ByteArrayInputStream(xml.getBytes()), GmDeserializationOptions.deriveDefaults().build());
	}

	@Test
	public void testXXEAttack() throws Exception {

		StaxMarshaller staxMarshaller = new StaxMarshaller();

		/* User user = User.T.create(); user.setFirstName("John"); user.setLastName("Doe");
		 * 
		 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); staxMarshaller.marshall(baos, user);
		 * 
		 * String text = new String(baos.toString("UTF-8")); System.out.println(text); */

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sb.append("<!DOCTYPE lolz [\n");
		sb.append("<!ENTITY lol SYSTEM \"file:///\">\n");
		sb.append("]>\n");
		sb.append("<?gm-xml version=\"4\"?>\n");
		sb.append("<gm-data>\n");
		sb.append("<required-types><t alias='User^CsSFfF' num='1'>com.braintribe.model.user.User</t></required-types>\n");
		sb.append("<root-value><r>User^CsSFfF-0</r></root-value>\n");
		sb.append("<pool>\n");
		sb.append("<E id='User^CsSFfF-0'>\n");
		sb.append("<s p='firstName'>John</s>\n");
		sb.append("<s p='lastName'>Doe</s>\n");
		sb.append("<s p='password'>&lol;</s>\n");
		sb.append("</E>\n");
		sb.append("</pool>\n");
		sb.append("</gm-data>\n");

		ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));

		try {
			staxMarshaller.unmarshall(bais);
			throw new AssertionError("XXE attack was successful.");
		} catch (Exception e) {
			// This is expected
			return;
		}

	}

}
