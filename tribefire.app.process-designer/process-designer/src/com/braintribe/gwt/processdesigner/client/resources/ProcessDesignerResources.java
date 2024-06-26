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
package com.braintribe.gwt.processdesigner.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ProcessDesignerResources extends ClientBundle {
	
	public static final ProcessDesignerResources INSTANCE = GWT.create(ProcessDesignerResources.class);
	
	@Source("Add_16x16.png")
	public ImageResource add();
	@Source("Add_32x32.png")
	public ImageResource addBig();
	
	@Source("Delete_32x32.png")
	public ImageResource remove();
	
	@Source("zoomOut_32x32.png")
	public ImageResource zoomOut();
	@Source("zoomIn_32x32.png")
	public ImageResource zoomIn();
	
	@Source("home_16x16.png")
	public ImageResource home();
	@Source("processDef_32x32.png")
	public ImageResource homeBig();
	
	@Source("refresh_16x16.png")
	public ImageResource restart();
	@Source("refresh_32x32.png")
	public ImageResource restartBig();
	
	@Source("user_16x16.png")
	public ImageResource user();
	@Source("undefined_16x16.png")
	public ImageResource undefined();	
	@Source("worker_32x32.png")
	public ImageResource worker();
	@Source("workers_32x32.png")
	public ImageResource workers();	
	@Source("end_16x16.png")
	public ImageResource end();
	
	@Source("init_32x32.png")
	public ImageResource init();
	public ImageResource select();
	public ImageResource connect();
	
	@Source("condition.edge.png")
	public ImageResource conditionalEdge();	
	@Source("edge.png")
	public ImageResource edge();
	@Source("error.node.png")
	public ImageResource errorNode();	
	@Source("overdue.node.png")
	public ImageResource overdueNode();

}
