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
package com.braintribe.gwt.smartmapper.client.util;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.model.accessdeployment.smart.meta.CompositeInverseKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.CompositeKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.InverseKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.KeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.LinkPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.OrderedLinkPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.PropertyAsIs;
import com.braintribe.model.accessdeployment.smart.meta.QualifiedPropertyAssignment;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

public class ConversionRendering {
	
	private static Map<EntityType<? extends GenericEntity>, String> renderMap;
	
	static{
		renderMap = new HashMap<EntityType<? extends GenericEntity>, String>();
		
		renderMap.put(PropertyAsIs.T, "=");
		renderMap.put(QualifiedPropertyAssignment.T, "=");
		renderMap.put(KeyPropertyAssignment.T, "join");
		renderMap.put(InverseKeyPropertyAssignment.T, "^join");
		renderMap.put(LinkPropertyAssignment.T, "join+");
		renderMap.put(OrderedLinkPropertyAssignment.T, "ordered join+");
		renderMap.put(CompositeKeyPropertyAssignment.T, "joins");
		renderMap.put(CompositeInverseKeyPropertyAssignment.T, "^joins");
	}
	
	public static String renderConversion(EntityType<GenericEntity> type){
		if(renderMap.containsKey(type)){
			return renderMap.get(type);
		}else
			return "?";
	}

}
