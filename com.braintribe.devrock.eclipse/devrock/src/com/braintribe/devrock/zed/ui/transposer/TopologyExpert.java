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
package com.braintribe.devrock.zed.ui.transposer;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.zarathud.model.data.AnnotationEntity;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.data.ClassEntity;
import com.braintribe.zarathud.model.data.EnumEntity;
import com.braintribe.zarathud.model.data.FieldEntity;
import com.braintribe.zarathud.model.data.InterfaceEntity;
import com.braintribe.zarathud.model.data.MethodEntity;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.forensics.FingerPrint;

public class TopologyExpert {

	public static ZedEntity findBaseTopOwner( FingerPrint fp) {
		GenericEntity entitySource = fp.getEntitySource();
		if (entitySource == null  || entitySource instanceof Artifact)
			return null;
		return findTopOwner( entitySource);
	}
	
	public static ZedEntity findOtherTopOwner( FingerPrint fp) {
		GenericEntity entitySource = fp.getEntityComparisonTarget();
		if (entitySource == null || entitySource instanceof Artifact)
			return null;
		return findTopOwner( entitySource);
	}
	
	public static ZedEntity findTopOwner( GenericEntity entitySource) {		
		GenericEntity passed = entitySource;
		ZedEntity ze = _findTopOwner(entitySource);
		if (ze != null)
			return ze;
		return (ZedEntity) passed;
	}
	
	public static ZedEntity _findTopOwner( GenericEntity entitySource) {
		
		if (entitySource instanceof MethodEntity) {
			MethodEntity me = (MethodEntity) entitySource;
			return _findTopOwner(me.getOwner());
		}
		else if (entitySource instanceof FieldEntity) {
			FieldEntity fe = (FieldEntity) entitySource;
			return _findTopOwner(fe.getOwner());
		}
		else if (entitySource instanceof AnnotationEntity) {
			AnnotationEntity ae = (AnnotationEntity) entitySource;
			if (ae.getOwner() == null) {
				return ae;
			}
			return _findTopOwner(ae.getOwner());
		}
		else if (entitySource instanceof ClassEntity) {
			return (ZedEntity) entitySource;
		}
		else if (entitySource instanceof InterfaceEntity){
			return (ZedEntity) entitySource;
		}
		else if (entitySource instanceof EnumEntity) {
			return (ZedEntity) entitySource;
		}
		else {		
			return null;
		}
	}
}
