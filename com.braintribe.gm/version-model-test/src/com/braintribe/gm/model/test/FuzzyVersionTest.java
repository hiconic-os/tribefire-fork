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
package com.braintribe.gm.model.test;



import org.junit.Assert;
import org.junit.Test;

import com.braintribe.model.version.FuzzyVersion;
import com.braintribe.model.version.Version;
import com.braintribe.model.version.VersionExpression;
import com.braintribe.model.version.VersionRange;
import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;



public class FuzzyVersionTest extends AbstractVersionTest{
	
	@Test
	public void matchingTest() {
		VersionExpression ve = VersionExpression.parse("[1.0, 1.1)");
		
		Version v1 = Version.parse("1.0");		
		Assert.assertTrue( v1.asString() + " is expected to match " + ve.getExpression() + "", ve.matches(v1));
		
		Version v2 = Version.parse("1.1");
		Assert.assertTrue( v2.asString() + " is not expected to match " + ve.getExpression() + "", !ve.matches(v2));
					
	}
	
	@Test
	public void parsingTest() {
		VersionExpression ve = VersionExpression.parseVersionInterval("[1.0, 1.1)");		
		Assert.assertTrue("expected [1.0,1.1) to be a fuzzy version, but it's a [" + ve.getClass().getName(), ve instanceof FuzzyVersion);
		
		
		ve = VersionExpression.parseVersionInterval("[1.0.1, 1.1)");		
		Assert.assertTrue("expected [1.0,1.1) to be a range, but it's a [" + ve.getClass().getName(), ve instanceof VersionRange);
		
		ve = VersionExpression.parseVersionInterval("[1.0, 1.1.1)");		
		Assert.assertTrue("expected [1.0,1.1) to be a range, but it's a [" + ve.getClass().getName(), ve instanceof VersionRange);
		
	}

	/**
	 * <ul>
	 * 	<li>2 -> 3</li>
	 * 	<li>2.4 -> 2.5</li>
	 * 	<li>2.7.8 -> 2.7.9</li>
	 * </ul>
	 */
	@Test
	public void sucessorTest() {
		Version s1 = FuzzyVersion.create(2).upperBound();
		Version s2 = FuzzyVersion.create(2, 4).upperBound();
		Version s3 = FuzzyVersion.create(2, 7, 8).upperBound();

		System.out.println(s1.asString());
		System.out.println(s2.asString());
		System.out.println(s3.asString());
		
		Version o1 = Version.create(3);
		Version o2 = Version.create(2, 5);
		Version o3 = Version.create(2, 7, 9);
		
		Assertions.assertThat(s1.compareTo(o1) == 0).isTrue();
		Assertions.assertThat(s2.compareTo(o2) == 0).isTrue();
		Assertions.assertThat(s3.compareTo(o2) == 0).isTrue();
		
	}
	
	@Test
	public void testShortNotation() {
		FuzzyVersion s1 = FuzzyVersion.create(2);
		FuzzyVersion s2 = FuzzyVersion.create(2, 4);
		FuzzyVersion s3 = FuzzyVersion.create(2, 7, 8);
		
		Assertions.assertThat(s1.asShortNotation()).isEqualTo("2~");
		Assertions.assertThat(s2.asShortNotation()).isEqualTo("2.4~");
		Assertions.assertThat(s3.asShortNotation()).isEqualTo("2.7.8~");
	}
}
