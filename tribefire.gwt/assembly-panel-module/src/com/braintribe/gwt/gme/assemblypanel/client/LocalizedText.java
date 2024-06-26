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
package com.braintribe.gwt.gme.assemblypanel.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface LocalizedText extends Messages {
	
	public static LocalizedText INSTANCE = ((LocalizedTextFactory) GWT.create(LocalizedTextFactory.class)).getLocalizedText();
	
	String absent();
	String addToList();
	String addToMap();
	String addToSet();
	String cancel();
	String change();
	String changeInstance();
	String clearText();
	String clearTextDescription();
	String copying();
	String copyType();
	String deepCopy();
	String empty();
	String entitiesCopiedClipboard(int number);
	String entityCopiedClipboard(String entityName);
	String errorLoadingAbsentProperty(String propertyName);
	String errorPreparingNewInstance();
	String errorRollingEditionBack();
	String errorRunningOnEditRequest();
	String invalidPaste();
	String itemSelected(int number);
	String keyValuePairs();
	String link();
	String move();
	String node();
	String noItemsToDisplay();
	String noOptionsAvailable();
	String ok();
	String onlyEntitiesCanBeCopied();
	String onlySameEntityTypeCanBeCopied();
	String pastedEntityNotInstantiable();
	String setNull();
	String setPropertyToNull();
	String simpleCopy();
	String shallowCopy();
	String toClipboard();
	String toClipboardDescription();
	String workWith();
	String workWithDescription();

}
