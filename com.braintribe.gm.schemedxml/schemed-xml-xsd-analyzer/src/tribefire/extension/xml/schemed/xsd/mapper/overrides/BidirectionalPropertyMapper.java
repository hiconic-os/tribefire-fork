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
package tribefire.extension.xml.schemed.xsd.mapper.overrides;

import java.util.HashMap;
import java.util.Map;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.BidirectionalLink;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;


public class BidirectionalPropertyMapper {
	private Map<String, String> targetToInjectionMap = new HashMap<String, String>();
	
	public BidirectionalPropertyMapper( BidirectionalLink ...bidirectionalLinks) {	
		if (bidirectionalLinks != null) {			
			for (BidirectionalLink backlink: bidirectionalLinks) {
				SchemaAddress schemaAddress = backlink.getSchemaAddress();				
				String key = schemaAddress.getParent() + "." + schemaAddress.getElement();
				String target = backlink.getBacklinkProperty();				
				targetToInjectionMap.put( key, target);
			}
		}
	}

	public String getBidirectionalTarget(String schemaAddress) throws RuntimeException {	
		 return targetToInjectionMap.get( schemaAddress);
	}
	
	
}
