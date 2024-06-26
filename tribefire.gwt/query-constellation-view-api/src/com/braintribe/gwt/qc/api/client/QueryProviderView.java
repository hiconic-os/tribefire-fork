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
package com.braintribe.gwt.qc.api.client;

import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.gm.storage.api.StorageColumnInfo;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.QueryResult;
import com.google.gwt.user.client.ui.Widget;

public interface QueryProviderView<T extends GenericEntity> {

	Widget getWidget();

	QueryProviderContext getQueryProviderContext();

	void notifyQueryPerformed(QueryResult queryResult, QueryProviderContext queryProviderContext);

	void setEntityContent(T entityContent);

	void addQueryProviderViewListener(QueryProviderViewListener listener);

	void removeQueryProviderViewListener(QueryProviderViewListener listener);

	void configureGmSession(PersistenceGmSession gmSession);

	void focusEditor();

	void setOtherModeQueryProviderView(Supplier<? extends QueryProviderView<T>> otherModelQueryProviderView);

	void modeQueryProviderViewChanged();
	
	/**
	 * Shows the form with variables.
	 */
	default void showForm() {
		//NOP
	}
	
	/**
	 * Hides the form with variables.
	 */
	default void hideForm() {
		//NOP
	}
	
	/**
	 * Returns whether the form is available for the view.
	 */
	default boolean isFormAvailable() {
		return false;
	}
	
	/**
	 * Notification for view changes.
	 * @param displayNode - true for displaying the node.
	 * @param nodeWidth - width of the node.
	 * @param columnsVisible - List of columns that are visible.
	 */
	default void onViewChange(boolean displayNode, Integer nodeWidth, List<StorageColumnInfo> columnsVisible) {
		//NOP
	}
	
	/**
	 * Sets the current query context name.
	 * @param global - true when using a global context. Should be false when a context is given.
	 * @param contextName - name of the context. Should be null when set as global.
	 */
	default void setCurrentContext(boolean global, String contextName) {
		//NOP
	}
	
	/**
	 * Adds a new query context.
	 * @param contextName - name of the context
	 */
	default void addQueryContext(String contextName) {
		//NOP
	}
	
	/**
	 * Removes the query context.
	 */
	default void removeQueryContext() {
		//NOP
	}
	
	default String getCurrentQueryText() {
		return null;
	}
	
	/**
	 * Displays the given count text.
	 * @param countText - the text to be displayed
	 */
	default void setDisplayCountText(String countText) {
		//NOOP
	}
}
