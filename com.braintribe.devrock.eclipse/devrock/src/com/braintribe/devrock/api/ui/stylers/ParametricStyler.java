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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IDisposable;

import com.braintribe.utils.lcd.LazyInitialized;

/**
 * a {@link StyledString} {@link Styler} that can be parametrized for font, font-style (bold or so) and foreground, background color 
 * @author pit
 *
 */
public class ParametricStyler extends Styler  implements IDisposable {

	private final Font initialFont;
	private FontDescriptor fontDescriptor;
	private Integer fontModifier;
	private Color foreground;
	private Color background;
	private Float sizeFactor;
	
	LazyInitialized<Font> derivedFont = new LazyInitialized<>( this::createFont);

	public ParametricStyler(Font initialFont, Integer fontModifier) {
		this.initialFont = initialFont;
		this.fontModifier = fontModifier;
		foreground = background = null;
		sizeFactor = null;
	}
			
	
	/**
	 * @param initialFont
	 * @param fontModifier
	 * @param foreground - system color
	 * @param background - system color
	 * @param sizeFactor
	 */
	public ParametricStyler(Font initialFont, Integer fontModifier, Integer foreground, Integer background, Float sizeFactor) {
		this.initialFont = initialFont;
		this.fontModifier = fontModifier;
		this.sizeFactor = sizeFactor;
		
		Display display = Display.getCurrent();
		if (foreground != null) {
			this.foreground = display.getSystemColor(foreground);
		}
		if (background != null) {
			this.background = display.getSystemColor(background);
		}
	}
	
	/**
	 * @param initialFont
	 * @param fontModifier
	 * @param foreground
	 * @param background
	 * @param sizeFactor
	 */
	public ParametricStyler(Font initialFont, Integer fontModifier, Color foreground, Color background, Float sizeFactor) {
		this.initialFont = initialFont;
		this.fontModifier = fontModifier;
		this.sizeFactor = sizeFactor;		

		this.foreground = foreground;
		this.background = background;
	}
	

	@Override
	public void applyStyles(TextStyle textStyle) {
		Font font = derivedFont.get();
        textStyle.font = font;
               
        if (foreground != null) {
        	textStyle.foreground = foreground;
        }
        if (background != null) {
        	textStyle.background = background;
        }
	}
	
	private Font createFont() {
		FontData[] fontData = initialFont.getFontData();
		if (sizeFactor != null) {
			for (FontData data : fontData) {
				data.setHeight( Math.round(data.getHeight() * sizeFactor));				
			}
		}		
		if (fontModifier != null) {
			fontDescriptor = FontDescriptor.createFrom( fontData).setStyle( fontModifier);			
		}
		else {
			fontDescriptor = FontDescriptor.createFrom( fontData);	
		}
		Font font = fontDescriptor.createFont(Display.getCurrent());
		return font;
	}
	
	@Override
	public void dispose() {
		if (fontDescriptor == null)
			return;
		Font font = derivedFont.get();
		if (font != null) {
			fontDescriptor.destroyFont(font);
		}
		
	}
	
}
