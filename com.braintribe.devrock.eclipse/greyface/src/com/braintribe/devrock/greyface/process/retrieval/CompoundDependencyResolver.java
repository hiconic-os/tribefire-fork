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
package com.braintribe.devrock.greyface.process.retrieval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.build.artifact.name.NameParser;
import com.braintribe.build.artifact.retrieval.multi.cache.SolutionWrapperCodec;
import com.braintribe.build.artifact.retrieval.multi.coding.DependencyWrapperCodec;
import com.braintribe.build.artifact.retrieval.multi.resolving.ResolvingException;
import com.braintribe.cc.lcd.CodingMap;
import com.braintribe.cc.lcd.CodingSet;
import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Part;
import com.braintribe.model.artifact.PartTuple;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.processing.part.PartTupleProcessor;
import com.braintribe.model.malaclypse.cfg.preferences.gf.RepositorySetting;

/**
 * a dependency resolver that uses delegates for several sources, both remote and/or local  
 * 
 * @author pit
 *
 */
public class CompoundDependencyResolver extends AbstractDependencyResolver {

	private List<RepositorySetting> sources;
	private List<AbstractDependencyResolver> delegates = new ArrayList<AbstractDependencyResolver>();
	
	private Map<String, RepositorySetting> resolvingMap = new HashMap<String, RepositorySetting>();
	private Map<AbstractDependencyResolver, RepositorySetting> resolverMap = new HashMap<AbstractDependencyResolver, RepositorySetting>();
	
	private Map<Dependency, Set<Solution>> dependencyToSolutionsMap = CodingMap.createHashMapBased( new DependencyWrapperCodec());
	
	
	public void setSources(List<RepositorySetting> settings) {
		this.sources = settings;
		
		for (RepositorySetting source : sources) {
			if (source.getPhonyLocal()) {
				if (source.getMavenCompatible()) {
					LocalDependencyResolver resolver = new LocalDependencyResolver();
					resolver.setLocalDirectoryRoot(source.getUrl());
					resolverMap.put( resolver, source);
					delegates.add(resolver);	
				}
				else {
					LocalSingleDirectoryDependencyResolver resolver = new LocalSingleDirectoryDependencyResolver();
					resolver.setLocalDirectory( source.getUrl());
					resolverMap.put( resolver, source);
					delegates.add(resolver);
				}
			}			
			else {
				GlobalDependencyResolver resolver = new GlobalDependencyResolver();
				resolver.setSetting(source);
				resolverMap.put( resolver, source);
				delegates.add(resolver);
			}
		}
	}
	
	@Override
	public Part resolvePomPart(String contextId, Part part) throws ResolvingException {
		try {
		for (AbstractDependencyResolver resolver : delegates) {
			Part pomPart = resolver.resolvePomPart(contextId, part);
			if (pomPart != null && pomPart.getLocation() != null) {
				resolvingMap.put( pomPart.getLocation(), resolverMap.get(resolver));
				return pomPart;		
			}
		}
		}
		catch (Throwable t) {
			System.out.println("caught!");
		}
		return null;
	}
	
	public RepositorySetting getSourceForPart( Part part) {
		return resolvingMap.get( part.getLocation());
	}

	@Override
	public Set<Solution> resolveTopDependency(String contextId, Dependency dependency) throws ResolvingException {
		
		Set<Solution> result = dependencyToSolutionsMap.get( dependency);
		if (result != null)
			return result;
		result = CodingSet.createHashSetBased( new SolutionWrapperCodec());
		PartTuple pomTuple = PartTupleProcessor.createPomPartTuple();
		for (AbstractDependencyResolver resolver : delegates) {
			try {
				Set<Solution> resolvedSolutions = resolver.resolveTopDependency(contextId, dependency);
				if (resolvedSolutions != null) {
					result.addAll(resolvedSolutions);			
					// put the files' locations to the map 
					for (Solution solution : resolvedSolutions) {
						 for (Part part : solution.getParts()) {
							 if (part == null) {
								 System.err.println("part unexpectedly null in [" + NameParser.buildName(solution) + "]");
								 continue;
							 }
							 if (PartTupleProcessor.equals( part.getType(), pomTuple)) {
								 resolvingMap.put( part.getLocation(), resolverMap.get(resolver));
							 }
						 }										 
					}
				}
			}
			catch (Exception e) {
				System.out.println("Exception caught");
			}
		}
		dependencyToSolutionsMap.put(dependency, new HashSet<Solution>(result));
		return result;
	}
	
	
}
