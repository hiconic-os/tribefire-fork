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
package com.braintribe.gwt.processdesigner.client.element;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ElementRendering extends Messages{
	
	public static final ElementRendering INSTANCE = GWT.create(ElementRendering.class);
	
	public String style(String fill, String strokeColor, double strokeWidth);
	public String styleWithDashArray(String fill, String strokeColor, double strokeWidth, String dashArray);
	public String quadraticBezierCurve (double x, double y, double cX, double cY, double eX, double eY);

}
