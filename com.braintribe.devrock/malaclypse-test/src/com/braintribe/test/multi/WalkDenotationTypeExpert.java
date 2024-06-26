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
package com.braintribe.test.multi;

import com.braintribe.model.malaclypse.cfg.denotations.ExclusionControlDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.ScopeControlDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.WalkDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.WalkDomain;
import com.braintribe.model.malaclypse.cfg.denotations.WalkKind;
import com.braintribe.model.malaclypse.cfg.denotations.clash.ClashResolverByDepthDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.clash.ClashResolverByIndexDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.clash.ClashResolverDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.clash.InitialDependencyClashPrecedence;
import com.braintribe.model.malaclypse.cfg.denotations.clash.OptimisticClashResolverDenotationType;
import com.braintribe.model.malaclypse.cfg.denotations.clash.ResolvingInstant;
import com.braintribe.model.malaclypse.cfg.denotations.scopes.DependencyScope;
import com.braintribe.model.malaclypse.cfg.denotations.scopes.MagicScope;
import com.braintribe.model.malaclypse.cfg.denotations.scopes.ScopeTreatement;
import com.braintribe.test.multi.AbstractWalkLab.ScopeKind;

public class WalkDenotationTypeExpert {

	public static ClashResolverDenotationType buildClashResolverDenotationType( InitialDependencyClashPrecedence initialPrecedence) {		
		return buildClashResolverDenotationType( ClashStyle.index, initialPrecedence, ResolvingInstant.adhoc);
	}
	
	public static ClashResolverDenotationType buildClashResolverDenotationType( ClashStyle clashStyle, InitialDependencyClashPrecedence initialPrecedence, ResolvingInstant resolvingInstant) {
		ClashResolverDenotationType clashResolvingType;
		switch (clashStyle) {
		case depth:
			clashResolvingType = ClashResolverByDepthDenotationType.T.create();
			break;
		case index:
			clashResolvingType = ClashResolverByIndexDenotationType.T.create();
			((ClashResolverByIndexDenotationType) clashResolvingType).setResolvingInstant(resolvingInstant);
			break;
		case optimistic:	
		default:
			clashResolvingType = OptimisticClashResolverDenotationType.T.create();
			break;		
		}
		if (initialPrecedence != null)
			clashResolvingType.setInitialClashPrecedence( initialPrecedence);
		return clashResolvingType;
	}
	
	public static WalkDenotationType buildCompileWalkDenotationType(ClashStyle clashStyle) {		
		//return buildCompileWalkDenotationType( buildClashResolverDenotationType(clashStyle, InitialDependencyClashPrecedence.pathIndex, ResolvingInstant.adhoc), ScopeKind.compile, WalkDomain.repository, WalkKind.classpath, true);
		return buildCompileWalkDenotationType( buildClashResolverDenotationType(clashStyle, InitialDependencyClashPrecedence.hierarchyIndex, ResolvingInstant.posthoc), ScopeKind.compile, WalkDomain.repository, WalkKind.classpath, true);
	}
	
	
	/**
	 * build a {@link WalkDenotationType} 
	 * @param scopeLevel - {@link ScopeKind}, compile or provided 
	 * @param walkDomain - {@link WalkDomain}, repository or workingcopy 
	 * @param walkKind - {@link WalkKind}, classpath or build order (latter not supported) 
	 * @param skipOptional - true 
	 * @return - {@link WalkDenotationType} configured
	 */
	public static WalkDenotationType buildCompileWalkDenotationType(ClashResolverDenotationType clashResolvingType, ScopeKind scopeLevel, WalkDomain walkDomain, WalkKind walkKind, boolean skipOptional, DependencyScope ... additionalIncludedScopes) {
		WalkDenotationType walkType = WalkDenotationType.T.create();
		walkType.setClashResolverDenotationType( clashResolvingType);
		
		ExclusionControlDenotationType exclusionControlType = ExclusionControlDenotationType.T.create();		
		walkType.setExclusionControlDenotationType( exclusionControlType);
		
		ScopeControlDenotationType scopeControlType = ScopeControlDenotationType.T.create();
		scopeControlType.setSkipOptional( skipOptional);		
		
		MagicScope scope;
		switch (scopeLevel) {
			case compile:
			default:
				scope = createClasspathMagicScope();
			break;
			case launch:
				scope = createRuntimeMagicScope();
			break;
		}
		scopeControlType.getScopes().add( scope);
		
		if (additionalIncludedScopes != null) {
			for (DependencyScope dependencyScope : additionalIncludedScopes) {
				scopeControlType.getScopes().add(dependencyScope);
			}
		}

		walkType.setScopeControlDenotationType( scopeControlType);
		walkType.setWalkDomain(walkDomain);
		walkType.setWalkKind(walkKind);
		return walkType;
	}

	
	/**
	 * creates a magic scope for compile 
	 * @return
	 */
	public static MagicScope createClasspathMagicScope() {
		MagicScope classpathScope = MagicScope.T.create();
		classpathScope.setName("classpathScope");
		
		DependencyScope compileScope = DependencyScope.T.create();
		compileScope.setName("compile");
		compileScope.setScopeTreatement( ScopeTreatement.INCLUDE);
		classpathScope.getScopes().add( compileScope);
		
		DependencyScope providedScope = DependencyScope.T.create();
		providedScope.setName("provided");
		providedScope.setScopeTreatement( ScopeTreatement.INCLUDE);		
		classpathScope.getScopes().add( providedScope);
		
		return classpathScope;
	}
	
	public static MagicScope createRuntimeMagicScope() {
		MagicScope runtimeScope = MagicScope.T.create();
		runtimeScope.setName("runtimeScope");
		
		DependencyScope compileScope = DependencyScope.T.create();
		compileScope.setName("compile");
		compileScope.setScopeTreatement( ScopeTreatement.INCLUDE);
		runtimeScope.getScopes().add( compileScope);
		
		DependencyScope providedScope = DependencyScope.T.create();
		providedScope.setName("runtime");
		providedScope.setScopeTreatement( ScopeTreatement.INCLUDE);		
		runtimeScope.getScopes().add( providedScope);
		
		return runtimeScope;
	}
	
}
