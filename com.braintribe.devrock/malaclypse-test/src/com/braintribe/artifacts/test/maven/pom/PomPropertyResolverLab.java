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
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braintribe.build.artifact.representations.RepresentationException;
import com.braintribe.build.artifact.representations.artifact.pom.ArtifactPomReader;
import com.braintribe.build.artifact.representations.artifact.pom.PomReaderException;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.processing.version.VersionProcessor;

public class PomPropertyResolverLab extends AbstractPomReaderLab{
	
	
	@BeforeClass
	public static void runBefore  () {
		runbefore();		
	}

	private void testPropertyResolvingViaSettings(File testPomFile) {	
		ArtifactPomReader reader = pomExpertFactory.getReader();
		Solution solution;
		try {
			solution = reader.readPom( UUID.randomUUID().toString(), testPomFile);
		} catch (PomReaderException e) {
			Assert.fail("exception thrown :" + e.getMessage());
			return;
		}
		
		Map<String, Map<String, String>> properties;
		try {
			properties = mavenSettingsReader.getPropertiesOfActiveProfiles();
		} catch (RepresentationException e) {
			Assert.fail("exception thrown :" + e.getMessage());
			return;
		}
		Map<String, String> propertiesOfBraintribe = properties.get("braintribe");
		String stGrp = propertiesOfBraintribe.get( "testPomGroup");
		String grp = solution.getGroupId();
		if (!grp.equalsIgnoreCase(stGrp)) {
			Assert.fail( "group [" + grp + "] found, expected [" + stGrp + "]");
			return;
		}
		String stArt = propertiesOfBraintribe.get("testPomArtifact");
		String art = solution.getArtifactId();
		if (!art.equalsIgnoreCase(stArt)) {
			Assert.fail("artifact [" + art + "] found, expected [" + stArt + "]");
			return;
		}
		String stVrs = propertiesOfBraintribe.get("testPomVersion");		
		String vrs = VersionProcessor.toString( solution.getVersion());
		
		if (!vrs.equalsIgnoreCase( stVrs)) {
			Assert.fail("artifact [" + vrs + "] found, expected [" + stVrs + "]");
			return;
		}	
	}
	
	@Test
	public void testStandardResolvingViaSettings() {
		testPropertyResolvingViaSettings( new File( contents, "testPropertyResolvingPom.xml"));
	}
	
	@Test
	public void testReflectionResolvingViaSettings() {
		testPropertyResolvingViaSettings( new File( contents, "testPropertyResolvingReflectionPom.xml"));
	}

}
