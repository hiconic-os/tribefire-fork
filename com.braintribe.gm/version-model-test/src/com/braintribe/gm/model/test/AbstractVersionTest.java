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
import org.junit.experimental.categories.Category;

import com.braintribe.model.version.Version;
import com.braintribe.model.version.VersionRange;
import com.braintribe.testing.category.KnownIssue;

@Category( KnownIssue.class)
public abstract class AbstractVersionTest {
	
	/**
	 * @param eMarker
	 * @param eRelease
	 * @param eNumbers
	 * @return
	 */
	protected Version versionOf( int major, int minor, int revision, String qualifier, int buildNumber, String nonconform) {
		Version version = Version.T.create();
		version.setMajor(major);
		version.setMinor(minor);
		version.setRevision( revision);
		version.setQualifier(qualifier);
		version.setBuildNumber(buildNumber);
		version.setNonConform(nonconform);
		return version;
	}

	/**
	 * validate a version - will throw an assert expection if it fails
	 * @param version - the found version (the one to test)
	 * @param eMarker - the expected prerelease marker 
	 * @param eRelease - the expected pre release number 
	 * @param eNumbers - the expected numbers 
	 */
	protected void validate( Version v, int major, int minor, int revision, String qualifier, int buildNumber, String nonconform) {

		Assert.assertTrue( "major : expected [" + major + "], found ["+ v.getMajor(), major == v.getMajor());
		Assert.assertTrue( "major : expected [" + major + "], found ["+ v.getMajor(), major == v.getMajor());
		Assert.assertTrue( "major : expected [" + major + "], found ["+ v.getMajor(), major == v.getMajor());
		
		String vQualifier = v.getQualifier();
		if (qualifier == null) {
			Assert.assertTrue("qualifier : expected [null], found [" + vQualifier, vQualifier == null);
		}
		else {
			String qualifierToValidate = vQualifier;
			
			Assert.assertTrue("expected [" + qualifier + "], found [" + qualifierToValidate + "]", qualifier.equalsIgnoreCase(qualifierToValidate));
		}
		
		int vBuildNumber = v.getBuildNumber();		
		Assert.assertTrue("buildnumber : expected [" + buildNumber + "], found [" + vBuildNumber + "]", buildNumber == vBuildNumber);
		
		String vNonconform = v.getNonConform();
		
		if (nonconform == null) {
			Assert.assertTrue("nonconform : expected [null], found [" + vNonconform, vNonconform == null);
		}
		else {
			Assert.assertTrue("expected [" + nonconform + "], found [" + vNonconform + "]", nonconform.equalsIgnoreCase(vNonconform));
		}
		


	}
	
	/**
	 * validates (aka compares) two {@link VersionRange}, will throw an assert exception if they differ
	 * @param found - the found {@link VersionRange}
	 * @param expected - the expected {@link VersionRange}
	 */
	protected void validate( VersionRange found, VersionRange expected) {
		Version flV = found.getLowerBound();
		Version elV = expected.getLowerBound();

		if (elV == null) {
			if (flV != null)
				Assert.fail("expected no lower bounds, but found [" + flV.asString() + "]");
		}
		else {
			Assert.assertTrue( "expected a lower boundary, but found none", flV != null);
		}
		if (elV != null && flV != null) {
			Assert.assertTrue("expected lower boundary [" + elV.asString() + "] but found [" + flV.asString() + "]", elV.compareTo(flV) == 0);		
			Assert.assertTrue("expected lower boundary exclusive to be [" + expected.getLowerBoundExclusive() + "] but found [" + found.getLowerBoundExclusive()+ "]", expected.getLowerBoundExclusive() == found.getLowerBoundExclusive());
		}
		

		Version fuV = found.getUpperBound();
		Version euV = expected.getUpperBound();

		if (euV == null) {
			if (fuV != null) 
				Assert.fail("expected no upper bounds, but found [" + fuV.asString() + "]");
		}
		else {
			Assert.assertTrue( "expected a upper boundary, but found none", fuV != null);
		}	
		if (euV != null && fuV != null) {
			Assert.assertTrue("expected upper boundary [" + euV.asString() + "] but found [" + fuV.asString()+ "]", euV.compareTo(fuV) == 0);		
			Assert.assertTrue("expected upper boundary exclusive to be [" + expected.getUpperBoundExclusive() + "] but found [" + found.getUpperBoundExclusive()+ "]", expected.getUpperBoundExclusive() == found.getUpperBoundExclusive());
		}
	}

	
	
}
