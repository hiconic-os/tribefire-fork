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
package com.braintribe.utils;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VersionTest {

	@Test
	public void testVersionFromString() throws Exception {

		Version v = null;

		v = new Version("1.0");
		assertThat(v.toString()).isEqualTo("1.0");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 0 });

		v = new Version("");
		assertThat(v.toString()).isEqualTo("");
		assertThat(v.isParseableVersionString()).isEqualTo(false);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 0 });

		v = new Version("1.2.3");
		assertThat(v.toString()).isEqualTo("1.2.3");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 2, 3 });

		v = new Version("1.");
		assertThat(v.toString()).isEqualTo("1");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1 });

		v = new Version("1.2.3.4.5.6.7");
		assertThat(v.toString()).isEqualTo("1.2.3.4.5.6.7");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 2, 3, 4, 5, 6, 7 });

	}

	@Test
	public void testVersionFromVersionString() throws Exception {

		Version v = null;
		Version original = null;

		original = new Version("1.0");
		v = new Version(original);

		assertThat(v.toString()).isEqualTo("1.0");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 0 });

		original = new Version("");
		v = new Version(original);

		assertThat(v.toString()).isEqualTo("");
		assertThat(v.isParseableVersionString()).isEqualTo(false);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 0 });

		original = new Version("1.2.3");
		v = new Version(original);

		assertThat(v.toString()).isEqualTo("1.2.3");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 2, 3 });

		original = new Version("1.");
		v = new Version(original);

		assertThat(v.toString()).isEqualTo("1");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1 });

		original = new Version("1.2.3.4.5.6.7");
		v = new Version(original);

		assertThat(v.toString()).isEqualTo("1.2.3.4.5.6.7");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 2, 3, 4, 5, 6, 7 });

	}

	@Test
	public void testVersionFromParts() throws Exception {

		Version v = null;

		v = new Version(new int[] { 1, 0 });
		assertThat(v.toString()).isEqualTo("1.0");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 0 });

		v = new Version(new int[] {});
		assertThat(v.toString()).isEqualTo("0");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 0 });

		v = new Version(new int[] { 1, 2, 3 });
		assertThat(v.toString()).isEqualTo("1.2.3");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 2, 3 });

		v = new Version(new int[] { 1, 2, 3, 4, 5, 6, 7 });
		assertThat(v.toString()).isEqualTo("1.2.3.4.5.6.7");
		assertThat(v.isParseableVersionString()).isEqualTo(true);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 2, 3, 4, 5, 6, 7 });

	}

	@Test
	public void testVersionComparison() throws Exception {

		Version v1 = null;
		Version v2 = null;

		v1 = new Version("1.0");
		v2 = new Version("1.0");

		assertThat(v1.equals(v2)).isEqualTo(true);
		assertThat(v1.compare(v1, v2)).isEqualTo(0);

		v1 = new Version("1.0");
		v2 = new Version("2.0");

		assertThat(v1.equals(v2)).isEqualTo(false);
		assertThat(v1.compare(v1, v2)).isEqualTo(-1);
		assertThat(v1.compare(v2, v1)).isEqualTo(1);

		v1 = new Version("1.0");
		v2 = new Version("1.1");

		assertThat(v1.equals(v2)).isEqualTo(false);
		assertThat(v1.compare(v1, v2)).isEqualTo(-1);
		assertThat(v1.compare(v2, v1)).isEqualTo(1);

		v1 = new Version("1.0");
		v2 = new Version("1.0.0");

		assertThat(v1.equals(v2)).isEqualTo(false);
		assertThat(v1.compare(v1, v2)).isEqualTo(-1);
		assertThat(v1.compare(v2, v1)).isEqualTo(1);

		v1 = new Version("1.0");
		v2 = new Version("1.0.1");

		assertThat(v1.equals(v2)).isEqualTo(false);
		assertThat(v1.compare(v1, v2)).isEqualTo(-1);
		assertThat(v1.compare(v2, v1)).isEqualTo(1);
	}

	@Test
	public void testErrors() throws Exception {

		@SuppressWarnings("unused")
		Version v = null;

		try {
			v = new Version((Version) null);
			throw new AssertionError();
		} catch (IllegalArgumentException iae) {
			// Expected
		}

		try {
			v = new Version((int[]) null);
			throw new AssertionError();
		} catch (IllegalArgumentException iae) {
			// Expected
		}

		try {
			v = new Version((String) null);
			throw new AssertionError();
		} catch (IllegalArgumentException iae) {
			// Expected
		}

	}

	@Test
	public void testVersionFromUnparseableString() throws Exception {

		Version v = null;

		v = new Version("1.a");
		assertThat(v.toString()).isEqualTo("1.a");
		assertThat(v.isParseableVersionString()).isEqualTo(false);
		assertThat(v.getVersionParts()).isEqualTo(new int[] { 1, 0 });

		Version v1 = new Version("1.a");
		Version v2 = new Version("1.b");
		assertThat(v1.equals(v2)).isEqualTo(false);
		assertThat(v1.compare(v1, v2)).isEqualTo(-1);
		assertThat(v1.compare(v2, v1)).isEqualTo(1);

	}
}
