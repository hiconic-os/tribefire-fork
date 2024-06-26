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
package com.braintribe.gwt.gmview.client;

import com.google.gwt.resources.client.ImageResource;

/**
 * Marker for actions which have two states (e.g. collapse/expand, maximize/restore)
 *
 */

public interface DoubleStateAction {
	
	public void setStateIcon1(ImageResource icon);
	public void setStateIcon2(ImageResource icon);
	
	public ImageResource getStateIcon1();
	public ImageResource getStateIcon2();
	
	public void setStateDescription1(String description);
	public void setStateDescription2(String description);
	
	public String getStateDescription1();
	public String getStateDescription2();
	
	public void updateState();
	public Boolean isDefaultState();
}
