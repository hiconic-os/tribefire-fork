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
package com.braintribe.gwt.gm.storage.expert.impl.wb;

import com.braintribe.gwt.gm.storage.api.ColumnData;
import com.braintribe.gwt.gm.storage.api.StorageHandle;
import com.braintribe.gwt.gm.storage.expert.api.QueryStorageExpert;
import com.braintribe.gwt.gm.storage.expert.api.QueryStorageInput;
import com.braintribe.gwt.gm.storage.impl.wb.WbStorageHandle;
import com.braintribe.gwt.gm.storage.impl.wb.WbStorageRuntimeException;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.query.Query;
import com.braintribe.model.template.Template;
import com.braintribe.model.template.meta.TemplateMetaData;
import com.braintribe.model.workbench.TemplateQueryAction;
import com.braintribe.model.workbench.meta.QueryString;

public class WbQueryStorageExpert implements QueryStorageExpert {

	@Override
	public QueryStorageInput prepareStorageInput(Query query, String queryString, ColumnData columnData) {
		WbQueryStorageInput storageInput = WbQueryStorageInput.T.create();
		storageInput.setQueryString(queryString);
		storageInput.setQuery(query);
		storageInput.setColumnData(columnData);
		return storageInput;
	}

	@Override
	public StorageHandle prepareStorageHandle(GenericEntity entity) {
		if (entity instanceof Folder) {
			WbStorageHandle storageHandle = new WbStorageHandle();
			storageHandle.setQueryFolder((Folder) entity);
			return storageHandle;
		}

		return null;
	}

	@Override
	public String getQueryString(StorageHandle handle) {
		if (handle instanceof WbStorageHandle) {
			final WbStorageHandle storageHandle = (WbStorageHandle) handle;
			final Folder queryFolder = storageHandle.getQueryFolder();

			if (queryFolder != null) {
				// Check for Template Query-Action.
				if (queryFolder.getContent() instanceof TemplateQueryAction) {
					final TemplateQueryAction templateQueryAction = (TemplateQueryAction) queryFolder.getContent();
					Template queryTemplate = templateQueryAction.getTemplate();

					// Check template for query
					if (queryTemplate != null) {
						// Try to find the QueryString
						for (final TemplateMetaData metaData : queryTemplate.getMetaData()) {
							if (metaData instanceof QueryString) {
								// QueryString Meta-Data found
								QueryString queryString = (QueryString) metaData;
								return queryString.getValue();
							}
						}
					}
				}

				return null;
			}
		}

		// Throw not found exception
		throw new WbStorageRuntimeException("Could not get Query-String. Invalid handle received.");
	}
}
