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
package com.braintribe.gwt.gme.constellation.client;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.gwt.gme.verticaltabpanel.client.VerticalTabActionMenu;
import com.braintribe.gwt.gmview.action.client.DefaultGmContentViewActionManager;
import com.braintribe.gwt.gmview.client.ClipboardListener;
import com.braintribe.model.generic.path.ModelPath;
import com.google.gwt.core.client.Scheduler;

/**
 * Class responsible for controlling {@link ClipboardConstellation} instances.
 * @author michel.docouto
 *
 */
public class ClipboardConstellationProvider implements Function<Supplier<MasterDetailConstellation>, Supplier<ClipboardConstellation>>, ClipboardListener {
	
	private Set<ClipboardConstellation> clipboardConstellations;
	private Set<ModelPath> modelsInClipboard = new LinkedHashSet<>();
	private VerticalTabActionMenu verticalTabActionBar;
	private Supplier<DefaultGmContentViewActionManager> actionManagerProvider;
	
	public void setVerticalTabActionBar(VerticalTabActionMenu verticalTabActionBar) {
		this.verticalTabActionBar = verticalTabActionBar;
	}
	
	public void setActionManagerProvider(Supplier<DefaultGmContentViewActionManager> actionManagerProvider) {
		this.actionManagerProvider = actionManagerProvider;		
	}
	
	@Override
	public Supplier<ClipboardConstellation> apply(Supplier<MasterDetailConstellation> masterDetailConstellationProvider) throws RuntimeException {
		return () -> {
			ClipboardConstellation constellation = new ClipboardConstellation();
			constellation.setMasterDetailConstellationProvider(masterDetailConstellationProvider);
			if (clipboardConstellations == null)
				clipboardConstellations = new LinkedHashSet<>();
			clipboardConstellations.add(constellation);
			constellation.addClipboardListener(ClipboardConstellationProvider.this);
			constellation.setVerticalTabActionBar(verticalTabActionBar);	
			
			if (actionManagerProvider != null)
				constellation.setActionManager(actionManagerProvider.get());
			
			if (!modelsInClipboard.isEmpty())
				Scheduler.get().scheduleDeferred(() -> constellation.addModelsToClipboard(new ArrayList<>(modelsInClipboard)));
			
			return constellation;
		};
	}
	
	@Override
	public void onModelsAddedToClipboard(List<ModelPath> models) {
		if (clipboardConstellations != null) {
			for (ClipboardConstellation constellation : clipboardConstellations)
				constellation.addModelsToClipboard(models);
		}
		
		if (models != null)
			modelsInClipboard.addAll(models);
	}
	
	@Override
	public void onModelsRemovedFromClipboard(List<ModelPath> models) {
		if (models != null)
			modelsInClipboard.removeAll(models);
	}
	
	@Override
	public void onModelsInClipoboardCleared() {
		modelsInClipboard.clear();
	}

}
