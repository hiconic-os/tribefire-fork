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
package tribefire.extension.process.processing;

import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.sp.api.ProcessStateChangeContext;

import tribefire.extension.process.api.TransitionProcessorContext;
import tribefire.extension.process.model.ContinueWithState;
import tribefire.extension.process.model.data.Process;

public class TransitionProcessorContextImpl<T extends Process> implements TransitionProcessorContext<T>{
	
	private Object leftState;
	private Object enteredState;
	private EntityProperty processProperty;
	private ProcessStateChangeContext<T> processStateChangeContext;
	private ContinueWithState continueWithState;
	
	@Override
	public EntityProperty getProcessProperty() {
		return processProperty;
	}
	
	@Override
	public T getProcess() {
		return processStateChangeContext.getProcessEntity();
	}	

	@Override
	public PersistenceGmSession getSession() {
		return processStateChangeContext.getSession();
	}
	@Override
	public Object getLeftState() {
		return leftState;
	}
	
	@Override
	public Object getEnteredState() {
		return enteredState;
	}
	
	public TransitionProcessorContextImpl(EntityProperty processProperty, Object leftState, Object enteredState) {		
		this.processProperty = processProperty;
		this.leftState = leftState;
		this.enteredState = enteredState;
	}
	
	public TransitionProcessorContextImpl(ProcessStateChangeContext<T> context, Object leftState, Object enteredState) {		
		this.processProperty = context.getEntityProperty();
		this.processStateChangeContext = context;
		this.leftState = leftState;
		this.enteredState = enteredState;
	}


	@Override
	public T getProcessFromSystemSession() {
		return processStateChangeContext.getSystemProcessEntity();
	}


	@Override
	public PersistenceGmSession getSystemSession() {
		return processStateChangeContext.getSystemSession();
	}


	@Override
	public void notifyInducedManipulation(Manipulation manipulation) {
		processStateChangeContext.notifyInducedManipulation(manipulation);
	}

	@Override
	public void continueWithState(Object value) {
		this.continueWithState = ContinueWithState.T.create();
		continueWithState.setState(value);
	}
	
	public ContinueWithState getContinueWithState() {
		return continueWithState;
	}
}
