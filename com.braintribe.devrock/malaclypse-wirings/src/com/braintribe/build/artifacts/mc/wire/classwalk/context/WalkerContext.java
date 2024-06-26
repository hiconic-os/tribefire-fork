package com.braintribe.build.artifacts.mc.wire.classwalk.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

import com.braintribe.build.artifact.walk.multi.clash.listener.ClashResolverNotificationListener;
import com.braintribe.build.artifact.walk.multi.listener.WalkNotificationListener;
import com.braintribe.build.artifact.walk.multi.scope.ScopeControl;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.ClasspathResolverExternalContract;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.PartTuples;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.Scopes;
import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.ScopeContext;
import com.braintribe.model.artifact.PartTuple;
import com.braintribe.model.malaclypse.cfg.denotations.scopes.Scope;

/**
 * a scope context that adds the walk configuration, such as scopes, type- and tag-rules, relevant part tuples et al 
 * 
 * @author pit
 *
 */
public class WalkerContext implements ScopeContext {
	private String typeRule = "jar,pom";	
	private String tagRule;
	private boolean abortOnUnresolvedDependency = true;
	private boolean ignoreDependencyScopes = false;
	private Set<Scope> scopes;
	private Boolean skipOptionals;
	private Collection<PartTuple> relevantPartTuples = new ArrayList<>(Arrays.asList(PartTuples.standardPartTuples()));
	private Predicate<? super PartTuple> relevantPartPredicate;
	private ClashResolverNotificationListener clashResolverListener;
	private WalkNotificationListener walkNotificationListener;
	
	public String getTypeRule() {
		return typeRule;
	}
	
	/**
	 * sets the TYPE rule for the walker, default is "jar"
	 * @param typeRule - the type rule to use during traversing
	 */
	@Configurable
	public void setTypeRule(String typeRule) {
		this.typeRule = typeRule;
	}
	
	public String getTagRule() {
		return tagRule;	
	}
	/**
	 * set the TAG rule for the walker, default is NULL
	 * @param tagRule - the tag rule to use during traversing 
	 */
	@Configurable
	public void setTagRule(String tagRule) {
		this.tagRule = tagRule;
	}
	
	
	public boolean getAbortOnUnresolvedDependency() {
		return abortOnUnresolvedDependency;
	}	
	/**
	 * set whether the walker should abort if an unresolved dependency is encountered, default is true
	 * @param abortOnUnresolvedDependency - true if unresolved dependencies should abort, false if to be continued
	 */
	@Configurable
	public void setAbortOnUnresolvedDependency(boolean abortOnUnresolvedDependency) {
		this.abortOnUnresolvedDependency = abortOnUnresolvedDependency;
	}

	/**
	 * scopes as generated by {@link Scopes#compileScopes()} or {@link Scopes#runtimeScopes()} or custom.. 
	 * if null, the value from the {@link ClasspathResolverExternalContract} is used
	 * @return - the {@link Set} of {@link Scope} to use
	 */
	public Set<Scope> getScopes() {
		return scopes;
	}
	/**
	 * set the scopes to use
	 * if null, the value from the {@link ClasspathResolverExternalContract} is used
	 * @param scopes a {@link Set} of {@link Scope}
	 */
	public void setScopes(Set<Scope> scopes) {
		this.scopes = scopes;
	}

	/**
	 * whether to skip optionals.. 
	 * if null, the value from the {@link ClasspathResolverExternalContract} is used
	 * @return
	 */
	public Boolean getSkipOptionals() {
		return skipOptionals;
	}
	/**
	 * set whether to skip optionals or not..
	 * if null, the value from the {@link ClasspathResolverExternalContract} is used
	 * @param skipOptionals
	 */
	public void setSkipOptionals(Boolean skipOptionals) {
		this.skipOptionals = skipOptionals;
	}

	/**
	 * gets the configured {@link PartTuple}
	 * @return - a {@link Collection} of the {@link PartTuple} to enrich
	 */
	public Collection<PartTuple> getRelevantPartTuples() {
		return relevantPartTuples;
	}
	/**
	 * configures the {@link PartTuple} that the walk's enricher should get.. 
	 * if null the settings from the 
	 * @param relevantPartTuples
	 */
	public void setRelevantPartTuples(Collection<PartTuple> relevantPartTuples) {
		this.relevantPartTuples = relevantPartTuples;
	}

	/**
	 * gets the alternative predicate to determine the relevant {@link PartTuple}
	 * @return
	 */
	public Predicate<? super PartTuple> getRelevantPartPredicate() {
		return relevantPartPredicate;
	}
	/**
	 * alternatively to {@link WalkerContext#setRelevantPartTuples(Collection)}, you can specify a 
	 * predicate that is asked to 
	 * @param relevantPartPredicate
	 */
	public void setRelevantPartPredicate(Predicate<? super PartTuple> relevantPartPredicate) {
		this.relevantPartPredicate = relevantPartPredicate;
	}

	public ClashResolverNotificationListener getClashResolverListener() {
		return clashResolverListener;
	}
	/**
	 * if not null, overrides the {@link ClashResolverNotificationListener} as declared by the {@link ClasspathResolverExternalContract}
	 * @param clashResolverListener - a {@link ClashResolverNotificationListener}
	 */
	public void setClashResolverListener(ClashResolverNotificationListener clashResolverListener) {
		this.clashResolverListener = clashResolverListener;
	}

	
	public WalkNotificationListener getWalkNotificationListener() {
		return walkNotificationListener;
	}

	/**
	 * if not null, overrides the {@link WalkNotificationListener} as declared by the {@link ClasspathResolverExternalContract}
	 * @param walkNotificationListener
	 */
	public void setWalkNotificationListener(WalkNotificationListener walkNotificationListener) {
		this.walkNotificationListener = walkNotificationListener;
	}

	/**
	 * true if MC's {@link ScopeControl} should implement a PASS-THROUGH behavior, i.e. accept any scope  
	 * @return - true if set to ignore, false to act on scopes
	 */
	public boolean getIgnoreDependencyScopes() {
		return ignoreDependencyScopes;
	}

	/**
	 * set true if MC's {@link ScopeControl} should implement a PASS-THROUGH behavior, i.e. accept any scope, false otherwise
	 * @param ignoreDependencyScopes - true to ignore, false to act on scopes
	 */
	public void setIgnoreDependencyScopes(boolean ignoreDependencyScopes) {
		this.ignoreDependencyScopes = ignoreDependencyScopes;
	}
		
	
		
}
