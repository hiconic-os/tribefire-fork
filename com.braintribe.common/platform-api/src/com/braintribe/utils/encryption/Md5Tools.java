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
package com.braintribe.utils.encryption;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.braintribe.utils.StringTools;

/**
 * @author peter.gazdik
 */
public class Md5Tools {

	public static String getMd5(String string) {
		MessageDigest digest = getMessageDigest();
		digest.update(string.getBytes());

		return StringTools.toHex(digest.digest());
	}

	public static String getMd5(File file) {
		try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
			return getMd5(in);

		} catch (Exception e) {
			throw new RuntimeException("Error while computing MD5 for file: " + file.getAbsolutePath(), e);
		}
	}

	public static String getMd5(URL url) throws Exception {
		try (InputStream in = url.openStream()) {
			return getMd5(in);
		}
	}

	public static String getMd5(InputStream in) {
		MessageDigest digest = getMessageDigest();
		byte md[] = new byte[8192];

		try {
			for (int n = 0; (n = in.read(md)) > -1;) {
				digest.update(md, 0, n);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error while computing MD5", e);
		}

		return StringTools.toHex(digest.digest());
	}

	private static MessageDigest getMessageDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 Algorithm available.", e);
		}
	}

}
