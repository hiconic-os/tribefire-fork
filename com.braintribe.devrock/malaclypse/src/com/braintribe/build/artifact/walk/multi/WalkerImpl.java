// ============================================================================
// BRAINTRIBE TECHNOLOGY GMBH - www.braintribe.com
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2018 - All Rights Reserved
// It is strictly forbidden to copy, modify, distribute or use this code without written permission
// To this file the Braintribe License Agreement applies.
// ============================================================================

package com.braintribe.build.artifact.walk.multi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.build.artifact.name.NameParser;
import com.braintribe.build.artifact.representations.artifact.pom.ArtifactPomReader;
import com.braintribe.build.artifact.representations.artifact.pom.PomExpertFactory;
import com.braintribe.build.artifact.representations.artifact.pom.PomReaderException;
import com.braintribe.build.artifact.representations.artifact.pom.listener.PomReaderNotificationListener;
import com.braintribe.build.artifact.retrieval.multi.coding.ArtifactWrapperCodec;
import com.braintribe.build.artifact.retrieval.multi.coding.DependencyWrapperCodec;
import com.braintribe.build.artifact.retrieval.multi.coding.IdentificationWrapperCodec;
import com.braintribe.build.artifact.retrieval.multi.enriching.EnrichingException;
import com.braintribe.build.artifact.retrieval.multi.enriching.MultiRepositorySolutionEnricher;
import com.braintribe.build.artifact.retrieval.multi.enriching.listener.SolutionEnricherNotificationListener;
import com.braintribe.build.artifact.retrieval.multi.resolving.DependencyResolver;
import com.braintribe.build.artifact.retrieval.multi.resolving.ResolvingException;
import com.braintribe.build.artifact.retrieval.multi.resolving.listener.DependencyResolverNotificationListener;
import com.braintribe.build.artifact.walk.multi.clash.ClashResolver;
import com.braintribe.build.artifact.walk.multi.clash.ClashResolvingException;
import com.braintribe.build.artifact.walk.multi.clash.listener.ClashResolverNotificationListener;
import com.braintribe.build.artifact.walk.multi.exclusion.ExclusionControl;
import com.braintribe.build.artifact.walk.multi.exclusion.ExclusionControlException;
import com.braintribe.build.artifact.walk.multi.filters.TagRuleFilter;
import com.braintribe.build.artifact.walk.multi.filters.TypeRuleFilter;
import com.braintribe.build.artifact.walk.multi.listener.WalkNotificationBroadcaster;
import com.braintribe.build.artifact.walk.multi.listener.WalkNotificationListener;
import com.braintribe.build.artifact.walk.multi.scope.ScopeControl;
import com.braintribe.build.process.ProcessAbortSignaller;
import com.braintribe.cc.lcd.CodingMap;
import com.braintribe.cc.lcd.CodingSet;
import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.logging.Logger;
import com.braintribe.model.artifact.Artifact;
import com.braintribe.model.artifact.ArtifactDeclarationType;
import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Exclusion;
import com.braintribe.model.artifact.Identification;
import com.braintribe.model.artifact.Part;
import com.braintribe.model.artifact.PartTuple;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.processing.part.PartTupleProcessor;
import com.braintribe.model.artifact.version.Version;
import com.braintribe.model.malaclypse.cfg.denotations.WalkDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.WalkKind;


public class WalkerImpl implements ConfigurableWalker, WalkNotificationBroadcaster, WalkNotificationListener {
	
	private static Logger log = Logger.getLogger(WalkerImpl.class);
	private DependencyResolver resolver;
	private MultiRepositorySolutionEnricher enricher;
	private PomExpertFactory pomExpertFactory;
	private ClashResolver clashResolver;
	private ScopeControl scopeControl;
	private ExclusionControl exclusionControl;
	private WalkKind requestedWalkKind = WalkKind.classpath;
	
	private Artifact terminalArtifact;
	private WalkCache cache = new WalkCacheImpl();
	
	private int dependencyIndex = 0;
	private Set<WalkNotificationListener> listeners;
	private ProcessAbortSignaller abortSignaller;
	private WalkDenotationType denotationType;	
	
	
	private TagRuleFilter tagRuleFilter = new TagRuleFilter();
	
	private TypeRuleFilter typeRuleFilter = new TypeRuleFilter();
	private boolean abortIfUnresolvedDependencyIsFound = true;

	private int traversingOrder = 0;
		
	@Override @Configurable
	public void setAbortSignaller(ProcessAbortSignaller signaller) {
		abortSignaller = signaller;		
	}

	@Override @Configurable @Required
	public void setResolver(DependencyResolver resolver) {
		this.resolver = resolver;	
	}

	@Override @Configurable @Required
	public void setEnricher(MultiRepositorySolutionEnricher enricher) {
		this.enricher = enricher;
	}

	@Override @Configurable @Required
	public void setClashResolver(ClashResolver resolver) {
		clashResolver = resolver;
	}
	
	@Override @Configurable @Required
	public void setPomExpertFactory(PomExpertFactory pomExpertFactory) {
		this.pomExpertFactory = pomExpertFactory;
	}
	
	@Override @Configurable @Required
	public void setScopeControl(ScopeControl scopeControl) {
		this.scopeControl = scopeControl;
	}
	@Override @Configurable @Required
	public void setExclusionControl(ExclusionControl exclusionControl) {
		this.exclusionControl = exclusionControl;
	}	
		
	@Override @Configurable
	public void setWalkKind(WalkKind walkKind) {
		requestedWalkKind = walkKind;		
	}
	
	@Override @Configurable
	public void setTagRule(String tagRule) {
		tagRuleFilter.setRule(tagRule);
	}
	

	@Override @Configurable
	public void setTypeFilter(String typeFilter) {
		typeRuleFilter.setRule( typeFilter);		
	}
	
	@Override @Configurable 
	public void setAbortIfUnresolvedDependencyIsFound(boolean abortIfUnresolvedDependencyIsFound) {
		this.abortIfUnresolvedDependencyIsFound = abortIfUnresolvedDependencyIsFound;
	}

	@Override
	public void addListener(WalkNotificationListener listener) {
		if (listeners == null) {
			listeners = new HashSet<WalkNotificationListener>();
		}		
		listeners.add( listener);
	}

	@Override
	public void removeListener(WalkNotificationListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
		if (listeners.isEmpty())
			listeners = null;
	}

	@Override
	public Artifact get() throws RuntimeException {
		return terminalArtifact;
	}
	
	@Override
	public  void wireListeners() {
		// attach listeners 
		for (WalkNotificationListener listener : listeners) {		
			// dependency resolver
			if (listener instanceof DependencyResolverNotificationListener) { 
				resolver.addListener( (DependencyResolverNotificationListener) listener);
			}
			// pom reader
			synchronized (pomExpertFactory) {
				if (listener instanceof PomReaderNotificationListener) {
					pomExpertFactory.addListener( (PomReaderNotificationListener) listener);
				}
			}
			// solution enricher
			if (listener instanceof SolutionEnricherNotificationListener) {
				enricher.addListener( (SolutionEnricherNotificationListener) listener);
			}
			// clash resolver
			if (listener instanceof ClashResolverNotificationListener) {
				clashResolver.addListener( (ClashResolverNotificationListener) listener);
			}		
		}
	}
					
	@Override
	public void unwireListeners(Set<WalkNotificationListener> listeners) {
		for (WalkNotificationListener listener : listeners) {		
			// dependency resolver
			if (listener instanceof DependencyResolverNotificationListener) { 
				resolver.removeListener( (DependencyResolverNotificationListener) listener);
			}
			// pom reader
			synchronized (pomExpertFactory) {
				if (listener instanceof PomReaderNotificationListener) {
					pomExpertFactory.removeListener( (PomReaderNotificationListener) listener);
				}
			}
			// solution enricher
			if (listener instanceof SolutionEnricherNotificationListener) {
				enricher.removeListener( (SolutionEnricherNotificationListener) listener);
			}
			// clash resolver
			if (listener instanceof ClashResolverNotificationListener) {
				clashResolver.removeListener( (ClashResolverNotificationListener) listener);
			}		
		}
		
	}
	
	

	@Override
	public void acknowledeDenotationType(WalkDenotationType walkDenotationType) {
		this.denotationType = walkDenotationType;		
	}
	
	private void logElapsedTime( String msg, String id, long before, long after, boolean publicMsg) {	
		if (!publicMsg) {
			log.debug( msg + " for [" + id + "] took [" + ((after-before) / 1E6) + "] ms");
		} else {
			log.info( msg + " for [" + id + "] took [" + ((after-before) / 1E6) + "] ms");
		}			
	}
	
	private void dump( Solution solution, Collection<Dependency> deps) {

			StaxMarshaller staxMarshaller = new StaxMarshaller();
			String name = NameParser.buildName(solution).replace(':', '.');			
			File file = new File(name + ".dump.txt");
			System.out.println("writing to [" + file.getAbsolutePath() + "]");
			try ( 
				  OutputStream out = new FileOutputStream( file);
				) {
				staxMarshaller.marshall(out, deps);
			}
			catch (Exception e) {
				;
			}
				
		
	}

	@Override
	public Collection<Solution> walk(String walkScopeId, Solution solution) throws WalkException {		
		// broadcast start 
		acknowledgeStartOn(walkScopeId, solution, denotationType);
		
		traversingOrder = 0;
		dependencyIndex = 0;
		terminalArtifact = solution;
		long before = System.nanoTime();
		try {
			// broadcast traversing phase
			acknowledgeTraversingPhase(walkScopeId);
			long beforeTraversing = System.nanoTime();
			// do the walk
			walk( walkScopeId, solution, null, 0);
			long afterTraversing = System.nanoTime();
			logElapsedTime("traversing [" + NameParser.buildName( solution) + "]", walkScopeId, beforeTraversing, afterTraversing, false);
			// extract found dependencies 
			Set<Dependency> collectedDependencies = cache.getCollectedDependencies();
			//
			// post process 
			//
			int numDepenendencies = collectedDependencies.size();
			// broadcast dependency clash resolving phase 
			acknowledgeDependencyClashResolvingPhase(walkScopeId, numDepenendencies);
			
			long beforeDependencyClashResolving = System.nanoTime();
			// clash resolving - clean-up dependencies list
			List<Dependency> resolvedDependencies;
			try {
				resolvedDependencies = clashResolver.resolveDependencyClashes( walkScopeId, solution, collectedDependencies);
			} catch (Exception e1) {
				throw new WalkException( e1);
			}
			long afterDependencyClashResolving = System.nanoTime();
			logElapsedTime("dependency clash resolving [" + NameParser.buildName( solution) + "]", walkScopeId, beforeDependencyClashResolving, afterDependencyClashResolving, false);
			// try to reassign undetermined dependencies 
			Set<Dependency> undeterminedDependencies = cache.getCollectedUnDeterminedDependencies();
			int numUndetermined = undeterminedDependencies.size(); 
			if ( numUndetermined > 0) {
				// broadcast determination phase
				acknowledgeDeterminationPhase( walkScopeId, numUndetermined);
				long beforeReassignment = System.nanoTime();
				Map<Identification, Dependency> lenientMap = CodingMap.createHashMapBased( new IdentificationWrapperCodec());
				for (Dependency dependency : resolvedDependencies) {
					lenientMap.put(dependency, dependency);
				}			
				for (Dependency undeterminedDependency : undeterminedDependencies) {
					Dependency replacementDependency = lenientMap.get(undeterminedDependency);
					// re-assign 
					if (replacementDependency != null) {
						// broadcast reassignment
						acknowledgeReassignedDependency(walkScopeId, undeterminedDependency, replacementDependency);
						Set<Artifact> requesters = undeterminedDependency.getRequestors();
						for (Artifact requester : requesters) {
							requester.getDependencies().remove( undeterminedDependency);												
							requester.getDependencies().add( replacementDependency);
							replacementDependency.getRequestors().add(requester);
							undeterminedDependency.setAutoDefined(true);
							undeterminedDependency.setVersionRange( replacementDependency.getVersionRange());
						}
					} 
					else {
						// broadcast reassigned undetermined dependency
						acknowledgeUndeterminedDependency(walkScopeId, undeterminedDependency);
					}
				}
				long afterReassignment = System.nanoTime();
				logElapsedTime("reassignment [" + NameParser.buildName( solution) + "]", walkScopeId, beforeReassignment, afterReassignment, false);
			}
			
			Set<Solution> solutions = CodingSet.createHashSetBased( new ArtifactWrapperCodec());
			
			for (Dependency dependency : resolvedDependencies) {				
				solutions.addAll( dependency.getSolutions());							
			}
			// broadcast collected dependencies 
			acknowledgeCollectedDependencies(walkScopeId, resolvedDependencies);
			
			// broadcast solution clash resolving phase 
			int numSolutions = solutions.size();
			acknowledgeSolutionClashResolvingPhase(walkScopeId, numSolutions);
			long beforeSolutionClashResolving = System.nanoTime();
			// clash resolving on solutions (optimistic sort of solutions)
			List<Solution> clearedSolutions;
			try {
				clearedSolutions = clashResolver.resolveSolutionClashes(walkScopeId, solutions);
			} catch (Exception e1) {
				throw new WalkException(e1);
			}
			long afterSolutionClashResolving = System.nanoTime();		
			logElapsedTime("solution clash resolving [" + NameParser.buildName( solution) + "]", walkScopeId, beforeSolutionClashResolving, afterSolutionClashResolving, false);
		
			// broadcast enriching phase 
			int numEnrichingSolutions = clearedSolutions.size();
			acknowledgeEnrichingPhase( walkScopeId, numEnrichingSolutions);
			
			// enrich the solutions
			try {
				enricher.setAbortSignaller(abortSignaller);
				long beforeEnriching = System.nanoTime();
				enricher.enrich( walkScopeId, clearedSolutions);
				long afterEnriching = System.nanoTime();
				logElapsedTime("enriching [" + NameParser.buildName( solution) + "]", walkScopeId, beforeEnriching, afterEnriching, false);
			} catch (EnrichingException e) {
				throw new WalkException(e);
			}
			// if WalkKind's a build order, then we should sort according number of requesters 	
			long beforeFinalSort = System.nanoTime();
			switch (requestedWalkKind) {
				case hierarchy:
					// 
					clearedSolutions.sort( new Comparator<Solution>() {
						@Override
						public int compare(Solution solution1, Solution solution2) {											
							if (solution1.getHierarchyLevel()  > solution2.getHierarchyLevel()) {
								return -1;
							}
							else { 
								return 1;
							}
						}					
					});
					break;
				case buildOrder:
					// 
					clearedSolutions.sort( Comparator.comparing( Solution::getOrder));
					break;
					default: 
						break;
			}
			long afterFinalSort = System.nanoTime();
			logElapsedTime("final sort", walkScopeId, beforeFinalSort, afterFinalSort, false);
			acknowledgeWalkResult(walkScopeId, clearedSolutions);
			long after = System.nanoTime();
			logElapsedTime("overall walk on " + NameParser.buildName( solution)+ "]" , walkScopeId, before, after, false);
			return clearedSolutions;
		}
		finally {
			// broadcast end 
			acknowledgeEndOn(walkScopeId, solution);
		}
				
	}
	
	/**
	 * actually perform the walk, will be called recursively on found solutions 
	 * @param solution - solutions to parse 
	 * @param level - the current hierarchy level, 0 being the level of the terminal 
	 * @throws WalkException -
	 */
	/**
	 * @param walkScopeId
	 * @param solution
	 * @param parentDependency
	 * @param level
	 * @throws WalkException
	 */
	/**
	 * @param walkScopeId
	 * @param solution
	 * @param parentDependency
	 * @param level
	 * @throws WalkException
	 */
	/**
	 * @param walkScopeId
	 * @param solution
	 * @param parentDependency
	 * @param level
	 * @throws WalkException
	 */
	private void walk( String walkScopeId, Solution solution, Dependency parentDependency, int level) throws WalkException {
		if (abortSignaller != null && abortSignaller.abortScan()) {
			return;
		}
		// read the pom (resolve and read) 
		ArtifactPomReader reader = pomExpertFactory.getReader();
		try {
			// store the values from the resolving, as reading the pom may destroy that information again 
			Set<Dependency> determinedRequesters = solution.getRequestors();
			Version version = solution.getVersion();
			int hierarchyLevel = solution.getHierarchyLevel();
			
			solution = reader.read(walkScopeId, solution);						

			// reassign the stored information 
			solution.setRequestors(determinedRequesters);
			solution.setHierarchyLevel(hierarchyLevel);
			solution.setVersion(version);
			acknowledgeTraversing(walkScopeId, solution, parentDependency, level, true);
		} catch (PomReaderException e) {
			acknowledgeTraversing(walkScopeId, solution, parentDependency, level, false);
			throw new WalkException(e);
		}		
		// check parent if any dependencies are to be inherited.. 
		Solution parent = solution.getResolvedParent();					
		// multi stage dependency inheritance ?		
		if (parent != null) {
			Set<Dependency> dependenciesToAdd = CodingSet.createHashSetBased( new DependencyWrapperCodec());
			dependenciesToAdd.addAll( solution.getDependencies());
			
			Solution current = parent;
			do {
				for (Dependency d : current.getDependencies()) {			
					if (!dependenciesToAdd.contains(d)) {
						// attach to end of parent
						solution.getDependencies().add(d);
						// add to check list
						dependenciesToAdd.add(d);
					}
				}
				current = current.getResolvedParent();
			} while (current != null);
			
			
		}	
		//TODO : honor the global exclusion / dominants pom section
		
		//
		// iterate over dependencies
		// 
		Set<Dependency> dependenciesToAdd = new HashSet<Dependency>();
		List<Dependency> dependenciesToIterate = solution.getDependencies();

		// if level == 0 : 
		// extract all dependencies with scope == provided, and build exclusions from them, push them to exclusion control,  
		// pop afterwards.. 
		Set<Exclusion> inducedExclusions = null;
		if (level == 0) {
			inducedExclusions = scopeControl.infereExclusionsFromDependencies( dependenciesToIterate);
			if (inducedExclusions != null) {
				exclusionControl.push( inducedExclusions);
			}
			// filter acc tag rule
			dependenciesToIterate = tagRuleFilter.filterDependencies(dependenciesToIterate);
		}
		
		// filter acc type rule (part tuples) : this filter is always active 
		dependenciesToIterate = typeRuleFilter.filterDependencies(dependenciesToIterate);

		// reattach filtered dependency list to solution( not the same instance anymore)
		solution.setDependencies(dependenciesToIterate);
		Iterator<Dependency> iterator = dependenciesToIterate.iterator();
		
		
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();			
			
			String dependencyClassifier = dependency.getClassifier();
			if (	dependencyClassifier != null &&
					(dependencyClassifier.equalsIgnoreCase("sources") || dependencyClassifier.equalsIgnoreCase("javadoc"))
				) {
				String msg = "classifier of [" + NameParser.buildName( (Identification) dependency) + "] : ["+ dependencyClassifier + "] cannot contribute to classpath. Ignored";
				log.warn(msg);
				continue;
			}
						
			//
			// check if excluded
			//
			// a) per exclusion
			if (exclusionControl.isExcluded(dependency)) {
				dependency.setExcluded(true);
				iterator.remove();
				if (log.isDebugEnabled()) {
					log.debug( "Dependency [" + NameParser.buildName(dependency) + "] of [" + NameParser.buildName(solution) + "] is excluded because it's listed to be excluded");
				}
				continue;
			}
			// b) per scope 
			String scope = dependency.getScope();			
			if (scopeControl.isExcluded( scope, level, dependency.getOptional())) {
				dependency.setExcluded(true);
				iterator.remove();
				if (log.isDebugEnabled()) {
					log.debug( "Dependency [" + NameParser.buildName(dependency) + "] of [" + NameParser.buildName(solution) + "] is excluded because its scope [" + dependency.getScope() + "] is either not included, or it's optional (and optionals are skipped)");
				}
				continue;
			}
			
			
			// if marked invalid by the pom reader -> abort 
			if (dependency.getIsInvalid()) {
				String reason = dependency.getInvalidationReason();
				String msg = "dependency [" + NameParser.buildName(dependency) + "] has been marked as invalid ";
				if (reason != null) {
					msg = msg + "(" + reason + ")";
				}
				log.error( msg);
				throw new WalkException(msg);
			}
						
			dependency.setHierarchyLevel(level);
			dependency.setPathIndex(dependencyIndex++);
			if (log.isDebugEnabled()) {
				log.debug("traversing [" + NameParser.buildName(dependency) + "] @ [" + (dependencyIndex-1) + "] : [" + level + "]");
			}

			// not excluded, so process ... 
			if (dependency.getVersionRange() == null || dependency.getVersionRange().getUndefined()) {
				// add the requestor now, here 
				dependency.getRequestors().add( solution);
				cache.addUnDeterminedDependency(dependency);				
				continue;
			}
					
			// check if already processed
			Dependency alreadyProcessedDependency = cache.containsDependency(dependency); 
					
			if (alreadyProcessedDependency == null) {			 
				try {
					alreadyProcessedDependency = clashResolver.adhocClashResolving(walkScopeId, dependency);					
				} catch (ClashResolvingException e) {
					throw new WalkException(e);
				} 
			}
			
			if (alreadyProcessedDependency != null) {
				// check classifiers & warn if mismatch
				String currentClassifier = alreadyProcessedDependency.getClassifier();
				String requestedClassifier = dependency.getClassifier();
				
				//				
				
				if (currentClassifier != null) {
					if (requestedClassifier == null || !requestedClassifier.equalsIgnoreCase(currentClassifier)) {
						String msg = "clashing classifiers in [" + NameParser.buildName( (Identification) dependency) + "]: ["+ currentClassifier + "] vs [" + requestedClassifier + "]";
						log.warn(msg);
						acknowledgeClashOnDependencyClassifier(walkScopeId, alreadyProcessedDependency, currentClassifier, requestedClassifier);
					}
				}
				else {
					if (requestedClassifier != null) {
						String msg = "clashing classifiers in [" + NameParser.buildName( (Identification) dependency) + "]: ["+ currentClassifier + "] vs [" + requestedClassifier + "]";
						log.warn(msg);
						acknowledgeClashOnDependencyClassifier(walkScopeId, alreadyProcessedDependency, currentClassifier, requestedClassifier);
					}
				}
				
				// transfer tags .. 
				alreadyProcessedDependency.getTags().addAll( dependency.getTags());
				
				alreadyProcessedDependency.getRequestors().add(solution);				
				dependenciesToAdd.add(alreadyProcessedDependency);
				// just for wiring... 
				dependency.getRequestors().add( solution);
				iterator.remove();
				// acknowledge cached part .. 			
				acknowledgeTraversingEndpoint(walkScopeId, alreadyProcessedDependency, solution, level);
				continue;
			} 
			else {
				dependency.getRequestors().add(solution);
				acknowledgeTraversing( walkScopeId, dependency, solution, level);
			}
						
			// 
			// dive deeper into the dependency
			// 
			walkDependency(solution, level, walkScopeId, dependency);

		}	
		if (inducedExclusions != null) {
			exclusionControl.pop();
		}
		
		solution.getDependencies().addAll(dependenciesToAdd);
	}
	
	

	private void walkDependency(Solution parent, int hierarchyLevel, String walkScopeId, Dependency dependency) throws WalkException{		
		if (abortSignaller != null && abortSignaller.abortScan()) {
			return;
		}
		// add dependency to cache 
		cache.addDependency(dependency);
		
		try {
			exclusionControl.push( dependency.getExclusions());
		} catch (ExclusionControlException e) {
			throw new WalkException(e);
		}
		try {
			Set<Solution> resolvedSolutions = resolver.resolveDependency(walkScopeId, dependency);
			
			if (resolvedSolutions == null || resolvedSolutions.size() == 0) {
				dependency.setUnresolved(true);
				dependency.getRequestors().add( parent);
				acknowledgeUnresolvedDependency(walkScopeId, dependency);
				String msg = "unresolved dependency [" + NameParser.buildName(dependency) + "] encountered in [" + NameParser.buildName(parent) + "]";
				if (abortIfUnresolvedDependencyIsFound) {
					log.error(msg);
					throw new WalkException(msg); 
				}
				if (log.isWarnEnabled()){
					log.warn(msg);
				}
				return;
			}
			
			// flat via solution?
			if (parent.getDeclarationType() == ArtifactDeclarationType.FLAT) {
				return;
			}
			// 
			// iterate over the solutions of the dependency 
			// 
			for (Solution resolvedSolution : resolvedSolutions) {					
				
				// check if this solution has been processed already 				
				Solution cachedSolution = cache.containsSolution(resolvedSolution);
				if (cachedSolution != null) {
					// wire 
					cachedSolution.getRequestors().add(dependency);
					dependency.getSolutions().add( cachedSolution);
					// 
					acknowledgeTraversingEndpoint(walkScopeId, cachedSolution, dependency, hierarchyLevel);
					continue;
				}
				// set hierarchy level (of dependency) 
				resolvedSolution.setHierarchyLevel( hierarchyLevel);
				
				// add solution to cache - to be extracted later 
				cache.addSolution(resolvedSolution);
				
				// wire
				resolvedSolution.getRequestors().add(dependency);
				dependency.getSolutions().add(resolvedSolution);
				
				Part pomPart = getPomPartFromResolvedSolution(resolvedSolution);					
				
				if (pomPart.getLocation() == null) {
					resolvedSolution.setUnresolved(true);						
				}
				else {										
					// walk deeper
					walk( walkScopeId, resolvedSolution, dependency, hierarchyLevel+1);
					resolvedSolution.setOrder( traversingOrder++);
				}					
			}
		} catch (ResolvingException e) {
			throw new WalkException(e);
		}
		exclusionControl.pop();
	}
	
	private Part getPomPartFromResolvedSolution ( Solution solution) {
		Set<Part> parts = solution.getParts();
		PartTuple pomTuple = PartTupleProcessor.createPomPartTuple();
		for (Part part : parts) {
			if (PartTupleProcessor.equals(part.getType(), pomTuple)) {
				return part;
			}
		}
		throw new WalkException("no pom part found for resolved solution [" + NameParser.buildName(solution) + "]");
		
	}

	@Override
	public void acknowledgeStartOn(String walkScopeId, Solution solution, WalkDenotationType denotationType) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeStartOn(walkScopeId, solution, denotationType);
			}
		}			
	}

	@Override
	public void acknowledgeEndOn(String walkScopeId, Solution solution) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeEndOn(walkScopeId, solution);
			}
		}	
	}

	
	@Override
	public void acknowledgeWalkResult(String walkScopeId, List<Solution> solutions) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeWalkResult(walkScopeId, solutions);
			}
		}	
		
	}

	@Override
	public void acknowledgeUnresolvedDependency(String walkScopeId, Dependency dependency) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeUnresolvedDependency(walkScopeId, dependency);
			}
		}			
	}

	@Override
	public void acknowledgeReassignedDependency(String walkScopeId, Dependency undetermined, Dependency replacement) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeReassignedDependency(walkScopeId, undetermined, replacement);
			}
		}			
	}

	@Override
	public void acknowledgeUndeterminedDependency(String walkScopeId, Dependency undetermined) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeUndeterminedDependency(walkScopeId, undetermined);
			}
		}		
	}

	@Override
	public void acknowledgeTraversing(String walkScopeId, Solution solution, Dependency parent, int level, boolean isvalid) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeTraversing(walkScopeId, solution, parent, level, isvalid);				
			}
		}		
	}
	

	@Override
	public void acknowledgeTraversing(String walkScopeId, Dependency dependency, Solution parent, int level) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeTraversing( walkScopeId, dependency, parent, level);
			}
		}			
	}

	@Override
	public void acknowledgeTraversingEndpoint(String walkScopeId, Dependency dependency, Solution parent, int level) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeTraversingEndpoint( walkScopeId, dependency, parent, level);
			}
		}			
	}
	@Override
	public void acknowledgeTraversingEndpoint(String walkScopeId, Solution solution, Dependency parent, int level) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeTraversingEndpoint( walkScopeId, solution, parent, level);
			}
		}			
	}
	@Override
	public void acknowledgeTraversingPhase(String walkScopeId ) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeTraversingPhase(walkScopeId);
			}
		}	
		
	}

	@Override
	public void acknowledgeDeterminationPhase(String walkScopeId, int numUndetermined) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeDeterminationPhase(walkScopeId, numUndetermined);
			}
		}	
		
	}

	@Override
	public void acknowledgeDependencyClashResolvingPhase(String walkScopeId, int nunDependencies) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeDependencyClashResolvingPhase(walkScopeId, nunDependencies);
			}
		}			
	}
	
	

	@Override
	public void acknowledgeCollectedDependencies(String walkScopeId, List<Dependency> collectedDependencies) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeCollectedDependencies(walkScopeId, collectedDependencies);
			}
		}				
	}

	@Override
	public void acknowledgeSolutionClashResolvingPhase(String walkScopeId, int numSolutions) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeSolutionClashResolvingPhase(walkScopeId, numSolutions);
			}
		}	
		
	}

	@Override
	public void acknowledgeEnrichingPhase(String walkScopeId, int numSolutions) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeEnrichingPhase(walkScopeId, numSolutions);
			}
		}			
	}

	@Override
	public void acknowledgeClashOnDependencyClassifier(String walkScopeId, Dependency dependency, String current, String requested) {
		if (listeners != null) {
			for (WalkNotificationListener listener : listeners) {
				listener.acknowledgeClashOnDependencyClassifier(walkScopeId, dependency, current, requested);
			}			
		}
	}
	
	
	
		
}
