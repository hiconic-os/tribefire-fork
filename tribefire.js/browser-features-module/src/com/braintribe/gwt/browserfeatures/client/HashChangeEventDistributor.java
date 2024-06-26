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

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gwt.ioc.client.Configurable;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class HashChangeEventDistributor {
	private List<HashChangeListener> hashChangeListeners = new ArrayList<>();
	private Timer timer;
	private boolean active;
	private int pollIntervalInMillies = 250;
	private String lastHash = "";
	private HashChangeListener[] listenersCopy = new HashChangeListener[0];
	private JavaScriptObject oldNativeListener;
	
	private final static HashChangeEventDistributor instance = new HashChangeEventDistributor();
	
	public static HashChangeEventDistributor getInstance() {
		return instance;
	}
	
	private HashChangeEventDistributor() {
		//Instantiation disabled
	}
	
	@Configurable
	public void setHashChangeListeners(List<HashChangeListener> hashChangeListeners) {
		this.hashChangeListeners = hashChangeListeners;
		manageActivity();
	}
	
	@Configurable
	public void setPollIntervalInMillies(int pollIntervalInMillies) {
		this.pollIntervalInMillies = pollIntervalInMillies;
	}
	
	protected native boolean hasNativeHashChangeSupport() /*-{
	   return "onhashchange" in $wnd;
	}-*/;
	
	protected native JavaScriptObject addNativeHashChangeListener() /*-{
		var oldListener = $wnd.onhashchange;
		var distributor = this;
	    $wnd.onhashchange = function() {
	    	distributor.@com.braintribe.gwt.browserfeatures.client.HashChangeEventDistributor::fireHashChangedEvent()();
	    };
	    return oldListener;
	}-*/;
	
	public static native void addHashChangeHandler(HashChangeHandler listener) /*-{
	    $wnd.onhashchange = function() {
	    	listener();
	    };
	}-*/;
	
	protected native void removeNativeHashChangeListener(JavaScriptObject oldListener) /*-{
		$wnd.onhashchange = oldListener;
	}-*/;
	
	public Timer getTimer() {
		if (timer == null) {
			timer = new Timer() {
				@Override
				public void run() {
					checkForUrlHashChanges();
				}
			};
		}

		return timer;
	}
	
	protected void checkForUrlHashChanges() {
		String currentHash = Window.Location.getHash();
		
		// detect hash changes
		if (!currentHash.equals(lastHash)) {
			// hash changed so decode it
			lastHash = currentHash;
			fireHashChangedEvent();
		}
	}
	
	/**
	 * Starts a the timer of this poll that examines the url for changes of the hash (#) part
	 */
	public void start() {
		if (active)
			return;
		
		if (hasNativeHashChangeSupport())
			oldNativeListener = addNativeHashChangeListener();
		else {
			timer = new Timer() {
				@Override
				public void run() {
					checkForUrlHashChanges();
				}
			};
			timer.scheduleRepeating(pollIntervalInMillies);
		}
		active = true;
	}

	/**
	 * Stops the timer of this poll 
	 */
	public void stop() {
		if (!active)
			return;
		
		if (hasNativeHashChangeSupport())
			removeNativeHashChangeListener(oldNativeListener);
		else {
			timer.cancel();
			timer = null;
		}
		active = false;
	}
	
	protected void manageActivity() {
		if (hashChangeListeners.isEmpty())
			stop();
		else
			start();
	}
	
	public void fireHashChangedEvent() {
		String currentHash = Window.Location.getHash();
		listenersCopy = hashChangeListeners.toArray(listenersCopy);
		
		for (HashChangeListener listener: listenersCopy)
			listener.hashChanged(currentHash);
	}
	
	public void addHashChangeListener(HashChangeListener listener) {
		hashChangeListeners.add(listener);
		manageActivity();
	}
	
	public void removeHashChangeListener(HashChangeListener listener) {
		hashChangeListeners.add(listener);
		manageActivity();
	}
}
