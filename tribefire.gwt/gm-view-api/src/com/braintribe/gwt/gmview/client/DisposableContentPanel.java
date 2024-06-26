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
package com.braintribe.gwt.gmview.client;

import com.braintribe.gwt.ioc.client.DisposableBean;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * {@link ContentPanel} implementation which is also a {@link DisposableBean}.
 * If the child widget from {@link #getWidget()} is also a {@link DisposableBean}, then it will get disposed.
 * 
 * @author michel.docouto
 *
 */
public class DisposableContentPanel extends ContentPanel implements DisposableBean {

	@Override
	public void disposeBean() throws Exception {
		if (getWidget() instanceof DisposableBean)
			((DisposableBean) getWidget()).disposeBean();
	}

}
