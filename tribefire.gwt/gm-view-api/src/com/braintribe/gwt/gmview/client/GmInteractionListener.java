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

public interface GmInteractionListener {
	
	public void onClick(GmMouseInteractionEvent event);
	
    public void onDblClick(GmMouseInteractionEvent event);
    
    /**
     * Fire when the mouse event is triggering the before expand event.
     * @return true for stopping the event propagation. False otherwise.
     * Stopping propagation will not stop all listeners from being triggered.
     */
    public boolean onBeforeExpand(GmMouseInteractionEvent event);

}
