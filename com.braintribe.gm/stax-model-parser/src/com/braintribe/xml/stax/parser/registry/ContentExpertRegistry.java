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
package com.braintribe.xml.stax.parser.registry;

import com.braintribe.xml.stax.parser.experts.ContentExpert;

/**
 * a {@link ContentExpertRegistry} is used to link tags in the xml to experts that can handle the data contained. 
 * @author Pit
 *
 */
public interface ContentExpertRegistry {
	/**
	 * add a tag factory that is able to deliver a new instance of an expert for the tag
	 * @param tag - the tag in the XML
	 * @param factory - a factory that can return an instance of the expert 
	 */
	void addExpertFactory( String tag, ContentExpertFactory factory);
	
	/**
	 * return an instance of the content expert that has been declared for the tag 
	 * @param tag - the tag in the XML
	 * @return - an instance of the expert for the tag 
	 */
	ContentExpert getExpertForTag( String tag);
	
	
}
