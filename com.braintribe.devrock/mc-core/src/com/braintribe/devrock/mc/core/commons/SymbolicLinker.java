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
package com.braintribe.devrock.mc.core.commons;



import static com.braintribe.utils.lcd.CollectionTools2.newTreeSet;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.analysis.AnalysisDependency;
import com.braintribe.model.version.VersionExpression;
import com.braintribe.utils.FileTools;


public class SymbolicLinker {		
	public static void createSymbolicLink( File source, File target, boolean relativize) throws IOException {		
		Path sourcePath = source.getAbsoluteFile().toPath();
		Path targetPath = target.getAbsoluteFile().toPath();
				
		Path targetPathToUse = relativize ? sourcePath.getParent().relativize( targetPath) : targetPath;
		Files.createSymbolicLink(sourcePath, targetPathToUse);				
	}
	
	public static void ensureSymbolicLink( File source, File target, boolean relativize) {		
		Path sourcePath = source.getAbsoluteFile().toPath();
		Path targetPath = target.getAbsoluteFile().toPath();
		
		Path targetPathToUse = relativize ? sourcePath.getParent().relativize( targetPath) : targetPath;
		
		try {
			
			if (Files.exists(sourcePath, LinkOption.NOFOLLOW_LINKS)) {
				if (Files.isSymbolicLink(sourcePath) && Files.readSymbolicLink(sourcePath).equals(targetPathToUse)) 
					return;
					
				FileTools.deleteRecursivelySymbolLinkAware(source);
			}
			
			Files.createSymbolicLink(sourcePath, targetPathToUse);
		} catch (IOException e) {
			throw new UncheckedIOException("Could not ensure symbolic link from [" + sourcePath + "] to [" + targetPathToUse + "]. Check user rights!", e);
		}				
	}

	public static void createCopy(File source, File target) {
		FileTools.copyFileOrDirectory(source, target);		
	}
	
	/**
	 * determines all link names that should be create for the passed solution.
	 * if mulitple requesters are found, all differing names are returned -> as they all need to get a link
	 */
	public static Set<String> determineLinkName(AnalysisArtifact artifact) {
		Set<AnalysisDependency> dependers = artifact.getDependers();
		if (dependers.isEmpty()) {
			return Collections.singleton(artifact.getGroupId() + "." + artifact.getArtifactId() + "-" + artifact.getVersion()); 
		}
		else {
			Set<String> names = newTreeSet();
			for (AnalysisDependency depender : dependers) {						
				String name = determineLinkName(depender);
				names.add(name);
			}
			return names;
		}		
	}

	/**
	 * generate a link name as defined by the dependency passed
	 * @return - the name
	 */
	private static String determineLinkName(AnalysisDependency dependency) {
		VersionExpression versionExpression = dependency.getOrigin().getVersion();
		String name = dependency.getGroupId() + "." + dependency.getArtifactId() + "-" + versionExpression.asShortNotation();
		return name;
	}
}
