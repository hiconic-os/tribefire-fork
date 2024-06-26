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
package com.braintribe.devrock.zarathud.model.common;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.forensics.FingerPrint;

public interface FingerPrintNode extends Node {
		
	EntityType<FingerPrintNode> T = EntityTypes.T(FingerPrintNode.class);
	
	String fingerPrint = "fingerPrint";
	String rating = "rating";
	String comparisonTopEntity = "comparisonTopEntity";
	String baseTopEntity = "baseTopEntity";
	String representation = "representation";
	
	FingerPrint getFingerPrint();
	void setFingerPrint(FingerPrint value);
	
	FingerPrintRating getRating();
	void setRating(FingerPrintRating value);
	
	ZedEntity getComparisonTopEntity();
	void setComparisonTopEntity(ZedEntity value);
	
	ZedEntity getBaseTopEntity();
	void setBaseTopEntity(ZedEntity value);
	
	FingerPrintRepresentation getRepresentation();
	void setRepresentation(FingerPrintRepresentation value);
}
