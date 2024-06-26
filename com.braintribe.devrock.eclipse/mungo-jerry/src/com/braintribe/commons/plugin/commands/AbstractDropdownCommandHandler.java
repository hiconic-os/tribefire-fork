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
package com.braintribe.commons.plugin.commands;

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IWorkingSet;

import com.braintribe.commons.plugin.selection.SelectionTuple;
import com.braintribe.commons.plugin.selection.TargetProvider;
import com.braintribe.commons.plugin.selection.TargetProviderImpl;

public abstract class AbstractDropdownCommandHandler extends AbstractHandler implements TargetProvider {
	
	protected String PARM_MSG = "com.braintribe.artifactcontainer.common.commands.command.param";
	private TargetProviderImpl targetProvider = new TargetProviderImpl();
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		refresh();
		String parameter = event.getParameter(PARM_MSG);
		process( parameter);
		return null;	    
	}
  
	public abstract void process( String parameter);  
	public void executeSingle(IProject project){}

	public void executeSingle( IProject project, IProgressMonitor monitor){}

	
	// target provider delegation 
	@Override
	public SelectionTuple getSelectionTuple() {	
		return targetProvider.getSelectionTuple();
	}
	@Override
	public IWorkingSet getTargetWorkingSet() {		
		return targetProvider.getTargetWorkingSet();
	}	
	@Override
	public IProject getTargetProject() {
		return targetProvider.getTargetProject();
	}
	@Override
	public Set<IProject> getTargetProjects() {
		return targetProvider.getTargetProjects();
	}
	@Override
	public void refresh() {
		targetProvider.refresh();	
	}
		
	
}
