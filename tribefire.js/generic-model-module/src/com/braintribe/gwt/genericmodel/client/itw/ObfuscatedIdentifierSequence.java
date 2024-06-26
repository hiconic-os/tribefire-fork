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
package com.braintribe.gwt.genericmodel.client.itw;

// Currently not used. This is important,a s the specialChar is actually used outside of this generator.
public class ObfuscatedIdentifierSequence {

	public static final char specialChar = '\u02b9';

	public static final ObfuscatedIdentifierSequence runtimePropertySequence = new ObfuscatedIdentifierSequence(specialChar);

	private static final char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$123456789".toCharArray();
	private static final int base = chars.length;

	private final int[] sequence = new int[6];
	private int digits = 1;
	public int count = 0;
	private final char poolName;
	private char extract[];

	public ObfuscatedIdentifierSequence(char poolName) {
		this.poolName = poolName;

		resetExtract();
	}

	public String next() {
		count++;
		extract[0] = poolName;
		boolean carry = true;
		for (int i = 0; i < digits; i++) {
			int d = sequence[i];

			extract[i + 1] = chars[d];

			if (carry) {
				d++;

				if (d == base) {
					sequence[i] = 0;
				} else {
					sequence[i] = d;
					carry = false;
				}
			}
		}

		String result = new String(extract);

		if (carry) {
			digits++;
			resetExtract();
		}

		return result;
	}

	private void resetExtract() {
		extract = new char[digits + 1];
	}

	public int getCount() {
		return count;
	}

}
