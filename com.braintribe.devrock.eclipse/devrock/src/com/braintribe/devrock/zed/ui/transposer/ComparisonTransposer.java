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
package com.braintribe.devrock.zed.ui.transposer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.devrock.zarathud.model.common.FingerPrintNode;
import com.braintribe.devrock.zarathud.model.common.FingerPrintRepresentation;
import com.braintribe.devrock.zarathud.model.common.Node;
import com.braintribe.devrock.zarathud.model.extraction.subs.ContainerNode;
import com.braintribe.devrock.zed.api.comparison.ComparisonIssueClassification;
import com.braintribe.devrock.zed.api.comparison.SemanticVersioningLevel;
import com.braintribe.devrock.zed.forensics.fingerprint.HasFingerPrintTokens;
import com.braintribe.devrock.zed.ui.comparison.ZedComparisonViewerContext;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;

/**
 * a specific transposer for the comparison fingerprints
 * 
 * @author pit
 */
public class ComparisonTransposer extends ExtractionTransposer implements HasFingerPrintTokens {
			
	/**
	 * comparator instance 
	 */
	private	Comparator<FingerPrintNode> fpnComparator = new Comparator<FingerPrintNode>() {
		@Override
		public int compare(FingerPrintNode o1, FingerPrintNode o2) {				
			return o1.getFingerPrint().getSlots().get(ISSUE).compareToIgnoreCase( o2.getFingerPrint().getSlots().get(ISSUE));
		}				
	};
	
	/**
	 * transposes the fingerprints as top-level nodes 
	 * @param extractionContext
	 * @param context
	 * @return
	 */
	public Node transposeFingerPrintsAsTop(ZedExtractionTransposingContext extractionContext, ZedComparisonViewerContext context) {
		clear();
		List<FingerPrint> fingerPrints = context.getFingerPrints();
		SemanticVersioningLevel level = context.getSemanticComparisonLevel();
		
		Map<String, List<FingerPrintNode>> nodes = new HashMap<>();		
		for (FingerPrint fp : fingerPrints) {
			String issue = fp.getSlots().get( HasFingerPrintTokens.ISSUE);						
			List<FingerPrintNode> fpns = nodes.computeIfAbsent(issue, k -> new ArrayList<>());
											
			FingerPrintNode cnn = FingerPrintNode.T.create();
			cnn.setFingerPrint(fp);					 
			ForensicsRating rating = ComparisonIssueClassification.rateComparisonIssue( fp.getComparisonIssueType(), level);
			cnn.setRating( CommonContentTransposer.transpose(rating));
			
			ZedEntity owner = TopologyExpert.findBaseTopOwner(fp);
			cnn.setBaseTopEntity(owner);					
			
			ZedEntity target = TopologyExpert.findOtherTopOwner(fp);
			cnn.setComparisonTopEntity(target);			
			
			cnn.setRepresentation( FingerPrintRepresentation.owner);
			
			fpns.add(cnn);				
			
		}
		ContainerNode cn = ContainerNode.T.create();
		cn.setName( "fingerprints");
		
		List<String> sortedKeys = new ArrayList<>(nodes.keySet());		
		sortedKeys.sort( new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {			
				return o1.compareTo(o2);
			}			
		});
		
		for (String key : sortedKeys) {
			ContainerNode cnn = ContainerNode.T.create();
			cnn.setName( key);
			cnn.setRepresentation(FingerPrintRepresentation.container);
			// get rating from a node
			List<FingerPrintNode> values = nodes.get(key);
			values.sort( fpnComparator);
			ForensicsRating rating = ComparisonIssueClassification.rateComparisonIssue( values.get(0).getFingerPrint().getComparisonIssueType(), level);
			cnn.setRating( CommonContentTransposer.transpose(rating));
			
			cnn.getChildren().addAll( values);			
			cn.getChildren().add(cnn);
		}		
		return cn;
	}
	

	/**
	 * transposes fingerprints with their 'owners' as toplevel nodes
	 * @param extractionContext
	 * @param context
	 * @return
	 */
	public Node transposeOwnersAsTop(ZedExtractionTransposingContext extractionContext, ZedComparisonViewerContext context) {
		clear();
		List<FingerPrint> fingerPrints = context.getFingerPrints();
		SemanticVersioningLevel level = context.getSemanticComparisonLevel();
		
		Map<String, List<FingerPrintNode>> nodes = new HashMap<>();
		Map<String, ZedEntity> nameToOwner = new HashMap<>();
		List<FingerPrintNode> associatedToArtifact = new ArrayList<>();
		
		for (FingerPrint fp : fingerPrints) {
			
			if (fp.getEntitySource() == null || fp.getEntitySource() instanceof Artifact) {
				FingerPrintNode cnn = FingerPrintNode.T.create();
				cnn.setFingerPrint(fp);		
				cnn.setRepresentation(FingerPrintRepresentation.container);
				ForensicsRating rating = ComparisonIssueClassification.rateComparisonIssue( fp.getComparisonIssueType(), level);
				cnn.setRating( CommonContentTransposer.transpose(rating));
				associatedToArtifact.add(cnn);
				continue;
			}
			ZedEntity owner = TopologyExpert.findBaseTopOwner(fp);
			String ownerName;
			
			ownerName = owner.getName();
					
			nameToOwner.put(ownerName, owner);
			List<FingerPrintNode> fpns = nodes.computeIfAbsent(ownerName, k -> new ArrayList<>());
											
			FingerPrintNode cnn = FingerPrintNode.T.create();
			cnn.setFingerPrint(fp);		
			cnn.setRepresentation(FingerPrintRepresentation.container);
			ForensicsRating rating = ComparisonIssueClassification.rateComparisonIssue( fp.getComparisonIssueType(), level);
			cnn.setRating( CommonContentTransposer.transpose(rating));
				
			cnn.setBaseTopEntity(owner);
				
			ZedEntity target = TopologyExpert.findOtherTopOwner(fp);
			cnn.setComparisonTopEntity(target);
				
			fpns.add(cnn);				
			
		}
		
		List<String> sortedKeys = new ArrayList<>(nodes.keySet());		
		sortedKeys.sort( new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {			
				return o1.compareTo(o2);
			}			
		});
		ContainerNode cn = ContainerNode.T.create();
		cn.setName( "fingerprints");
		for (String key : sortedKeys) {
			ContainerNode cnn = ContainerNode.T.create();
			cnn.setName( key);
			List<FingerPrintNode> values = nodes.get(key);
			values.sort( fpnComparator);
			cnn.getChildren().addAll( values);
			cnn.setRepresentation(FingerPrintRepresentation.owner);
			cnn.setOwner( nameToOwner.get( key));
			// get rating from a node
			ForensicsRating rating = ComparisonIssueClassification.rateComparisonIssue( values.get(0).getFingerPrint().getComparisonIssueType(), level);
			cnn.setRating( CommonContentTransposer.transpose(rating));
			cn.getChildren().add(cnn);
		}	
		if (associatedToArtifact.size() > 0) {
			for (FingerPrintNode fpn : associatedToArtifact) {
				cn.getChildren().add(fpn);
			}
		}
		return cn;
	}			
	
}
