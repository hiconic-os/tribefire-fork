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

import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.List;

import com.braintribe.gwt.gmview.client.js.interop.InteropConstants;
import com.braintribe.model.generic.path.ModelPath;
import com.google.gwt.dom.client.Element;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@SuppressWarnings("unusable-by-js")
@JsType(namespace = InteropConstants.VIEW_NAMESPACE)
public interface GmSelectionSupport {
	
	public void addSelectionListener(GmSelectionListener  sl);

    public void removeSelectionListener(GmSelectionListener  sl);

    public ModelPath getFirstSelectedItem();

    public List<ModelPath> getCurrentSelection();

    public boolean isSelected(Object element);
    
    public void select(int index, boolean keepExisting);
    
    @JsIgnore
    public GmContentView getView();
    
    /**
     * Selected the element if found.
     * @param element - the element to be selected
     * @param keepExisting - true for keeping the existing selected entries
     */
    @JsIgnore
    public default boolean select(Element element, boolean keepExisting) {
    	return false;
    }

    /**
     * Selected the element on Horizontal row.
     * @param next - true for next element, false for previous element
     * @param keepExisting - true for keeping the existing selected entries
     */
    @JsMethod (name = "selectHorizontal")
    public default boolean selectHorizontal(Boolean next, boolean keepExisting) {
    	return false;
    }    

    /**
     * Selected the element on Vertical column.
     * @param next - true for next element, false for previous element
     * @param keepExisting - true for keeping the existing selected entries
     */
    @JsMethod (name = "selectVertical")
    public default boolean selectVertical(Boolean next, boolean keepExisting) {
    	return false;
    }    
    
    /**
     * This must be overridden by views which are GmTreeView.
     */
    public default void selectRoot(int index, boolean keepExisting) {
    	select(index, keepExisting);
    }
    
    public default List<List<ModelPath>> transformSelection(List<ModelPath> selection) {
    	if (selection == null)
    		return null;
    	
		List<List<ModelPath>> list = newList();
		
		for (ModelPath modelPath : selection) {
			List<ModelPath> singleList = newList();
			singleList.add(modelPath);
			list.add(singleList);
		}
		
		return list;
	}
    
    public default int getFirstSelectedIndex() {
    	return -1;
    }
    
    /**
     * This must be implemented for every view interested in supporting the deselection
     */
    public default void deselectAll() {
    	//NOP
    }

}
