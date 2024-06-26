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
package com.braintribe.gwt.modeller.client.standalone;

import com.braintribe.gwt.gme.constellation.client.MasterDetailConstellation;
import com.braintribe.gwt.gmview.actionbar.client.GmViewActionBar;
import com.braintribe.gwt.gmview.actionbar.client.GmViewActionProvider;
import com.braintribe.gwt.ioc.client.InitializableBean;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

public class StandAloneModeler extends BorderLayoutContainer implements InitializableBean{
	
	private MasterDetailConstellation masterDetailConstellation;
	private GmViewActionBar actionBar;
	
	public void setMasterDetailConstellation(MasterDetailConstellation masterDetailConstellation) {
		this.masterDetailConstellation = masterDetailConstellation;
	}
	
	public void setActionBar(GmViewActionBar actionBar) {
		this.actionBar = actionBar;
		actionBar.getView().getElement().setId("gmViewActionBar");
	}
	
	@Override
	public void intializeBean() throws Exception {
		//actionBar.prepareActionsForView((GmViewActionProvider) masterDetailConstellation.getCurrentMasterView());
		setCenterWidget(masterDetailConstellation);
		setSouthWidget(actionBar.getView(), new BorderLayoutData(85));
	}
	
	public void init(GmSession session) {
		masterDetailConstellation.configureGmSession((PersistenceGmSession) session);
		actionBar.prepareActionsForView((GmViewActionProvider) masterDetailConstellation.getCurrentMasterView());
	}

	public void setContent(ModelPath mp) {
		masterDetailConstellation.setContent(mp);
	}
}
