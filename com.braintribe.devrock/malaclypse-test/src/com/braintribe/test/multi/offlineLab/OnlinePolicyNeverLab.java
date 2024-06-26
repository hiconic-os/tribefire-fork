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
package com.braintribe.test.multi.offlineLab;

import java.io.File;

import org.junit.BeforeClass;

public class OnlinePolicyNeverLab extends AbstractOfflinePolicyLab {
	protected static File settings = new File( "res/updatePolicyLab/contents/settings.never.xml");	
	
	@BeforeClass
	public static void before() {
		before( settings);
	}
		

	@Override
	protected String[] getResultsForFirstRun() {
		return new String [] {
				"com.braintribe.test.dependencies.updatePolicyTest:A#1.0",				
				"com.braintribe.test.dependencies.updatePolicyTest:B#1.0",									
		};
	}

	@Override
	protected String[] getResultsForSecondRun() {
		return new String [] {
				"com.braintribe.test.dependencies.updatePolicyTest:A#1.0",				
				"com.braintribe.test.dependencies.updatePolicyTest:B#1.0",									
		};
	}

	@Override
	protected void tweakEnvironment() {}
			
	
}