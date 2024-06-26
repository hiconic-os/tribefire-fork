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
package com.braintribe.model.processing.query.smart.test.model.smart.special;

import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessA.special.ReaderA;
import com.braintribe.model.processing.query.smart.test.model.smart.StandardSmartIdentifiable;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EmUseCase;

/**
 * TODO UPDATE JAVADOC
 * 
 * This entity references an unmapped type {@link SmartPublication} - this is the {@link EmUseCase#smartReference}
 * situation.
 * 
 * Mapped to {@link ReaderA}.
 */
public interface SmartReaderA extends StandardSmartIdentifiable {

	EntityType<SmartReaderA> T = EntityTypes.T(SmartReaderA.class);

	String getName();
	void setName(String name);

	SmartPublicationB getFavoritePublication();
	void setFavoritePublication(SmartPublicationB favoritePublication);

	Set<SmartPublicationB> getFavoritePublications();
	void setFavoritePublications(Set<SmartPublicationB> favoritePublications);

	SmartManualA getFavoriteManual();
	void setFavoriteManual(SmartManualA favoriteManual);

	// mapped via ReaderBookLink
	SmartPublicationB getFavoritePublicationLink();
	void setFavoritePublicationLink(SmartPublicationB favoritePublicationLink);

	// mapped via ReaderBookSetLink
	Set<SmartPublicationB> getFavoritePublicationLinks();
	void setFavoritePublicationLinks(Set<SmartPublicationB> favoritePublicationLinks);

	// ##################################################
	// ## . . . . . . Weak-type Properties . . . . . . ##
	// ##################################################

	/**
	 * The type of the property is SmartPublication, but we map it to it's sub-type {@link SmartManualA}, so that one is
	 * queried directly. We also throw an exception in case we would assign instance of any other type to this property.
	 */
	SmartPublication getWeakFavoriteManual();
	void setWeakFavoriteManual(SmartPublication weakFavoriteManual);

	Set<SmartPublication> getWeakFavoriteManuals();
	void setWeakFavoriteManuals(Set<SmartPublication> weakFavoriteManuals);

	// mapped as IKPA: SmartManualA.smartManualString = ReaderA.name
	Set<SmartPublication> getWeakInverseFavoriteManuals();
	void setWeakInverseFavoriteManuals(Set<SmartPublication> weakInverseFavoriteManuals);

	// mapped via ReaderBookLink (just like the property above - favoritePublicationLink, no problem if we recycle)
	SmartPublication getWeakFavoriteManualLink();
	void setWeakFavoriteManualLink(SmartPublication weakFavoriteManualLink);

}
