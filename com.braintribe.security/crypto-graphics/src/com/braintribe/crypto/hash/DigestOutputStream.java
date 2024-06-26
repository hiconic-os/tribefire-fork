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
package com.braintribe.crypto.hash;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;

import com.braintribe.crypto.utils.TextUtils;

public class DigestOutputStream extends OutputStream {
	private MessageDigest messageDigest;
	private OutputStream targetOut;
	
	public DigestOutputStream(MessageDigest digest) {
		super();
		this.messageDigest = digest;
	}
	
	public DigestOutputStream(MessageDigest digest, OutputStream targetOut) {
		super();
		this.messageDigest = digest;
		this.targetOut = targetOut;
	}

	@Override
	public void write(int b) throws IOException {
		messageDigest.update((byte)b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		messageDigest.update(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		messageDigest.update(b, off, len);
	}
	
	@Override
	public void close() throws IOException {
		if (targetOut != null) {
			OutputStreamWriter writer = new OutputStreamWriter(targetOut, "UTF-8");
			String hex = TextUtils.convertToHex(messageDigest.digest());
			writer.write(hex);
			writer.close();
		}
	}
	
	public MessageDigest getMessageDigest() {
		return messageDigest;
	}
}
