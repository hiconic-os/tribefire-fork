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
package com.braintribe.devrock.ant.test.dependencies;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.braintribe.build.process.listener.MessageType;
import com.braintribe.build.process.listener.ProcessNotificationListener;
import com.braintribe.devrock.ant.test.TaskRunner;
import com.braintribe.devrock.ant.test.Validator;
import com.braintribe.devrock.ant.test.common.TestUtils;
import com.braintribe.devrock.model.repolet.content.RepoletContent;

/**
 * tests a simple dependencies-task run
 *  
 * @author pit
 *
 */
public class DependenciesTaskBasicsTest extends TaskRunner implements ProcessNotificationListener {
	
	private List<String> result;

	@Override
	protected String filesystemRoot() {	
		return "dependencies.basic";
	}

	@Override
	protected RepoletContent archiveContent() {
		return archiveInput( "simplest.tree.definition.yaml");
	}
		

	@Override
	protected void additionalTasks() {
		// copy build file 
		TestUtils.copy( new File(input, "build.xml"), new File(output, "build.xml"));
		// copy pom file
		TestUtils.copy( new File(input, "pom.xml"), new File(output, "pom.xml"));
	}

	@Override
	protected void preProcess() {
	}

	@Override
	protected void postProcess() {
		result = loadNamesFromFilesetDump( new File(output, "classpath.txt"));
	}

	@Override
	public void acknowledgeProcessNotification(MessageType messageType, String msg) {
		System.out.println( msg);
		
	}

	@Test
	public void runSimpleDependenciesTasks() {
		process( new File( output, "build.xml"), "dependencies", false, false);
		
		List<String> expectations = new ArrayList<>();
		expectations.add( "a-1.0.1.jar");
		expectations.add( "b-1.0.1.jar");
		expectations.add( "t-1.0.1.jar");
		expectations.add( "x-1.0.1.jar");
		expectations.add( "y-1.0.1.jar");
		expectations.add( "z-1.0.1.jar");
		
		// assert
		Validator validator = new Validator();		
		validator.validate( "classpath", result, expectations);
		
		validator.assertResults();		
	}
	
		
	
}
