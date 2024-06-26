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
package com.braintribe.gwt.gxt.gxtresources.extendedtrigger.client;

import com.braintribe.gwt.gxt.gxtresources.multieditor.client.NoBlurWhileEditingField;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.form.TriggerField;

/**
 * This editor consists of a normal TextField for strings.
 * It has a button that shows a dialog, which then shows a TextBox for editing multi line strings.
 * @author michel.docouto
 *
 */
public class ExtendedStringField extends TriggerField<String> implements NoBlurWhileEditingField {
	
	private static final String systemLineBreak = "\n";  //New line text character
	private static final char editorLineBreak = 12;      //Fake character - at ASCII table mean Form Feed (not visible)
	private static final char spaceCharacter  = 32;
	protected Window dialog;
	protected boolean editingField = false;
	
	public ExtendedStringField() {
		super(new ExtendedStringCell());
		
		addTriggerClickHandler(event -> handleTriggerClick());
	}
	
	protected void handleTriggerClick() {
		//NOP
	}
	
	@Override
	public boolean isEditingField() {
		return editingField;
	}
	
	protected <T> T getTextWithCodedLineBreaks(T value) {
		if (!(value instanceof String))
			return value;
		
		//RVE workaround - as TriggerField clear New line ("\n") characters at String, is need make a fake char {12,32,12}
		//Shows multiline text at one line edit field			
		String stringValue = (String) value;
		try {
			int i = stringValue.indexOf(systemLineBreak);
			while (i > -1) {
				stringValue = stringValue.substring(0, i) + editorLineBreak + spaceCharacter + editorLineBreak
						+ stringValue.substring(i + 1, stringValue.length());
				i = stringValue.indexOf(systemLineBreak);
			}
		}
		catch (Exception e ) {
			stringValue = (String) value;
		}
		
		return (T) stringValue;
	}
	
	public String getTextWithEncodedLineBreaks(String text) {
		if (text == null)
			return null;
		
		//RVE workaround - as TriggerField clear New line ("\n") - Set back fake char to New line character ("\n")			
		String stringValue = text;
		try {
			int i = stringValue.indexOf(editorLineBreak);
			while (i > -1) {				
				boolean lineBreakFound = (stringValue.length() > i + 2) && ((editorLineBreak == stringValue.charAt(i))
						&& (spaceCharacter == stringValue.charAt(i + 1)) && (editorLineBreak == stringValue.charAt(i + 2)));
				if (lineBreakFound)
					stringValue = stringValue.substring(0, i) + systemLineBreak + stringValue.substring(i+3, stringValue.length());
				
				i = stringValue.indexOf(editorLineBreak, i + 1);
			}
		} catch(Exception e) {
			stringValue = text;
		}
		
		return stringValue;
	}

}
