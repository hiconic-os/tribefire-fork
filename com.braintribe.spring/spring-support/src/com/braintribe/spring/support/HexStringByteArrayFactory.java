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
package com.braintribe.spring.support;

import java.math.BigInteger;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;

public class HexStringByteArrayFactory implements FactoryBean {
	private String hexString;
	
	@Required
	public void setHexString(String hexString) {
		this.hexString = hexString;
	}
	
	public Object getObject() throws Exception {
		String encodedKeyHex = hexString.replaceAll("\\s", "");
		byte bytes[] = new BigInteger(encodedKeyHex, 16).toByteArray();
		return bytes;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return byte[].class;
	}

	public boolean isSingleton() {
		return true;
	}

}