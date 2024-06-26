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
package com.braintribe.gwt.gme.headerbar.client.action;

import java.util.function.Function;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.GmSessionHandler;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.user.client.ui.Widget;

public interface HeaderBar extends GmSessionHandler, Function<Folder, Future<Void>> {
	
	public Widget getHeaderBar();
	public void setWorkbenchSession(PersistenceGmSession workbenchSession);
	
}