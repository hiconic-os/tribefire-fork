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
package com.braintribe.model.processing.query.smart.test.model.accessA.special;

import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessA.StandardIdentifiableA;
import com.braintribe.model.processing.query.smart.test.model.smart.special.SmartReaderA;

/**
 * @see SmartReaderA
 */

public interface ReaderA extends StandardIdentifiableA {

	EntityType<ReaderA> T = EntityTypes.T(ReaderA.class);

	// @formatter:off
	String getName();
	void setName(String name);

	String getFavoritePublicationTitle();
	void setFavoritePublicationTitle(String favoritePublicationTitle);

	Set<String> getFavoritePublicationTitles();
	void setFavoritePublicationTitles(Set<String> favoritePublicationTitles);

	ManualA getFavoriteManual();
	void setFavoriteManual(ManualA favoriteManual);

	// mapped via IKPA - this.ikpaPublicationTitle = SmartPublicationB.title
	String getIkpaPublicationTitle();
	void setIkpaPublicationTitle(String ikpaPublicationTitle);

	// ##################################################
	// ## . . . . . . Weak-type Properties . . . . . . ##
	// ##################################################

	String getFavoriteManualTitle();
	void setFavoriteManualTitle(String favoriteManualTitle);

	Set<String> getFavoriteManualTitles();
	void setFavoriteManualTitles(Set<String> favoriteManualTitles);
	// @formatter:on

}
