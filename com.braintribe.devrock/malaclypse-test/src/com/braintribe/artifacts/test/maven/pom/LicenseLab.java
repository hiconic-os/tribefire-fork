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
package com.braintribe.artifacts.test.maven.pom;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braintribe.build.artifact.representations.artifact.pom.ArtifactPomReader;
import com.braintribe.build.artifact.representations.artifact.pom.PomReaderException;
import com.braintribe.model.artifact.Distribution;
import com.braintribe.model.artifact.License;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.processing.version.VersionProcessor;

public class LicenseLab extends AbstractPomReaderLab {

	@BeforeClass
	public static void runBefore  () {
		runbefore();		
	}
	
	private Solution read( File testPomFile) {
		ArtifactPomReader reader = pomExpertFactory.getReader();
		Solution solution;
		try {
			solution = reader.readPom( UUID.randomUUID().toString(), testPomFile);
			return solution;
		} catch (PomReaderException e) {
			Assert.fail("exception thrown :" + e.getMessage());
			return null;
		}
	}
	
	private void testLicense(String pom, Map<String, License> expectedLicenses) {
		File testPomFile = new File( contents, pom);
		Solution solution = read( testPomFile);
		Set<License> licenses = solution.getLicenses();
		if (licenses == null || licenses.size() == 0) {
			Assert.fail( "no licenses found");
			return;
		}
		
		for (License license : licenses) {
			String name = license.getName();
			License expected = expectedLicenses.get( name);
			if (expected == null) {
				Assert.fail("license [" + name + "] is not expected");
				continue;
			}
			Assert.assertTrue("found url [" + license.getUrl() + "], expected [" + expected.getUrl() + "]", expected.getUrl().equalsIgnoreCase(license.getUrl()));
			Assert.assertTrue("found distribution [" + license.getDistribution() + "], expected [" + expected.getDistribution() + "]", expected.getDistribution() == license.getDistribution());
		}
	
		System.out.println("group [" + solution.getGroupId() + "]");
		System.out.println("artifact [" + solution.getArtifactId() + "]");
		
		System.out.println("version [" + VersionProcessor.toString( solution.getVersion()) + "]");
	}
	
	@Test
	public void testSingleLicense() {
		License license = License.T.create();
		license.setName( "Braintribe License");
		license.setUrl( "http://license.braintribe.com");
		license.setDistribution( Distribution.manual);
		Map<String, License> expected = new HashMap<String, License>();
		expected.put( license.getName(), license);
		testLicense( "singleLicense.xml", expected);
	}
	
	

}
