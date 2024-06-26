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
package tribefire.extension.xml.schemed.model.xsd;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;

@Abstract
public interface Numbered extends  GenericEntity {

	@Initializer("1")
	int getMaxOccurs();
	void setMaxOccurs(int maxOccurs);
	
	boolean getMaxOccursSpecified();
	void setMaxOccursSpecified( boolean specified);

	@Initializer("1")
	int getMinOccurs();
	void setMinOccurs(int minOccurs);
	
	boolean getMinOccursSpecified();
	void setMinOccursSpecified( boolean specified);

}
