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
package com.braintribe.gm.model.marshaller.api.data;

import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.codec.marshaller.api.TypeExplicitness;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Abstract
public interface MarshallQualification extends GenericEntity {
	EntityType<MarshallQualification> T = EntityTypes.T(MarshallQualification.class);
	
	String getResourceName();
	void setResourceName(String resource);
	
	@Mandatory
    String getMimeType();
    void setMimeType(String mimeType);
    
    boolean getStabilizeOrder();
	void setStabilizeOrder(boolean stabilizeOrder);

    boolean getWriteEmptyProperties();
	void setWriteEmptyProperties(boolean writeEmptyProperties);
	
	boolean getWriteAbsenceInformation();
	void setWriteAbsenceInformation(boolean writeAbsenceInformation);

    TypeExplicitness getTypeExplicitness();
	void setTypeExplicitness(TypeExplicitness typeExplicitness);
	
	@Initializer(value="enum(com.braintribe.codec.marshaller.api.OutputPrettiness,mid)")
	OutputPrettiness getPrettiness();
	void setPrettiness(OutputPrettiness prettiness);
	
	@Initializer("0")
	Integer getEntityRecurrenceDepth();
	void setEntityRecurrenceDepth(Integer entityRecurrenceDepth);
}
