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
package com.braintribe.test.multi.performance;

import java.io.File;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.artifact.Solution;
import com.braintribe.testing.category.Slow;


/**
 
 * 
 * @author pit
 *
 */
@Category(Slow.class)
public class MalaclypseLab extends AbstractPerformanceLab {
		
	
	private static final String TERMINAL = "com.braintribe.devrock:malaclypse#[1.0,1.1)";

	@BeforeClass
	public static void before() {
		File settings = determineSettings();
		before( settings);
	}


	@Override
	protected void testPresence(Collection<Solution> solutions, File repository) {
		super.testPresence(solutions, repository);
	}	
	
	//@Test
	public void runMalaclypse() {
		run( TERMINAL); 				
	}
	
	
	

	
}
