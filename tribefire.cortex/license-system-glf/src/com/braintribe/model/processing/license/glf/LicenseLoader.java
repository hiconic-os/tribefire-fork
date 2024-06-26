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
package com.braintribe.model.processing.license.glf;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.auxilii.glf.client.exception.SystemException;
import com.auxilii.glf.client.loader.XMLLoader;
import com.braintribe.model.generic.session.InputStreamProvider;
import com.braintribe.utils.StringTools;

public class LicenseLoader extends XMLLoader {
	private InputStreamProvider inputStreamProvider;
	private MessageDigest digest;

	public LicenseLoader(InputStreamProvider inputStreamProvider) throws SystemException {
		super();
		this.inputStreamProvider = inputStreamProvider;
		
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public InputStream openLicenseStream() throws Exception {
		return new DigestInputStream(inputStreamProvider.openInputStream(), digest);
	}
	
	public String getMd5() {
		return StringTools.toHex(digest.digest());
	}
	
	@Override
	public boolean stateChanged() {
		return false;
	}
}
