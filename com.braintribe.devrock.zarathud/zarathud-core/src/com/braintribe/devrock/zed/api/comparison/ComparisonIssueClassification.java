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
package com.braintribe.devrock.zed.api.comparison;

import com.braintribe.model.version.Version;
import com.braintribe.zarathud.model.forensics.ForensicsRating;
import com.braintribe.zarathud.model.forensics.findings.ComparisonIssueType;

/**
 * helper that deals with the different {@link ComparisonIssueType} and {@link SemanticVersioningLevel} 
 * @author pit
 */
public class ComparisonIssueClassification {

	/**
	 * whether a {@link ComparisonIssueType} is a 'collection' type 
	 * @param type
	 * @return
	 */
	public static boolean isCollectionComparisonIssue(String type) {
		for (ComparisonIssueType cit : ComparisonIssueType.values()) {
			if (cit.name().equalsIgnoreCase(type)) {
				switch (cit) {
					case missingAnnotations:
					case missingAnnotationContainers:
					case surplusAnnotationContainers:
						
					case additionalContent:
						
					case missingEnumValues:					
					
					case missingFields:
					
					case missingImplementedInterfaces:
					case missingImplementingClasses:
					case missingInCollection:
					case missingMethodArguments:
					case missingMethodExceptions:
					case missingMethods:
					case missingSubInterfaces:
					case missingSubTypes:
					case missingSuperInterfaces:
					case surplusAnnotations:
					case surplusEnumValues:					
					case surplusFields:
					case surplusImplementedInterfaces:
					case surplusImplementingClasses:
					case surplusInCollection:
					case surplusMethodArguments:
					case surplusMethodExceptions:
					case surplusMethods:
					case surplusSubInterfaces:
					case surplusSubTypes:
					case surplusSuperInterfaces:
					
						return true;
					default:
						return false;				
				}
			}
		}
		return false;
	}
	
	/**
	 * whether it's a 'surplus' collection (i.e. the other side has more of it)
	 * @param type
	 * @return
	 */
	public static boolean isSurplus(String type) {
		for (ComparisonIssueType cit : ComparisonIssueType.values()) {
			if (cit.name().equalsIgnoreCase(type)) {
				switch (cit) {				
					case surplusAnnotations:
					case surplusEnumValues:					
					case surplusFields:
					case surplusImplementedInterfaces:
					case surplusImplementingClasses:
					case surplusInCollection:
					case surplusMethodArguments:
					case surplusMethodExceptions:
					case surplusMethods:
					case surplusSubInterfaces:
					case surplusSubTypes:
					case surplusSuperInterfaces:
					case surplusAnnotationContainers:
					case additionalContent:
						return true;
					default:
						return false;				
					}
			}
		}
		return false;
	}
	
	/**
	 * rates a passed {@link ComparisonIssueType} according the {@link SemanticVersioningLevel}
	 * @param cit - the {@link ComparisonIssueType}
	 * @param level - the {@link SemanticVersioningLevel}
	 * @return - the {@link ForensicsRating} that follows 
	 */
	public static ForensicsRating rateComparisonIssue( ComparisonIssueType cit, SemanticVersioningLevel level) {
		if (level == SemanticVersioningLevel.none) {
			return ForensicsRating.IGNORE;
		}
		else if (level == SemanticVersioningLevel.major) {
			return ForensicsRating.INFO;
		}		
		if (isCollectionComparisonIssue( cit.name())) {
			// missings are an issue, surplus isn't 
			if (!isSurplus( cit.name())) {
				switch (level) {
					case minor :		
						return ForensicsRating.WARN;
					case revision :
						return ForensicsRating.ERROR;
					default:
						break;
				}
			}
			else {
				switch (level) {			
					case revision :
						return ForensicsRating.WARN;
					default:
						break;
				}
			}
		}
		else {
			// missing class/interface/enum -> always an error in minor/revision?
			if (cit == ComparisonIssueType.noContentToCompare) {
				return ForensicsRating.ERROR;
			}
			// additional content -> error in revision, no impact on others
			else if (cit == ComparisonIssueType.additionalContent) {
				if (level == SemanticVersioningLevel.revision) {
					return ForensicsRating.ERROR;
				}
				return ForensicsRating.IGNORE;				
			}
			
			// everything else is an error?
			return ForensicsRating.ERROR;
		}
		return ForensicsRating.IGNORE;
	}
		
	/**
	 * derives a {@link SemanticVersioningLevel} from two {@link Version}s
	 * @param baseVersion - the {@link Version} of the base of the comparison
	 * @param otherVersion - the {@link Version} of the other part in the comparison
	 * @return - the {@link SemanticVersioningLevel} derived from it
	 */
	public static SemanticVersioningLevel determineLevelFromVersions( Version baseVersion, Version otherVersion) {
		int baseLevel = baseVersion.continuousPrecision();
		int otherLevel = otherVersion.continuousPrecision();
		
		if (baseLevel != otherLevel) {
			return SemanticVersioningLevel.none;
		}
		switch (baseLevel) {
			case 0:
				return SemanticVersioningLevel.major;
			case 1:
				return SemanticVersioningLevel.minor;
			case 2:
				return SemanticVersioningLevel.revision;
		}
		return SemanticVersioningLevel.none;
	}
}
