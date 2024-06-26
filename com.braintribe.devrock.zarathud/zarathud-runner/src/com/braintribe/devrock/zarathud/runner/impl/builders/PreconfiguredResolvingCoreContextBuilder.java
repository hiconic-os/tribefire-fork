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
package com.braintribe.devrock.zarathud.runner.impl.builders;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.braintribe.devrock.mc.api.classpath.ClasspathDependencyResolver;
import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionContext;
import com.braintribe.devrock.zarathud.model.ResolvingRunnerContext;
import com.braintribe.devrock.zarathud.model.context.ConsoleOutputVerbosity;
import com.braintribe.devrock.zarathud.wirings.core.context.CoreContext;
import com.braintribe.logging.Logger;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.model.artifact.analysis.AnalysisDependency;
import com.braintribe.model.artifact.analysis.AnalysisTerminal;
import com.braintribe.model.artifact.compiled.CompiledArtifact;
import com.braintribe.model.artifact.compiled.CompiledDependencyIdentification;
import com.braintribe.model.artifact.compiled.CompiledTerminal;
import com.braintribe.zarathud.model.data.Artifact;

public class PreconfiguredResolvingCoreContextBuilder implements Supplier<CoreContext> {
	private static Logger log = Logger.getLogger(PreconfiguredResolvingCoreContextBuilder.class);	
	private ResolvingRunnerContext context;
	private ClasspathDependencyResolver classpathResolver;
	
	
	public PreconfiguredResolvingCoreContextBuilder(ResolvingRunnerContext r, ClasspathDependencyResolver classpathResolver) {
		this.context = r;
		this.classpathResolver = classpathResolver;
	}

	@Override
	public CoreContext get() {
		
		ClasspathResolutionContext resolutionContext = ClasspathResolutionContext.build().enrichJar(true).done();
		
		// 
		CompiledTerminal cdi = CompiledTerminal.from ( CompiledDependencyIdentification.parse(context.getTerminal()));			
		AnalysisArtifactResolution resolution = classpathResolver.resolve( resolutionContext, cdi);
		
		// TODO: error handling here
		if (resolution.hasFailed()) {
			System.out.println("honk");
		}
		
	
		if (resolution.getSolutions().size() == 0) {
			log.info("no dependencies found for terminal:" + context.getTerminal());			
			
		}
		
		AnalysisTerminal analysisTerminal = resolution.getTerminals().get(0);	
		if (analysisTerminal instanceof AnalysisDependency) {
			AnalysisDependency ad = (AnalysisDependency) analysisTerminal;
			if (ad.getFailure() != null) {
				log.info("cannot run classpath resolution on artifact [" + context.getTerminal() + "] as " + ad.getFailure().stringify());
				return null;
			}
		}
		AnalysisArtifact analysisArtifact = resolution.getSolutions().stream().filter( s -> s.compareTo(analysisTerminal) == 0).findFirst().orElse(null);
		String packaging = analysisArtifact.getPackaging();
		
		if (packaging == null) {
			packaging = "jar";
		}
						
		if (!packaging.equalsIgnoreCase( "JAR") && !packaging.equalsIgnoreCase("BUNDLE")) {
			log.info("artifact [" + context.getTerminal() + "] doesn't contain a jar (by its declaration : [" + packaging + "]) and will not be processed");
			return null;
		}
		
						
		CoreContext coreContext = new CoreContext();
		coreContext.setClasspath( resolution.getSolutions());
		List<CompiledArtifact> cas = resolution.getSolutions().stream().map( aa -> aa.getOrigin()).collect( Collectors.toList());
		coreContext.setCompiledSolutionsOfClasspath(  cas);
		coreContext.setDependencies( analysisArtifact.getDependencies());
		coreContext.setCustomRatingsResource( context.getCustomRatingsResource());
		coreContext.setPullRequestRatingsResource(context.getPullRequestRatingsResource());
		
		Artifact artifact = toArtifact( analysisArtifact);
		coreContext.setTerminalArtifact( artifact);
		
		coreContext.setRespectBraintribeSpecifica(context.getRespectBraintribeSpecifica());
		ConsoleOutputVerbosity consoleOutputVerbosity = context.getConsoleOutputVerbosity();
		if (consoleOutputVerbosity != null) {
			coreContext.setConsoleOutputVerbosity(com.braintribe.devrock.zed.api.context.ConsoleOutputVerbosity.valueOf( consoleOutputVerbosity.toString()));
		}		
		
		return coreContext;
	}
	
		
	/**
	 * @param solution
	 * @return
	 */
	public static Artifact toArtifact( AnalysisArtifact solution) {
		Artifact artifact = Artifact.T.create();
		artifact.setGroupId( solution.getGroupId());
		artifact.setArtifactId( solution.getArtifactId());
		artifact.setVersion( solution.getVersion());		
		return artifact;
	}
	
	

}