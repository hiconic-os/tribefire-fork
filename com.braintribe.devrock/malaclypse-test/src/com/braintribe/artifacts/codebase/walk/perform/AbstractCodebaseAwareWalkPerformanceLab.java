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
package com.braintribe.artifacts.codebase.walk.perform;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

import org.junit.Assert;

import com.braintribe.artifacts.codebase.AbstractCodebaseAwareLab;
import com.braintribe.build.artifact.representations.artifact.pom.ArtifactPomReader;
import com.braintribe.build.artifact.representations.artifact.pom.PomReaderException;
import com.braintribe.build.artifact.retrieval.multi.repository.reflection.impl.CrcValidationLevel;
import com.braintribe.build.artifact.walk.multi.WalkException;
import com.braintribe.build.artifact.walk.multi.Walker;
import com.braintribe.build.artifact.walk.multi.WalkerFactory;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.malaclypse.cfg.denotations.WalkDenotationType;
import com.braintribe.model.panther.SourceRepository;
import com.braintribe.test.multi.ClashStyle;
import com.braintribe.test.multi.WalkDenotationTypeExpert;
import com.braintribe.test.multi.WalkingExpert;
import com.braintribe.test.multi.realRepoWalk.Monitor;
import com.braintribe.testing.category.SpecialEnvironment;

public abstract class AbstractCodebaseAwareWalkPerformanceLab extends AbstractCodebaseAwareLab implements SpecialEnvironment{
	protected static File masterCodebase;
	
	private static WalkerFactory walkerFactory;
	
	
	
	protected static void runbefore(String templateStr) {
		try {
			runBefore( CrcValidationLevel.ignore);
		
			SourceRepository sourceRepository = SourceRepository.T.create();
			sourceRepository.setRepoUrl( masterCodebase.toURI().toURL().toString());			
		
			groupingAwareResolverFactory.setSourceRepository(sourceRepository);
			groupingAwareResolverFactory.setCodebaseTemplate( templateStr);
			pomExpertFactory.setDependencyResolverFactory(groupingAwareResolverFactory);
			
			// walk
			
			Monitor monitor = new Monitor();
			monitor.setVerbosity( true);
									
			walkerFactory = walkerFactory(groupingAwareResolverFactory);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("exception [" + e + "] thrown");
		}  
	}
	
	private Solution read(File file) {
		
		ArtifactPomReader reader = pomExpertFactory.getReader();
		
		try {
			Solution solution = reader.readPom( UUID.randomUUID().toString(), file);
			return solution;
		} catch (PomReaderException e) {
			e.printStackTrace();
			Assert.fail("exception [" + e + "] thrown");
		}
		return null;
	}
	protected void walk(File file, String [] expectedNames) {
		walk( file, expectedNames, 1, 0);
	}
	
	protected Collection<Solution> walk(File file, String [] expectedNames, int run, int threshold) {		
		Solution terminal = read( file);
		WalkDenotationType walkType = WalkDenotationTypeExpert.buildCompileWalkDenotationType(ClashStyle.optimistic);
		Walker walker = walkerFactory.apply(walkType);
		
		try {
			Collection<Solution> walkResult = WalkingExpert.walk( UUID.randomUUID().toString(), terminal, expectedNames, run, threshold, walker, true);
			return walkResult;			
		} catch (WalkException e) {
			e.printStackTrace();	
			Assert.fail("exception [" + e + "] thrown");
			return null;
		}
	}
	
}
