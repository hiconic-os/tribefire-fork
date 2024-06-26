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
package com.braintribe.model.generic.validation.expert;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface LocalizedText extends Messages {
	
	public static LocalizedText INSTANCE = GWT.create(LocalizedText.class);
	
	public String mandatoryMessage(String propertyName);
	public String uniqueMessage();
	public String stringRegexpMessage();
	public String collectionElementMinCountMessage(String propertyName, int minCount);
	public String collectionElementMaxCountMessage(String propertyName, int maxCount);
	public String collectionElementMinLengthMessage(String propertyName, int minCount);
	public String collectionElementMaxLengthMessage(String propertyName, int maxCount);
	public String collectionElementMinLengthFailMessage(String propertyName, int minCount, String failValues);
	public String collectionElementMaxLengthFailMessage(String propertyName, int maxCount, String failValues);	
	public String lessEqual(String propertyName, String value);
	public String less(String propertyName, String value);
	public String greaterEqual(String propertyName, String value);
	public String greater(String propertyName, String value);
	public String stringMinSizeMessage(String propertyName, int minSize);
	public String stringMaxSizeMessage(String propertyName, int maxSize);

	//short texts
	public String stringAllowEmpty();
	public String stringDenyEmpty();
	public String stringGreatherThan(String minSize);
	public String stringLesserThan(String maxSize);
	public String stringGreatherEqual(String minSize);
	public String stringLesserEqual(String maxSize);
	public String stringGreatherEqualLength(String minSize);
	public String stringLesserEqualLength(String maxSize);
	public String stringAnd();
	public String stringOr();
	public String stringGreatherAndLesser(String minSize, String maxSize);
}
