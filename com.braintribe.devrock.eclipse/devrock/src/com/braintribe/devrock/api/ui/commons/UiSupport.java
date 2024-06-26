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
package com.braintribe.devrock.api.ui.commons;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.services.IDisposable;

import com.braintribe.devrock.api.ui.images.Images;
import com.braintribe.devrock.api.ui.stylers.Stylers;
import com.braintribe.logging.Logger;


/**
 * a class to manage resources - lives only during plugin's running.
 * @author pit
 *
 */
public class UiSupport implements IDisposable {
	private static Logger log = Logger.getLogger(UiSupport.class);
	
	// stylers 
	private Map<String, Stylers> stylerMap = new ConcurrentHashMap<>();
	// images
	private Images images = new Images();
	// unstructured stuff
	private final Map<Object, Object> map = new ConcurrentHashMap<>();
	
	/**
	 * if the styler doesn't exist yet, it will be created 
	 * @param key - the key
	 * @return - the {@link Stylers} which may be freshly instantiated
	 */
	public Stylers stylers(String key) { 
		return stylerMap.computeIfAbsent( key, k -> new Stylers());
	}
	
	/**
	 * if the styler doesn't exist yet, it will created with the font passed
	 * @param key - the key 
	 * @param basefont - the basefont for the styler 
	 * @return - the {@link Styler}
	 */
	public Stylers stylers(String key, Font basefont) {
		Stylers styler = stylerMap.get(key);
		if (styler == null) {
			styler = new Stylers();
			styler.setInitialFont(basefont);
		}
		else {
			log.warn("a styler is already present for the key: " + key);
		}
		return styler;
	}	
	
	/**
	 * @return - the {@link Images}
	 */
	public Images images() {
		return images;
	}

	@Override
	public void dispose() {
		stylerMap.values().stream().forEach( s -> s.dispose());
		stylerMap.clear();
		images.dispose();		
	}
	

	/**
	 * let's you add a custom value to the map 
	 * @param key - the key
	 * @param value - the {@link Object} to store 
	 */
	public void setCustomValue(Object key, Object value) {
		map.put(key, value);
	}

	/**
	 * get a custom value
	 * @param <T>
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCustomValue(Object key) {
		return (T) map.get(key);
	}

}
