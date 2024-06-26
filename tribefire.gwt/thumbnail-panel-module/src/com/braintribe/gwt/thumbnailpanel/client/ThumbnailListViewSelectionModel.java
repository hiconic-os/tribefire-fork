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
package com.braintribe.gwt.thumbnailpanel.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewSelectionModel;
import com.sencha.gxt.widget.core.client.event.XEvent;

/**
 * {@link ListViewSelectionModel}'s implementation to fix Sencha problem with Shift+Mouse click multiselect and scrolling.
 * Also fixing the problem where the right mouse click wouldn't fire the selection event.
 * 
 */

public class ThumbnailListViewSelectionModel<M> extends ListViewSelectionModel<M> {

	  private int indexOnSelectNoShift;
	  private boolean disableClickSelection;
	  
	  public ThumbnailListViewSelectionModel(ThumbnailPanel thumbnailPanel) {
		  setSelectionMode(SelectionMode.MULTI);
		  setVertical(false);
		  
		  thumbnailPanel.addBeforeShowContextMenuHandler(event -> {
			  if (fireSelectionChangeOnClick) {
				  fireSelectionChange();
				  fireSelectionChangeOnClick = false;
			  }
		  });
	  }
	  
	  /**
	   * Disables the click selection.
	   */
	  public void setDisableClickSelection(boolean disableClickSelection) {
		this.disableClickSelection = disableClickSelection;
	}
	  
	  @Override
	  protected void onMouseClick(NativeEvent event) {
		    if (disableClickSelection)
		    	return;
		    
		    XEvent e = event.cast();
		    XElement target = e.getEventTargetEl();

		    int index = this.listView.findElementIndex(target);
		    if (index == -1) {
		    	super.onMouseClick(event);
		        return;
		    }
		    
		    if (selectionMode == SelectionMode.MULTI) {
		      M sel = listStore.get(index);
		      if (e.getCtrlOrMetaKey() && isSelected(sel)) {
		        indexOnSelectNoShift = index;
		      } else if (e.getCtrlOrMetaKey()) {
		        indexOnSelectNoShift = index;
		      }
		    }
		    
		    super.onMouseClick(event);
	  }
	  @Override
	  protected void onMouseDown(NativeEvent event) {
		   XEvent e = event.<XEvent>cast();
		   if (selectionMode != SelectionMode.MULTI || e.isRightClick()) {
			   super.onMouseDown(event);
		   } else {
			  XElement target = e.getEventTargetEl();
			  int selIndex = listView.findElementIndex(target);
			   
			  if (selIndex == -1 || isLocked() || isInput(target)) {
				  return;
			  }			  
			  
		      mouseDown = true;
		  
		      M sel = listStore.get(selIndex);
		      if (sel == null) {
		        return;
		      }
		      
		      boolean isSelected = isSelected(sel);
		      boolean isShift = e.getShiftKey();
		      boolean isMeta = e.getCtrlOrMetaKey();
		      
		      if (!isMeta) {	      
			      if (isShift && lastSelected != null) {
			    	  	//RVE commented out - makes flickering and scrolling problems
			            int last = listStore.indexOf(lastSelected);
			            focusItem(listView, last);
	
			            int start;
			            int end;
			            // This deals with flipping directions
			            if (indexOnSelectNoShift < selIndex) {
			              start = indexOnSelectNoShift;
			              end = selIndex;
			            } else {
			              start = selIndex;
			              end = indexOnSelectNoShift;
			            }
			            
			            select(start, end, false);
			      } else if (!isSelected) {
			            // reset the starting location of multi select
			            indexOnSelectNoShift = selIndex;
	
			            //RVE commented out - makes flickering and scrolling problems
			            mouseDown = false;
			            focusItem(listView, selIndex);
			            doSelect(Collections.singletonList(sel), false, false);
			      }
		      }
		      mouseDown = false;
		  }	
	  }
	  
	  
	  @Override
	  public void select(int start, int end, boolean keepExisting) {
	    if (store instanceof ListStore) {
	      ListStore<M> ls = (ListStore<M>) store;
	      List<M> sel = new ArrayList<M>();
	      if (start <= end) {
	        for (int i = start; i <= end; i++) {
	          Object obj = ls.get(i);
	          if (obj != null)
	        	  sel.add(ls.get(i));
	        }
	      } else {
	        for (int i = start; i >= end; i--) {
	          Object obj = ls.get(i);
	          if (obj != null)
	        	  sel.add(ls.get(i));
	        }
	      }
	      doSelect(sel, keepExisting, false);
	    }
	  }
	  
	@Override
	protected void onKeyPress(NativeEvent event) {
		if (event.getKeyCode() == KeyCodes.KEY_SPACE) //Ignores space handling
			return;
		
		super.onKeyPress(event);
	}
	
	/**
	 * Focus the given index into view.
	 */
	public void focusItem(int index) {
		focusItem(listView, index);
	}
	  
	  protected void focusItem(ListView<M, ?> listView, int index) {	  
		  XElement elem = listView.getElement(index);
		  if (elem != null) {
			  //elem.scrollIntoView(listView.getElement(), false);
		      focusEl(listView).setXY(elem.getXY());
		  }
		  
		  listView.focus();
	  }	  	
	  
	  
	  protected native XElement focusEl(ListView<M,?> listView) /*-{
	  	return listView.@com.sencha.gxt.widget.core.client.ListView::focusEl;
      }-*/;
	  
}
