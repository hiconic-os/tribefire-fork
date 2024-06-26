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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IDisposable;

import com.braintribe.cfg.Configurable;

/**
 * a 'group' of Styler instances.. 
 * @author pit
 *
 */
public class Stylers {
	public static final String KEY_ITALIC = "italic";
	public static final String KEY_BOLD = "bold";
	public static final String KEY_DEFAULT = "default";
	
	private Map<String, Styler> keyToStylerMap = new ConcurrentHashMap<>();
	private Font initialFont;
	
	/**
	 * constructor using the current font as a base
	 */
	public Stylers() {
		this.initialFont = Display.getCurrent().getSystemFont();
	}
	
	/**
	 * constructor using the passed font as a base
	 * @param initialFont - the {@link Font} to base the derivations from
	 */
	public Stylers(Font initialFont) {
		this.initialFont = initialFont;		
	}
	
	/**
	 * sets the base font 
	 * @param initialFont - the {@link Font} to base the derivations from
	 */
	@Configurable
	public void setInitialFont(Font initialFont) {
		this.initialFont = initialFont;
	}
	
	public Font getInitialFont() {
		return initialFont;
	}
	
	/**
	 * adds a bold, italic and default styler 
	 */
	public void addStandardStylers() {
		keyToStylerMap.computeIfAbsent(KEY_BOLD, k -> new BoldStyler( initialFont));				
		keyToStylerMap.computeIfAbsent(KEY_ITALIC, k -> new ItalicStyler(initialFont));
		keyToStylerMap.computeIfAbsent(KEY_DEFAULT, k -> new ParametricStyler(initialFont, null, (Color) null, (Color) null, null)); 
	}
	
	/**
	 * @param key - the key of one of the standard stylers
	 * @return
	 */
	public Styler standardStyler(String key) {
		addStandardStylers();
		return keyToStylerMap.get(key);
	}
	
	/**
	 * add a styler 
	 * @param key - the key of the Styler
	 * @param styler - the {@link Styler}
	 */
	public void addStyler( String key, Styler styler) {
		keyToStylerMap.put(key, styler);
	}
	
	
	public Styler addDefaultStyler(String key) {
		Styler styler = keyToStylerMap.computeIfAbsent(key, k -> new ParametricStyler(initialFont, (Integer) null));
		return styler;
	}
	public Styler addStyler(String key, Integer fontModifier) {
		Styler styler = keyToStylerMap.computeIfAbsent(key, k -> new ParametricStyler(initialFont, fontModifier));
		return styler;
	}
	
	/**
	 * create and add a styler 
	 * @param key - the key 
	 * @param fontModifier - the font modifier (see SWT)
	 * @param foreground - the foreground (see SWT)
	 * @param background - the background (see SWT)
	 * @return - the {@link Styler} created and added to list
	 */
	public Styler addStyler(String key, Integer fontModifier, Integer foreground, Integer background, Float sizeFactor) {
		Styler styler = keyToStylerMap.computeIfAbsent(key, k -> new ParametricStyler(initialFont, fontModifier, foreground, background, sizeFactor));
		return styler;
	}
	
	public Styler addStyler(String key, Integer fontModifier, Color foreground, Color background, Float sizeFactor) {
		Styler styler = keyToStylerMap.computeIfAbsent(key, k -> new ParametricStyler(initialFont, fontModifier, foreground, background, sizeFactor));
		return styler;
	}
	
	
	/**
	 * @param key - the key of the styler to retrieve
	 * @return - the {@link Styler} if any
	 */
	public Styler styler(String key) {
		return keyToStylerMap.get(key);				
	}
	
	/**
	 * dispose all stylers 
	 */
	public void dispose() {
		keyToStylerMap.values().stream().map( s -> (IDisposable) s).forEach( s -> s.dispose());		
	}

}
