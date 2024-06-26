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
package com.braintribe.devrock.zed.api.context;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.braintribe.devrock.zed.api.core.Verbosity;
import com.braintribe.devrock.zed.scan.ScannerResult;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.analysis.AnalysisDependency;

/**
 * the basic context for the zed-core 
 * @author pit
 *
 */
public interface ZedAnalyzerContext extends CommonZedCoreContext {
	/**
	 * @return - a {@link Collection} of all {@link AnalysisArtifact} that make up the classpath
	 */
	Collection<AnalysisArtifact> classpath();
	/**
	 * @return - a {@link List} of the class names to analyze
	 */
	List<String> classesToProcess();
	
	/**
	 * @return - 
	 */
	ScannerResult terminalScanData();
	/**
	 * @return - the declared dependencies of the terminal
	 */
	List<AnalysisDependency> declaredTerminalDependencies();
	
	
	/**
	 * @return - the {@link AnalysisArtifact}s provided via folders of classes (case of Eclipse projects)
	 */
	List<AnalysisArtifact> additionsToClasspath();
	
	
	/**
	 * @return - the currently scanned resource, may be null
	 */
	URL currentlyScannedResource();
	
	/**
	 * @param resource - the currently scanned resource
	 */
	void setCurrentlyScannedResource(URL resource);
	
	/**
	 * @return - the Java runtime jar used
	 */
	URL runtimeJar();
	
	/**
	 * @return - the current {@link Verbosity} level
	 */
	Verbosity verbosity();
	
}
