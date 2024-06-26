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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.partreflection;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.braintribe.devrock.repolet.launcher.Launcher;
import com.braintribe.model.artifact.consumable.PartReflection;

/**
 * tests reflection on an remote repository with RestSupport only 
 * @author pit
 *
 */
public class SimplePartReflectionTest extends AbstractPartReflectionTest {
	public SimplePartReflectionTest() {	
			launcher = Launcher.build()
					.repolet()
					.name("archive")
						.descriptiveContent()
							.descriptiveContent( archiveInput( "simple.definition.txt"))
						.close()
						.restApiUrl("http://localhost:${port}/api/storage/archive")
						.serverIdentification("Artifactory/faked.by.repolet")			
					.close()
					.done();	
	}

	
	@Test
	public void simplePartReflectionOnTerminalTest() {
		
		List<PartReflection> expectations = new ArrayList<>();
		
		String repoletRoot = launcher.getLaunchedRepolets().get("archive");
		expectations.add( PartReflection.create( null, "pom", "archive"));
		expectations.add( PartReflection.create( null, "jar", "archive"));
		expectations.add( PartReflection.create( "sources", "jar", "archive"));
		expectations.add( PartReflection.create( "javadoc", "jar", "archive"));
		expectations.add( PartReflection.create( "asset", "man", "archive"));
					
		List<PartReflection> found = runResolveViaSettings("com.braintribe.devrock.test:t#1.0.1");		
		validate( found, expectations);
	}
	@Test
	public void simplePartReflectionOnB() {
		
		List<PartReflection> expectations = new ArrayList<>();
		
		String repoletRoot = launcher.getLaunchedRepolets().get("archive");
		expectations.add( PartReflection.create( null, "pom", "archive"));
		expectations.add( PartReflection.create( null, "jar", "archive"));
		expectations.add( PartReflection.create( "sources", "jar", "archive"));		
		
		List<PartReflection> found = runResolveViaSettings("com.braintribe.devrock.test.b:b#1.0.1");
		
		validate( found,  expectations);
	}
	@Test
	public void simplePartReflectionOnA() {
		
		
		List<PartReflection> expectations = new ArrayList<>();
		
		String repoletRoot = launcher.getLaunchedRepolets().get("archive");
		expectations.add( PartReflection.create( null, "pom", "archive"));
		expectations.add( PartReflection.create( null, "jar", "archive"));
		expectations.add( PartReflection.create( "sources", "jar", "archive"));
		expectations.add( PartReflection.create( "javadoc", "jar", "archive"));
		expectations.add( PartReflection.create( "asset", "man", "archive"));
		expectations.add( PartReflection.create( "properties", "zip", "archive"));

		
		List<PartReflection> found = runResolveViaSettings("com.braintribe.devrock.test.a:a#1.0.2");
		
		validate( found, expectations);
	}
}
