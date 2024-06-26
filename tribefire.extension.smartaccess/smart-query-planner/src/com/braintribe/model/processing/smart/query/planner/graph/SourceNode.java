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
package com.braintribe.model.processing.smart.query.planner.graph;

import static com.braintribe.model.generic.GMF.getTypeReflection;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmCollectionType;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMapType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlannerException;
import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.processing.smart.query.planner.tools.SmartQueryPlannerTools;
import com.braintribe.model.query.Join;
import com.braintribe.model.query.JoinType;
import com.braintribe.model.query.Source;

/**
 * 
 */
public abstract class SourceNode {

	protected final Source source;
	protected final GmType smartGmType;
	protected final SmartQueryPlannerContext context;

	protected SourceNodeType nodeType;

	protected String smartJoinProperty;
	protected EntitySourceNode joinMaster = null;
	protected String delegateJoinProperty;
	/* We have this because our OutterJoinAdjuster in the SmartQueryPlannerContext runs only after SourceNodes are initialized. That is
	 * because we need the SourceNodes for the analysis of the conditions, and the OutterJoinAdjuster needs the analyzed conditions for it's
	 * run. */
	protected Join joinForJoinType;

	protected boolean retrieveEntireNode;

	private boolean retrieveJoinFunction;
	private int joinFunctionPosition = -1;

	protected GmCollectionType delegateCollectionGmType;

	private EntitySourceNode mapKeyNode; // only set iff this node represents a Map and we want to select the map key

	/**
	 * CollectionSelection_InverseKeyProperty_PlannerTests.simpleEntityQuery <- type is delegate type (for inverseKey
	 * collection)
	 */
	public SourceNode(Source source, GmType smartGmType, SmartQueryPlannerContext context) {
		this.source = source;
		this.smartGmType = smartGmType;
		this.context = context;
		this.joinForJoinType = (source instanceof Join) ? (Join) source : null;
	}

	public Source getSource() {
		return source;
	}

	public GmProperty getSmartJoinGmProperty() {
		return context.modelExpert().getGmProperty(joinMaster.getSmartGmType(), smartJoinProperty);
	}

	public GmType getSmartGmType() {
		return smartGmType;
	}

	/** Only called by {@link QueryPlanStructure} in the initialization phase, and by this class itself. */
	public void markJoinFunction() {
		if (retrieveJoinFunction) {
			return;
		}

		retrieveJoinFunction = true;

		EntityType<?> mapKeyType = getMapKeyTypeIfEntity();
		if (mapKeyType != null) {
			mapKeyNode = new EntitySourceNode(null, null, mapKeyType, null, joinMaster.getAccess(), joinMaster.getEmUseCase(), context,
					null);
			mapKeyNode.markNodeForSelection();
		}
	}

	private EntityType<?> getMapKeyTypeIfEntity() {
		GmType keyType = getSmartMapKeyTypeIfEligible();
		return keyType instanceof GmEntityType ? getTypeReflection().<EntityType<?>> getType(keyType.getTypeSignature()) : null;
	}

	private GmType getSmartMapKeyTypeIfEligible() {
		if (!(smartGmType instanceof GmMapType)) {
			return null;
		}

		return ((GmMapType) smartGmType).getKeyType();
	}

	public GmType getDelegateMapKeyGmTypeIfEligible() {
		return SmartQueryPlannerTools.keyTypeIfMap(delegateCollectionGmType);
	}

	public GmCollectionType getDelegateCollectionGmType() {
		return delegateCollectionGmType;
	}
	
	public boolean shouldRetrieveJoinFunction() {
		return retrieveJoinFunction;
	}

	public abstract void markNodeForSelection();

	public boolean isNodeMarkedForSelection() {
		return retrieveEntireNode;
	}

	public EntitySourceNode getMapKeyNode() {
		return mapKeyNode;
	}

	public final EntitySourceNode getJoinMaster() {
		return joinMaster;
	}

	public final String getExplicitJoinDelegateProperty() {
		return delegateJoinProperty;
	}

	public final JoinType getJoinType() {
		return joinForJoinType != null ? joinForJoinType.getJoinType() : null;
	}

	public int acquireJoinFunctionPosition() {
		return joinFunctionPosition >= 0 ? joinFunctionPosition : (joinFunctionPosition = context.allocateTuplePosition());
	}

	public int getJoinFunctionPosition() {
		if (joinFunctionPosition < 0) {
			throw new SmartQueryPlannerException("Cannot get position of join-function, as join function was not selected!");
		}

		return joinFunctionPosition;
	}

	// TODO this is only relevant in case of KPA - so I do not want this method here
	public boolean isCollection() {
		return smartGmType instanceof GmCollectionType;
	}

	public SourceNodeType getSourceNodeType() {
		return nodeType;
	}

}
