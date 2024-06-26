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
package com.braintribe.devrock.repolet.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.devrock.model.repolet.content.Dependency;


public class DependencyStringParserTest {

	private Map<String, Dependency> testCases = new HashMap<>();
	{
		Dependency dp = from( "group", "artifact", "1.0");		
		testCases.put("group:artifact#1.0", dp);
		
		dp = from( "group", "artifact", "1.0");
		dp.setClassifier("classifier");
		dp.setScope( "scope");
		dp.setType("type");	
		testCases.put("group:artifact#1.0-classifier:scope:type", dp);
		
		dp = from( "group", "artifact", "1.0");
		dp.setClassifier("classifier");		
		dp.setType("type");
		testCases.put("group:artifact#1.0-classifier:type", dp);
		
		dp = from( "group", "artifact", "1.0");
		dp.setClassifier("classifier");
		dp.setScope( "scope");		
		testCases.put("group:artifact#1.0-classifier:scope:", dp);
		
		
		
	}
	
	

	private Dependency from(String grp, String art, String vrs) {
		Dependency d = Dependency.T.create();
		d.setGroupId(grp);
		d.setArtifactId(art);
		d.setVersion(vrs);
		return d;
	}
	
	private void validate(String tag, String expected, String found) {
		if (expected == null) {			
			Assert.assertTrue("expected [null], but found [" + found + "]", found == null);							
		}
		else {
			if (found == null) {
				Assert.assertTrue("expected [" + expected + "], but found [" + found + "]", found != null);
			}
			else {
				Assert.assertTrue("expected [" + expected + "], but found [" + found + "]", found.equals(expected));
			}
		}
		
	}

	private void validate(Dependency expected, Dependency found) {
		validate("group", expected.getGroupId(), found.getGroupId());
		validate( "artifact", expected.getArtifactId(), found.getArtifactId());
		validate("version", expected.getVersion(), expected.getVersion());
		validate("classifier", expected.getClassifier(), expected.getClassifier());
		validate("scope", expected.getScope(), expected.getScope());
		validate("type", expected.getType(), expected.getType());		
	}
	
	@Test
	public void test() {
		for (Map.Entry<String, Dependency> entry : testCases.entrySet()) {
			Dependency found = Dependency.parse( entry.getKey());
			validate( entry.getValue(), found);
		}
	}
	
	//@Test
	public void relocationExpressionTest() {
		String partial1 = "com.braintribe.test.redirect";
		String partial2 = "com.braintribe.test.redirect:a";
		String partial3 = ":a";
		String partial4 = ":a#1.0.1";
		String full = "com.braintribe.test.redirect:a#1.0.1";
		
		Dependency.parse( full, false);
		Dependency.parse( partial1, true);
		Dependency.parse( partial2, true);
		Dependency.parse( partial3, true);
		Dependency.parse( partial4, true);
		
	}
}
