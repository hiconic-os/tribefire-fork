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
package com.braintribe.gwt.ioc.gme.client.resources;

import com.google.gwt.resources.client.ImageResource;

public interface ColouredCustomizationResources extends CustomizationResources {
	
	@Override
	@Source("com/braintribe/gwt/gxt/gxtresources/images/log.gif")
	public ImageResource log();
	@Override
	@Source("com/braintribe/gwt/gxt/gxtresources/images/userManagement.png")
	public ImageResource userManagement();
	@Override
	@Source("com/braintribe/gwt/gxt/gxtresources/images/key.png")
	public ImageResource key();
	@Override
	@Source("com/braintribe/gwt/gxt/gxtresources/images/maintenanceTasks.png")
	public ImageResource maintenanceTasks();
	@Override
	@Source("com/braintribe/gwt/gxt/gxtresources/images/verification.png")
	public ImageResource verification();

}
