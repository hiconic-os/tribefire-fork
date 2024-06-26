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
package com.braintribe.gwt.metadataeditor.client.view;

import java.util.Set;

import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.meta.cmd.CmdResolverImpl;
import com.braintribe.model.processing.meta.cmd.ResolutionContextBuilder;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;
import com.braintribe.model.processing.meta.cmd.context.aspects.RoleAspect;
import com.braintribe.model.processing.meta.oracle.BasicModelOracle;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.processing.async.api.AsyncCallback;

public class MetaDataResolverProvider {
	
	private ModelMdResolver modelMetaDataContextBuilder = null;
	private ModelMdResolver sessionModelMetaDataContextBuilder = null;
	private CmdResolver metaDataResolver = null;
    private GmMetaModel lastGmMetaModel = null;
    private GmMetaModel prepareGmMetaModel = null;
    private Set<String> listUseCase = null;
    private Set<String> listRoles = null;
    private Set<String> listAccess = null;
	private PersistenceGmSession gmSession;
	private Boolean useSessionResolver = false;
	private Boolean useFilter = true;
	private Boolean isReady = false;
	
	public void configureGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}

	public PersistenceGmSession getGmSession() {
		return this.gmSession;
	}
	
	public void setUseSessionResolver(Boolean useSessionResolver) {
		this.useSessionResolver = useSessionResolver;
	}
	public Boolean getUseSessionResolver() {
		return this.useSessionResolver;
	}
	
	public void rebuildResolver() {
		isReady = false;
		if (prepareGmMetaModel != null) {
			this.metaDataResolver = null;
			setModelMetaDataContextBuilder(prepareGmMetaModel, listUseCase, listRoles, listAccess, useFilter);
		}
	}

	public void setGmMetaModel (GmMetaModel gmMetaModel) {
		this.isReady = false;
		this.prepareGmMetaModel = gmMetaModel;
	}
	
	//Return last rebuilded GmMetaModel
	public GmMetaModel getGmMetaModel() {
		return this.lastGmMetaModel;
	}
	
	public void setFilter(Set<String> listUseCase, Set<String> listRoles, Set<String> listAccess) {
		this.isReady = false;
		this.listUseCase = listUseCase;
		this.listRoles = listRoles;
		this.listAccess = listAccess;
	}
	
	public Set<String> getFilterUseCase()	{
		return this.listUseCase;
	}
	public Set<String> getFilterRole()	{
		return this.listRoles;
	}
	public Set<String> getFilterAccess()	{
		return this.listAccess;
	}
	
	public void setUseFilter(Boolean useFilter) {
		this.isReady = false;
		this.useFilter  = useFilter;
	}
	
	public Boolean getUseFilter() {
		return this.useFilter;
	}
	
	public Boolean isBuildReady() {
		return this.isReady;
	}
	
	public ModelMdResolver getModelMetaDataContextBuilder() {
		if (!this.isReady) {
			setModelMetaDataContextBuilder(this.prepareGmMetaModel, this.listUseCase, this.listRoles, this.listAccess, this.useFilter);
		}
		
		if ((this.useSessionResolver) || (this.modelMetaDataContextBuilder == null)) {
			return this.sessionModelMetaDataContextBuilder;				
		} else {							
			return this.modelMetaDataContextBuilder;
		}
	}
	
	public void provide(Callback callback) {
		try {
			setModelMetaDataContextBuilder(this.prepareGmMetaModel, this.listUseCase, this.listRoles, this.listAccess, this.useFilter);
					
			if (this.useSessionResolver) {
				callback.onSuccess(this.sessionModelMetaDataContextBuilder);				
			} else {							
				callback.onSuccess(this.modelMetaDataContextBuilder);
			}
		} catch (Exception e) {
			callback.onFailure(e);
		}
	}

	//if gmMetaModel is NULL, than would be used Session resolver!!!
	private void setModelMetaDataContextBuilder(GmMetaModel gmMetaModel, Set<String> listUseCase, Set<String> listRoles, Set<String> listAccess, Boolean useFilter) {
		this.isReady = false;
		if ((gmMetaModel != null) && ((this.metaDataResolver == null) || (!gmMetaModel.equals(this.lastGmMetaModel)))) {
			this.metaDataResolver = buildCmdResolverFor(gmMetaModel);
			this.lastGmMetaModel = gmMetaModel;
		}
		
		if (gmMetaModel != null) this.modelMetaDataContextBuilder = this.metaDataResolver.getMetaData();
		this.sessionModelMetaDataContextBuilder = this.gmSession.getModelAccessory().getMetaData();
		
		if (useFilter) {			
		    String accessId = null;
		    if (listAccess != null &&!listAccess.isEmpty()) {
		    	for(String setString: listAccess) {
			    	accessId = setString;
		    	    break;
		    	}
		    }		
			
			if (listUseCase != null && !listUseCase.isEmpty()) {
				if (gmMetaModel != null) this.modelMetaDataContextBuilder = this.modelMetaDataContextBuilder.useCases(listUseCase);
				this.sessionModelMetaDataContextBuilder = this.sessionModelMetaDataContextBuilder.useCases(listUseCase);
			}
			if (accessId != null) {
				if (gmMetaModel != null) this.modelMetaDataContextBuilder = this.modelMetaDataContextBuilder.access(accessId);
				this.sessionModelMetaDataContextBuilder = this.sessionModelMetaDataContextBuilder.access(accessId);
			}
			if (listRoles != null && !listRoles.isEmpty()) {
				if (gmMetaModel != null) this.modelMetaDataContextBuilder = this.modelMetaDataContextBuilder.with(RoleAspect.class, listRoles);
				this.sessionModelMetaDataContextBuilder = this.sessionModelMetaDataContextBuilder.with(RoleAspect.class, listRoles);
			}
		}
		this.isReady = true;
	}
	
	private static CmdResolverImpl buildCmdResolverFor(GmMetaModel model) {
		BasicModelOracle modelOracle = new BasicModelOracle(model);
		return new CmdResolverImpl(new ResolutionContextBuilder(modelOracle).build());
	}
	

	public static abstract class Callback implements AsyncCallback<ModelMdResolver> {
		@Override
		public void onFailure(Throwable t) {
			ErrorDialog.show("MetaDataEditorModelContextBuilder-Failure", t);
		}
	}
	
}

