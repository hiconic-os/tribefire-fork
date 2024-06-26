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
package com.braintribe.web.velocity.renderer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author pit
 *
 */
public class VelocityRenderContext {

	private String name;
	private Map<String, Object> contents;
	
	public VelocityRenderContext( String name){
		this.name = name;
	}
	
	public void setValue( String name, Object object) {
		if (contents == null)
			contents = new HashMap<String, Object>();
		contents.put( name, object);				
	}
	
	public Object getValue( String name) {
		if (contents == null)
			return null;
		return contents.get( name);
	}

	public String getName() {
		return name;
	}

	public Set<String> getVariables() {
		if (contents != null)
			return contents.keySet();
		return new HashSet<String>();
	}
	
	
	public void clear() {
		contents.clear();
	}
	
	
}
