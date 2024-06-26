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
package com.braintribe.gwt.gmview.action.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface LocalizedText extends Messages {
	
	public static LocalizedText INSTANCE = ((LocalizedTextFactory) GWT.create(LocalizedTextFactory.class)).getLocalizedText();
	
	String actions();
	String add();
	String addingMapKey();
	String addingMapValue();
	String addMetaData();
	String addProperty(String propertyName, String entityInstance);
	String assign();
	String assignProperty(String propertyName, String entityInstance);
	String back();
	String backDescription();
	String cancel();
	String cannotUploadFolders();
	String clearCollection();
	String clearCollectionProperty();
	String close();
	String condensation();
	String confirmDeletionText(String entityDisplayInfo, String selectiveInfo);
	String confirmDeletionTitle();
	String confirmMultipleDeletionText();
	String copyId();
	String copyText();
	String createEntity(String entityName);
	String deleteEntity(String entityName);
	String details();
	String differentAmountOfKeysAndValues(int amountKeys, int amountValues);
	String downloadResource();
	String entityIdCopiedClipboard(String ids);
	String editEntity();
	String editTemplate();
	String errorAddingEntries();
	String errorChangingProperty();
	String errorInstantiatingEntity();
	String errorLoadingEntities();
	String errorRollingBack();
	String exchangeView();
	String execute();
	String filter();
	String filterDescription();
	String insertBefore();
	String less();
	String listView();
	String loadingActions();
	String loadingExternalComponent();
	String maximize();
	String more();
	String newEntity();
	String newTransientEntity();
	String newType(String type);
	String okDescription();
	String openWebReader();
	String record();
	String refresh();
	String refreshing();
	String refreshMetaData();
	String reInstantiate();
	String removeFromCollection();
	String removeFromCollectionProperty();
	String removeMetaData();
	String restore();
	String select();
	String selectType(String type);
	String selectTypeOrValue();
	String serviceRequests();
	String setNull();
	String showHideDetails();
	String switchTo();
	String toClipboard();
	String toMapKey();
	String toMapValue();
	String types();
	String values();
	String workWithEntity();

}
