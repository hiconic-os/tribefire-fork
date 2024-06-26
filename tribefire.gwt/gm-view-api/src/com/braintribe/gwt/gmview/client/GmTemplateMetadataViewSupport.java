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
package com.braintribe.gwt.gmview.client;

import com.braintribe.gwt.gm.storage.api.ColumnData;
import com.braintribe.model.meta.data.prompt.AutoExpand;

/**
 * This interface should be implemented by views which are interested in receiving metadata defined within templates.
 * @author michel.docouto
 *
 */
public interface GmTemplateMetadataViewSupport {
	
	/**
     * Sets the optional display paths. Any path which is not included in this list, if it is set, should be hidden by default. 
     * @param columnData - the list of paths to be shown.
     */
    public void setColumnData(ColumnData columnData);
	
	public void setAutoExpand(AutoExpand autoExpand);

}
