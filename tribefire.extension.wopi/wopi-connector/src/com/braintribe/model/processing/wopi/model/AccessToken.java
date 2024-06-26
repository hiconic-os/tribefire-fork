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
package com.braintribe.model.processing.wopi.model;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import com.braintribe.model.processing.wopi.misc.JsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

//@formatter:off
/**
 * Token representation from Authorization Header from Request from MS Web App to TF (e.g. userName, sessionId (Token given in the Browser URL))
 * 
 * 
 * --------------       ---------------
 * | MS Web App | ----> | WOPI module |
 * --------------       ---------------
 * 
 */
//@formatter:on
@JsonInclude(Include.NON_DEFAULT)
public class AccessToken {

	private String userName;
	private String sessionId;

	@Override
	public String toString() {
		try {
			return JsonUtils.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Could not create '" + AccessToken.class.getSimpleName() + "' - this should never happen!", e);
		}
	}

	public String getUserName() {
		return userName;
	}

	public AccessToken setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getSessionId() {
		return sessionId;
	}

	public AccessToken setSessionId(String sessionId) {
		this.sessionId = sessionId;
		return this;
	}

	public String encode() {
		byte[] binaryData = toString().getBytes();
		return Base64.encodeBase64URLSafeString(binaryData);
	}

	public static AccessToken decode(String base64String) {
		try {
			String content = new String(Base64.decodeBase64(base64String));
			return JsonUtils.readValue(content, AccessToken.class);
		} catch (IOException e) {
			return null;
		}
	}
}
