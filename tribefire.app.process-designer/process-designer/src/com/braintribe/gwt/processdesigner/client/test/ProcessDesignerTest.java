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
package com.braintribe.gwt.processdesigner.client.test;

import java.util.HashSet;
import java.util.Set;

import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

import tribefire.extension.process.model.deployment.Edge;
import tribefire.extension.process.model.deployment.ProcessDefinition;
import tribefire.extension.process.model.deployment.ProcessElement;
import tribefire.extension.process.model.deployment.StandardNode;

public class ProcessDesignerTest {
	
	private PersistenceGmSession session;
	
	public void setSession(PersistenceGmSession session) {
		this.session = session;
	}
	
	Set<ProcessElement> elements = new HashSet<ProcessElement>();
	public ProcessDefinition createProcessDefinition(String name){
		ProcessDefinition processDefinition = session.create(ProcessDefinition.T);
		processDefinition.setName(name);
		elements.clear();
		
		StandardNode initial = prepareNode("initial");
		StandardNode detect = prepareNode("detect");
		StandardNode detectError = prepareNode("detectError");
		StandardNode transform = prepareNode("transform");
		StandardNode transformError = prepareNode("transformError");
		StandardNode correlate = prepareNode("correlate");
		StandardNode correlateError = prepareNode("correlateError");
		StandardNode duplicateCheck = prepareNode("duplicateCheck");
		StandardNode duplicateCheckError = prepareNode("duplicateCheckError");
		StandardNode rules = prepareNode("rules");
		StandardNode rulesError = prepareNode("rulesError");
		StandardNode dispatch = prepareNode("dispatch");
		StandardNode dispatchError = prepareNode("dispatchError");
		StandardNode confirm = prepareNode("confirm");
		StandardNode confirmError = prepareNode("confirmError");
		StandardNode confirmWait = prepareNode("confirmWait");
		StandardNode done = prepareNode("done");
		
		prepareEdge(null, initial);
		
		prepareEdge(initial, detect);

		//detect-detectError edge
		prepareEdge(detect, detectError);
		prepareEdge(detectError, detect);
		prepareEdge(detectError, confirm);
		prepareEdge(detectError, done);
		
		//detect-transform edge
		prepareEdge(detect, transform);
		prepareEdge(transform, transformError);
		prepareEdge(transformError, transform);
		prepareEdge(transformError, confirm);
		prepareEdge(transformError, done	);
		
		//transform-correlate edge
		prepareEdge(transform, correlate);
		prepareEdge(correlate, correlateError);
		prepareEdge(correlateError, correlate);
		prepareEdge(correlateError, duplicateCheck);
		prepareEdge(correlateError, dispatch);
		prepareEdge(correlateError, confirm);
		prepareEdge(correlateError, done);
		
		//correlate-duplicateCheck edge
		prepareEdge(correlate, duplicateCheck);
		prepareEdge(duplicateCheck, duplicateCheckError);
		prepareEdge(duplicateCheckError, duplicateCheck);
		prepareEdge(duplicateCheckError, rules);
		prepareEdge(duplicateCheckError, dispatch);
		prepareEdge(duplicateCheckError	, confirm);
		prepareEdge(duplicateCheckError, done);
		
		//duplicateCheck-rules edge
		prepareEdge(duplicateCheck, rules);
		prepareEdge(rules, rulesError);
		prepareEdge(rulesError, rules);
		prepareEdge(rulesError, dispatch);
		prepareEdge(rulesError, confirm	);
		prepareEdge(rulesError, done);
		
		//rules-dispatch edge
		prepareEdge(rules, dispatch);
		prepareEdge(dispatch, dispatchError);
		prepareEdge(dispatchError, dispatch);
		prepareEdge(dispatchError, confirm);
		prepareEdge(dispatchError, done);
		
		//dispatch-confirm edge
		prepareEdge(dispatch, confirm);
		prepareEdge(confirm, confirmError);
		prepareEdge(confirmError, confirm);
		prepareEdge(confirmError, done);
	
		//confirm-confirmWait edge
		prepareEdge(confirm, confirmWait);
		prepareEdge(confirmWait, done);
		prepareEdge(confirmWait, confirmError);
		prepareEdge(confirmError, confirmWait);
		
		//dispatch-done edge
		prepareEdge(confirm, done);

		processDefinition.setElements(elements);
		return processDefinition;
	}
	
	private StandardNode prepareNode(String state){
		StandardNode node = session.create(StandardNode.T);
		node.setState(state);
		elements.add(node);
		return node;
	}
	
	private Edge prepareEdge(StandardNode fromNode, StandardNode toNode) {
		Edge edge = session.create(Edge.T);
		edge.setFrom(fromNode);
		edge.setTo(toNode);
		elements.add(edge);
		return edge;
	}

}
