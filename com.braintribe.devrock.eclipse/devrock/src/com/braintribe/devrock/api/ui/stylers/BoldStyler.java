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
package com.braintribe.devrock.api.ui.stylers;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IDisposable;

import com.braintribe.utils.lcd.LazyInitialized;

/**
 * a {@link Styler} to make {@link StyledString} BOLD
 * 
 * @author pit
 *
 */
public class BoldStyler extends Styler implements IDisposable {
	
	private Font initialFont;

	private FontDescriptor fontDescriptor;

	LazyInitialized<Font> boldFont = new LazyInitialized<>( this::createFont);
	
	/**
	 * @param initialFont - the font the bold-font is derived of 
	 */
	public BoldStyler(Font initialFont) {
		this.initialFont = initialFont;		
	}

	@Override
	public void applyStyles(TextStyle textStyle) {		
        textStyle.font = boldFont.get();		
	}

	private Font createFont() {				
		fontDescriptor = FontDescriptor.createFrom( initialFont.getFontData()).setStyle( SWT.BOLD);					
		Font font = fontDescriptor.createFont(Display.getCurrent());
		return font;
	}

	@Override
	public void dispose() {
		if (fontDescriptor == null) {
			return;			
		}
		Font font = boldFont.get();
		if (font != null) {
			fontDescriptor.destroyFont(font);
		}
	}
	
	
}
