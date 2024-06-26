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
package com.braintribe.gwt.browserfeatures.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;

public class DOMSelectionUtil {
	private static DOMSelectionUtil instance = GWT.create(DOMSelectionUtil.class);

	private DivElement copyPasteElement;
	
	private DivElement getCopyPasteElement() {
		if (copyPasteElement == null) {
			copyPasteElement = Document.get().createDivElement();
			Style style = copyPasteElement.getStyle();
			style.setPosition(Position.ABSOLUTE);
			style.setOverflow(Overflow.HIDDEN);
			style.setLeft(-1, Unit.PX);
			style.setTop(-1, Unit.PX);
			style.setWidth(1, Unit.PX);
			style.setHeight(1, Unit.PX);
			
			Document.get().getBody().appendChild(copyPasteElement);
		}
		
		return copyPasteElement;
	}

	public void supplyForCopyPaste(String text) {
		DivElement copyPasteElement = getCopyPasteElement();
		copyPasteElement.setInnerText(text);
		selectElementText(copyPasteElement);
	}
	
	public static DOMSelectionUtil getInstance() {
		return instance;
	}
	
	public native void selectElementText(Element element)/*-{
		var range = $doc.createRange();
    	range.selectNode(element);
    	var selection = $wnd.getSelection();
    	selection.removeAllRanges();
    	selection.addRange(range);
	}-*/;
}
