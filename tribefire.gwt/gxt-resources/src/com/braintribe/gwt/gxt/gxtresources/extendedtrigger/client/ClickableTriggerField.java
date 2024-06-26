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

import com.braintribe.gwt.gxt.gxtresources.css.GxtResources;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.form.TriggerField;

/**
 * Interface for fields which provides an icon for hovering effect.
 * @author michel.docouto
 *
 */
public interface ClickableTriggerField {
	
	default public ImageResource getImageResource() {
		return GxtResources.INSTANCE.edit();
	}
	
	public TriggerField<?> getTriggerField();
	
	public void fireTriggerClick(NativeEvent event);
	
	public void setHideTrigger(boolean hideTrigger);

}
