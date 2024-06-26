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
package com.braintribe.gwt.logging.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface LoggingResources extends ClientBundle {

	public static LoggingResources INSTANCE = GWT.create(LoggingResources.class);

	@Source("Delete_32x32.png")
	public ImageResource delete();
	@Source("Maximize_32x32.png")
	public ImageResource maximize();
	@Source("Minimize_32x32.png")
	public ImageResource restore();
	@Source("Remove_32x32.png")
	public ImageResource clear();
	@Source("Refresh_32x32.png")
	public ImageResource refresh();
	@Source("Profiling_32x32.png")
	public ImageResource profiling();
}
