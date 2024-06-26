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
package com.braintribe.gwt.gme.constellation.client.gima.field;

import java.util.List;

import com.braintribe.gwt.gme.constellation.client.GIMADialog;
import com.braintribe.gwt.gme.constellation.client.gima.GIMAView;
import com.braintribe.gwt.gmview.client.EntityFieldDialog;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmSelectionListener;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public abstract class GIMAEntityFieldView<T extends EntityFieldDialog<?>> extends SimpleContainer implements GIMAView {
	
	protected GIMADialog gimaDialog;
	
	public GIMAEntityFieldView(T entityFieldDialog, GIMADialog gimaDialog) {
		this.gimaDialog = gimaDialog;
		add(entityFieldDialog.getView());
	}
	
	@Override
	public boolean isApplyAllHandler() {
		return false;
	}
	
	public void configureGimaDialog(GIMADialog gimaDialog) {
		this.gimaDialog = gimaDialog;
	}
	
	/******************* GmContentView related methods ************************/
	
	@Override
	public GmContentView getView() {
		return this;
	}
	
	@Override
	public PersistenceGmSession getGmSession() {
		return gimaDialog.getSessionForTransactionAndCMD();
	}
	
	@Override
	public String getUseCase() {
		return gimaDialog.getUseCase();
	}
	
	@Override
	public ModelPath getContentPath() {
		return null;
	}

	@Override
	public void setContent(ModelPath modelPath) {
		//NOP
	}

	@Override
	public void addSelectionListener(GmSelectionListener sl) {
		//NOP
	}

	@Override
	public void removeSelectionListener(GmSelectionListener sl) {
		//NOP
	}

	@Override
	public ModelPath getFirstSelectedItem() {
		return null;
	}

	@Override
	public List<ModelPath> getCurrentSelection() {
		return null;
	}

	@Override
	public boolean isSelected(Object element) {
		return false;
	}

	@Override
	public void select(int index, boolean keepExisting) {
		//NOP
	}

	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		//NOP
	}

	@Override
	public void configureUseCase(String useCase) {
		//NOP
	}

}
