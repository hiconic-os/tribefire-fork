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
package com.braintribe.gwt.gme.propertypanel.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface LocalizedText extends Messages {
	
	public static LocalizedText INSTANCE = ((LocalizedTextFactory) GWT.create(LocalizedTextFactory.class)).getLocalizedText();
	
	String add();
	String addDescription();
	String addToCollection();
	String applyDescription();
	String assign();
	String back();
	String backDescription();
	String base();
	String changeDescription();
	String changeNewExistingDescription();
	String clearCollection();
	String clearCollectionDescription();
	String clearText();
	String clearTextDescription();
	String close();
	String closeDescription();
	String dropFile();
	String empty();
	String errorChangingPropertyValue();
	String errorLoadingAbsentProperties();
	String errorRollingEditionBack();
	String errorRunningOnEditRequest();
	String execute();
	String executeDescription();
	String frontDescription();
	String instantiate();
	String instantiateDescription();
	String loadingAbsentProperty();
	String noOptionsAvailable();
	String notSet();
	String of();
	String ok();
	String open();
	String openDescription();
	String query();
	String queryDescription();
	String quickAccess();
	String readOnly();
	String select(String typeName);
	String selectDescription();
	String setNull();
	String setPropertyToNull();
	String subType();
	String typeToShowValues();
	String typeValue();
	String uploading();
	String workWith();
	String workWithDescription();
	

}
