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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.util.Random;
import java.util.function.Supplier;

import com.braintribe.logging.Logger;
import com.braintribe.utils.IOTools;

public class CountingDigestInputStream extends DigestInputStream {
	private static final Logger logger = Logger.getLogger(CountingDigestInputStream.class);
	private long count;
	private static Random random;

	public CountingDigestInputStream(InputStream stream, MessageDigest digest) {
		super(stream, digest);
	}

	@Override
	public int read() throws IOException {
		int res = super.read();

		if (res != -1) {
			count++;
		}

		return res;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int res = super.read(b, off, len);

		if (res != -1) {
			count += res;
		}

		return res;
	}

	public long getCount() {
		return count;
	}

	public static void main(String[] args) {
		try {
			int count = 1000000;

			final byte[] data = new byte[count];
			Random random = getRandom();
			random.nextBytes(data);

			Supplier<InputStream> provider1 = new Supplier<InputStream>() {

				@Override
				public InputStream get() throws RuntimeException {
					try {
						return new CountingDigestInputStream(new ByteArrayInputStream(data), MessageDigest.getInstance("MD5"));
					} catch (NoSuchAlgorithmException e) {
						throw new RuntimeException(e);
					}
				}

			};

			Supplier<InputStream> provider2 = new Supplier<InputStream>() {

				@Override
				public InputStream get() throws ProviderException {
					return new ByteArrayInputStream(data);
				}

			};

			suck(provider1);
			suck(provider2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void suck(Supplier<InputStream> provider) throws Exception {
		long start = System.currentTimeMillis();
		for (int n = 0; n < 10; n++) {
			InputStream in = provider.get();

			try {
				byte buffer[] = new byte[8192];
				while (in.read(buffer) != -1) {
					// nothing to do
				}
			} finally {
				IOTools.closeCloseable(in, logger);
			}
		}
		long end = System.currentTimeMillis();
		long delta = end - start;

		System.out.println(delta + " ms");
	}
	
	private static Random getRandom() {
		if (random != null)
			return random;
		
		random = new Random();
		return random;
	}
}
