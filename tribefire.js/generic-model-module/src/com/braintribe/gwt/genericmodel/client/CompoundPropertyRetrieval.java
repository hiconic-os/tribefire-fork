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
package com.braintribe.gwt.genericmodel.client;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.utils.i18n.I18nTools;
import com.google.gwt.i18n.client.LocaleInfo;

public class CompoundPropertyRetrieval {

	public static Object retrieveCompoundProperty(Object entity, Property propertyChain[], boolean selective) {
		for (int i = 0; entity != null && i < propertyChain.length; i++) {
			entity = propertyChain[i].get((GenericEntity) entity);

			if (entity instanceof List<?>) {
				List<?> list = (List<?>) entity;
				entity = list.isEmpty() ? null : list.get(0);
			}
		}

		if (entity == null)
			return "";

		if (!(entity instanceof GenericEntity))
			return entity;

		if (entity instanceof LocalizedString) {
			String locale = LocaleInfo.getCurrentLocale().getLocaleName();
			LocalizedString ls = (LocalizedString) entity;
			return I18nTools.get(ls, locale);
		}

		return selective ? ((GenericEntity) entity).toSelectiveInformation() : entity;
	}

}
