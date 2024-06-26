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

import java.text.ParseException;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.cell.core.client.form.TriggerFieldCell;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;

/**
 * {@link TriggerFieldCell} implementation for the {@link ExtendedStringField}.
 * @author michel.docouto
 *
 */
public class ExtendedStringCell extends TriggerFieldCell<String> {
	
	public interface ExtendedStringCellAppearance extends TriggerFieldAppearance {
		//NOP
	}
	
	public ExtendedStringCell() {
		this(GWT.<ExtendedStringCellAppearance> create(ExtendedStringCellAppearance.class));
	}
	
	public ExtendedStringCell(ExtendedStringCellAppearance appearance) {
	    super(appearance);
	    setPropertyEditor(new PropertyEditor<String>() {
			@Override
			public String parse(CharSequence text) throws ParseException {
				return text.toString();
			}
			
			@Override
			public String render(String object) {
				return object;
			}
		});
	}
	
}
