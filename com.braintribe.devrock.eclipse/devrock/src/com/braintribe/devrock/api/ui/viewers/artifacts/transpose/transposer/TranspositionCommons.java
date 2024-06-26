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
package com.braintribe.devrock.api.ui.viewers.artifacts.transpose.transposer;

import java.util.Comparator;

import com.braintribe.devrock.eclipse.model.resolution.nodes.AnalysisNode;
import com.braintribe.devrock.eclipse.model.resolution.nodes.DependerNode;
import com.braintribe.devrock.eclipse.model.resolution.nodes.Node;
import com.braintribe.model.artifact.essential.VersionedArtifactIdentification;

/**
 * divers comparators for the transposer
 * @author pit
 *
 */
public class TranspositionCommons {

	public static DependerNodeComparator dependencyNodeComparator = new DependerNodeComparator();
	public static AnalysisNodeComparator analysisNodeComparator = new AnalysisNodeComparator();
	public static NodeComparator nodeComparator = new NodeComparator();
	
	/**
	 * compares {@link DependerNode} based on the {@link VersionedArtifactIdentification} comparator
	 * @author pit
	 *
	 */
	public static class DependerNodeComparator implements Comparator<DependerNode> {		
		@Override
		public int compare(DependerNode o1, DependerNode o2) {				
			return o1.getDependerArtifact().compareTo( o2.getDependerArtifact());
		}					
	}
	
	/**
	 * compares {@link AnalysisNode} based on either the {@link VersionedArtifactIdentification}s of the dependency ID or the artifact ID
	 * @author pit
	 *
	 */
	public static class AnalysisNodeComparator implements Comparator<AnalysisNode> {		
		@Override
		public int compare(AnalysisNode o1, AnalysisNode o2) {
			if (o1.getDependencyIdentification() != null) {
				o1.getDependencyIdentification().compareTo( o2.getDependencyIdentification());
			}
			else {				
				return o1.getSolutionIdentification().compareTo( o2.getSolutionIdentification());
			}
			return 0;
		}					
	}

	/**
	 * common comparator for {@link Node}, decides which comparison to make depending on first instance,
	 * either {@link AnalysisNodeComparator} or {@link DependerNodeComparator}
	 * @author pit
	 *
	 */
	public static class NodeComparator implements Comparator<Node> {		
		@Override
		public int compare(Node o1, Node o2) {
			if (o1 instanceof AnalysisNode) {
				return analysisNodeComparator.compare( (AnalysisNode) o1, (AnalysisNode) o2);
			}
			else if (o1 instanceof DependerNode) {
				return dependencyNodeComparator.compare( (DependerNode) o1, (DependerNode) o2);
			}						
			return 0;
		}
	}
		 	
}
