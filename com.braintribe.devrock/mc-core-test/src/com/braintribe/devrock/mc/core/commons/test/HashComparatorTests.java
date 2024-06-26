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
package com.braintribe.devrock.mc.core.commons.test;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.braintribe.devrock.mc.core.declared.commons.HashComparators;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.essential.ArtifactIdentification;

/**
 * just some simple test to see that the pre-packaged HashingComparators do work
 * @author pit
 *
 */
public class HashComparatorTests {

	/**
	 * test {@link ArtifactIdentification}, groupId & artifactId
	 */
	@Test
	public void artifactIdentificationTest() {
		// set 
		Set<ArtifactIdentification> set = HashComparators.artifactIdentification.newHashSet();
		
		ArtifactIdentification ai1 = ArtifactIdentification.parse("my.group.one:my-artifact-one");
		set.add(ai1);
		
		ArtifactIdentification ai2 = ArtifactIdentification.parse("my.group.two:my-artifact-one");
		set.add(ai2);
		
		assertTrue( "expected size is [2], found [" + set.size() + "]",set.size() == 2);
		
		ArtifactIdentification ai3 = ArtifactIdentification.parse("my.group.one:my-artifact-one");
		set.add(ai3);
		
		assertTrue( "expected size is [2], found [" + set.size() + "]",set.size() == 2);
						
	}
	
	/**
	 * test comparator on {@link CompiledArtifactIdentification}, groupId, artifactId and version 
	 */
	@Test
	public void compiledArtifactIdentificationTest() {
		// set 
		Set<CompiledArtifactIdentification> set = HashComparators.compiledArtifactIdentification.newHashSet();
		
		CompiledArtifactIdentification ai1 = CompiledArtifactIdentification.parse("my.group.one:my-artifact-one#1.0");
		set.add(ai1);
		
		CompiledArtifactIdentification ai2 = CompiledArtifactIdentification.parse("my.group.two:my-artifact-one#1.0");
		set.add(ai2);
		
		assertTrue( "expected size is [2], found [" + set.size() + "]",set.size() == 2);
		
		CompiledArtifactIdentification ai3 = CompiledArtifactIdentification.parse("my.group.one:my-artifact-one#1.0");
		set.add(ai3);
		
		assertTrue( "expected size is [2], found [" + set.size() + "]",set.size() == 2);
						
	}


}
