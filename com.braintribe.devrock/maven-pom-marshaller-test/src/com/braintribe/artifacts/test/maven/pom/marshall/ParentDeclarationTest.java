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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.artifacts.test.maven.pom.marshall.validator.BasicValidatorContext;
import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Exclusion;
import com.braintribe.model.artifact.Solution;

/**
 * test parent file 
 * @author pit
 *
 */
public class ParentDeclarationTest extends AbstractPomMarshallerTest {
	
	
	@Override
	public boolean validate(Solution solution) {
		String groupId = "com.braintribe.test";
		if (!validateHeader(solution, new BasicValidatorContext(groupId, "Parent", "1.0"))) {
			Assert.fail( "header not as expected");
			return false;
		}
		
		// properties
		Map<String, String> expectedProperties = new HashMap<>();
		expectedProperties.put("codebase", "braintribe-dev");
		expectedProperties.put("version", "1.0");
		expectedProperties.put("property1", "${version}");
		
		validateProperties(solution, expectedProperties);
		
		// dependency management
		List<Dependency> managedDependencies = solution.getManagedDependencies();
		// 
		Dependency dep1 = retrieveDependency(managedDependencies, groupId, "Dep1", "1.0");
		if (dep1 == null) {
			Assert.fail("dependency com.braintribe.test:Dep1#1.0 not found");
			return false;
		}
		BasicValidatorContext c1 = new BasicValidatorContext(dep1, groupId, "Dep1", "1.0");
		validateDependency(c1);
		
		
		Dependency dep2 = retrieveDependency(managedDependencies, groupId, "Dep2", "1.0");
		if (dep2 == null) {
			Assert.fail("dependency com.braintribe.test:Dep2#1.0 not found");
			return false;
		}
		BasicValidatorContext c2 = new BasicValidatorContext(dep2, groupId, "Dep2", "1.0");
		c2.setScope("provided");
		validateDependency(c2);
		
		Dependency dep3 = retrieveDependency(managedDependencies, groupId, "Dep3", "1.0");
		if (dep3 == null) {
			Assert.fail("dependency com.braintribe.test:Dep3#1.0 not found");
			return false;
		}
		BasicValidatorContext c3 = new BasicValidatorContext(dep3, groupId, "Dep3", "1.0");
		c3.setType("jar");
		validateDependency(c3);		
		
		Dependency dep4 = retrieveDependency(managedDependencies, groupId, "Dep4", "1.0");
		if (dep4 == null) {
			Assert.fail("dependency com.braintribe.test:Dep4#1.0 not found");
			return false;
		}
		BasicValidatorContext c4 = new BasicValidatorContext(dep4, groupId, "Dep4", "1.0");
		c4.setOptional( true);
		validateDependency(c4);
		
		Exclusion singleExclusion = Exclusion.T.create();
		singleExclusion.setGroupId("com.braintribe.exclusion");
		singleExclusion.setArtifactId( "Exclusion");
		validateExclusions(dep4, Collections.singleton( singleExclusion));
		
		Dependency dep5 = retrieveDependency(managedDependencies, groupId, "Dep5", "1.0");
		if (dep5 == null) {
			Assert.fail("dependency com.braintribe.test:Dep5#1.0 not found");
			return false;
		}
		BasicValidatorContext c5 = new BasicValidatorContext(dep5, groupId, "Dep5", "1.0");
		c5.setScope("import");
		validateDependency(c5);		
		return true;
	}

	@Test
	public void redirectionTest() {
		read( "parent.xml");
	}

}
