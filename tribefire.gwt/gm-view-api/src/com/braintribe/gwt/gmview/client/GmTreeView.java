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

 /**
 * Interface of a view which has a tree grid style (showing node and other columns). 
 * @author michel.docouto
 *
 */
public interface GmTreeView extends GmContentView {

 	public void showAllColumns();

 	public void showOnlyNodeColumn();
 	
 	/**
     * Limits the initial amount of data to render when exchanging views, for example.
     */
    public void limitAmountOfDataToRender();
    
    /**
     * Saves the current scroll state of the columns.
     */
    public void saveScrollState();
    
    /**
     * Disables the special handling of the resize.
     */
    public void disableTreeViewResizeHandling();

 }