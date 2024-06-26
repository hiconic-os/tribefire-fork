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
package com.braintribe.artifacts.test.maven.pom.marshall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Solution;

public class TagTest extends AbstractPomMarshallerTest {
	private static Map<String,List<String>> expected;
	
	@BeforeClass
	public static void before() {
		expected = new HashMap<>();
		expected.put("none", Arrays.asList("n/a"));
		expected.put("standard", Arrays.asList( "standard"));
		expected.put("one", Arrays.asList( "one"));
		expected.put("one-and-two", Arrays.asList( "one", "two"));
	}

	@Test
	public void test() {
		read( "tags-terminal-1.0.1.pom");
	}

	@Override
	public boolean validate(Solution solution) {
				
		
		List<Dependency> dependencies = solution.getDependencies();
		Assert.assertTrue( "expected [" + expected.size() + "] dependencies, found [" + dependencies.size() + "]", expected.size() == dependencies.size());
		
		for (Dependency dependency : dependencies) {
			List<String> foundTags = dependency.getTags();
			List<String> expectedTags = expected.get( dependency.getArtifactId());
			
			if (expectedTags.size() == 1 && expectedTags.get(0).equalsIgnoreCase("n/a")) {
				Assert.assertTrue( "no tags expected for [" + dependency.getArtifactId() + "], but found [" + foundTags.size()+ "]", foundTags.size() == 0);				
			}
			else {
				List<String> expected = new ArrayList<>( expectedTags);
				List<String> notFound = new ArrayList<>();
				Iterator<String> iterator = expected.iterator();
				while (iterator.hasNext()) {
					String exp = iterator.next();
					if (!foundTags.contains(exp)) {
						notFound.add(exp);
					}
				}
				Assert.assertTrue("expected values not found", notFound.size() == 0);
				Assert.assertTrue("found contains unexpected tags", expectedTags.size() == foundTags.size());
			}
			
		}
		
		return true;
	}
	
	

}
