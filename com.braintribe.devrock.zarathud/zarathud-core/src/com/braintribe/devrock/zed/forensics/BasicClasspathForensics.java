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
package com.braintribe.devrock.zed.forensics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.zed.api.context.ZedForensicsContext;
import com.braintribe.devrock.zed.api.forensics.ClasspathForensics;
import com.braintribe.devrock.zed.commons.Comparators;
import com.braintribe.devrock.zed.forensics.fingerprint.FingerPrintExpert;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.data.TypeReferenceEntity;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.forensics.ClasspathForensicsResult;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.data.ClasspathDuplicate;
import com.braintribe.zarathud.model.forensics.findings.ClasspathForensicIssueType;

/**
 * 
 *  forensic expert for classpath 
 * 
 * <ul>forensics for the classpath 
 * <li>shadowing -> MAJOR_ISSUES</li>
 * </ul>
 * @author pit
 *
 */
public class BasicClasspathForensics extends ZedForensicsCommons implements ClasspathForensics {
	
	public BasicClasspathForensics(ZedForensicsContext context) {
		super(context);
	}

	@Override
	public ClasspathForensicsResult runForensics() {
		Set<ZedEntity> population = context.terminalArtifact().getEntries();
		ClasspathForensicsResult forensicsResult = extractForensicsOnPopulation(population);
		forensicsResult.setArtifact( shallowArtifactCopy(context.terminalArtifact()));
		return forensicsResult;
	}

	public ClasspathForensicsResult extractForensicsOnPopulation( Collection<ZedEntity> population) {
		Artifact runtimeArtifact = context.artifacts().runtimeArtifact(context);
		ClasspathForensicsResult result = ClasspathForensicsResult.T.create();
		
		List<Pair<ZedEntity, List<TypeReferenceEntity>>> pairs = new ArrayList<>(population.size());
		for (ZedEntity z : population) {
			if (!z.getDefinedInTerminal()) {
				continue;
			}
			List<TypeReferenceEntity> typeReferencesOfEntity = getTypeReferencesOfEntity(z);			
			pairs.add( Pair.of(z, typeReferencesOfEntity));
		}
						
	
		population.stream().forEach( z -> {
			if (z.getArtifacts().size() > 1) {
				ClasspathDuplicate duplicate = ClasspathDuplicate.T.create();				
				
				for (Pair<ZedEntity, List<TypeReferenceEntity>> pair : pairs)  {								
					TypeReferenceEntity trf = pair.second.stream().filter( r -> r.getReferencedType().equals(z)).findFirst().orElse( null);
					if (trf != null) {
						duplicate.getReferencersInTerminal().add( pair.first);						
					}
				}									
				duplicate.setType(z);
				duplicate.setDuplicates( z.getArtifacts());
				if (Comparators.contains( z.getArtifacts(), runtimeArtifact)) {
					duplicate.setShadowingRuntime( true);
				}
				result.getDuplicates().add(duplicate);
				// generate finger print, add 
				FingerPrint fp = FingerPrintExpert.build(context.terminalArtifact(), ClasspathForensicIssueType.ShadowingClassesInClasspath.toString(), Collections.singletonList( duplicate.toStringRepresentation()));
				result.getFingerPrintsOfIssues().add( fp);
			}
		});
		
		return result;
	}

	@Override
	public ClasspathForensicsResult extractForensicsOnPopulation(Artifact artifact) {
		Set<ZedEntity> population = context.terminalArtifact().getEntries();
		List<ZedEntity> subset = population.stream().filter( z -> Comparators.contains( z.getArtifacts(), artifact)).collect(Collectors.toList());
		ClasspathForensicsResult classpathForensicsResult = extractForensicsOnPopulation(subset);
		return classpathForensicsResult;
	}
	
	
}
