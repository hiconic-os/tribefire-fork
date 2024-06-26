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

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.artifacts.test.maven.pom.marshall.validator.BasicValidatorContext;
import com.braintribe.model.artifact.Distribution;
import com.braintribe.model.artifact.License;
import com.braintribe.model.artifact.Solution;

/**
 * test licenses 
 * @author pit
 *
 */
public class LicensesTest extends AbstractPomMarshallerTest {
	
	
	@Override
	public boolean validate(Solution solution) {
		if (!validateHeader(solution, new BasicValidatorContext("com.braintribe.test", "A", "1.0"))) {
			Assert.fail( "header not as expected");
			return false;
		}
		Set<License> licenses = solution.getLicenses();
		if (licenses == null || licenses.size() != 2) {
			Assert.fail("expected 2 licenses, found [" + licenses == null ? "0" : licenses.size() + "]");
			return false;
		}
		License license1 = retrieveLicense(licenses, "test1");
		if (license1 == null) {
			Assert.fail("no license with name [test1] found");
			return false;
		}
		if (!validateLicense(license1, "test1", "https://test.license.1.com", Distribution.manual))
			return false;
		
		License license2 = retrieveLicense(licenses, "test2");
		if (license2 == null) {
			Assert.fail("no license with name [test2] found");
			return false;
		}
		if (!validateLicense(license2, "test2", "https://test.license.2.com", Distribution.repo)) 
			return false;
		
		return true;
	}
	
	private License retrieveLicense( Set<License> licenses, String name) {
		for (License license : licenses) {
			if (name.equalsIgnoreCase(license.getName())) {
				return license;
			}
		}
		return null;
	}
	
	private boolean validateLicense( License license, String name, String url, Distribution distribution) {
		if (!name.equalsIgnoreCase( license.getName())) {
			Assert.fail("expected license name [" + name + "], yet found [" + license.getName() + "]");
			return false;
		}
		if (!url.equalsIgnoreCase( license.getUrl())) {
			Assert.fail("expected license url [" + url + "], yet found [" + license.getUrl() + "]");
			return false;
		}
		if (distribution != license.getDistribution()) {
			Assert.fail("expected license url [" + distribution + "], yet found [" + license.getDistribution() + "]");
			return false;
		}
		return true;
	}

	@Test
	public void redirectionTest() {
		read( "licenses.xml");
	}

}
