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

import java.util.Collections;

import com.braintribe.devrock.zarathud.model.common.EntityNode;
import com.braintribe.devrock.zarathud.model.common.FingerPrintNode;
import com.braintribe.devrock.zarathud.model.common.FingerPrintRating;
import com.braintribe.devrock.zarathud.model.common.ReferenceNode;
import com.braintribe.devrock.zed.forensics.fingerprint.HasFingerPrintTokens;
import com.braintribe.devrock.zed.ui.ZedViewingContext;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;
import com.braintribe.zarathud.model.forensics.data.ArtifactReference;

/**
 * a selection of commonly used small functions
 * @author pit
 *
 */
public class CommonContentTransposer implements HasFingerPrintTokens{

	/**
	 * transpose a {@link ArtifactReference} from zede
	 * @param reference - the {@link ArtifactReference}
	 * @return - the {@link ReferenceNode}
	 */
	public static ReferenceNode transpose( ArtifactReference reference) {
		ReferenceNode node = ReferenceNode.T.create();
		
		node.setSource( transpose( reference.getSource()));
		node.setTarget( transpose(reference.getTarget()));
		
		return node;
	}
	
	/**
	 * @param zedEntity
	 * @return
	 */
	public static EntityNode transpose( ZedEntity zedEntity) {
		EntityNode node = EntityNode.T.create();

		node.setName( zedEntity.getName());
		node.setModuleName( zedEntity.getModuleName());

		return node;
	}
	
	/**
	 * transpose a single {@link FingerPrint}
	 * @param context - the {@link ZedViewingContext}
	 * @param fp - the {@link FingerPrint}
	 * @return - the {@link FingerPrintNode}
	 */
	public static FingerPrintNode transpose( ZedViewingContext context, FingerPrint fp) {
		ForensicsRating rating = context.getRatingRegistry().getWorstRatingOfFingerPrints( Collections.singletonList( fp));
		FingerPrintNode node = FingerPrintNode.T.create();
		node.setFingerPrint( fp);
		//node.setMessage( fp.getSlots().get( ISSUE));
		node.setRating( transpose( rating));
		return node;
	}
	
	/**
	 * transposes a {@link ForensicsRating} from Zed to a {@link FingerPrintRating} for the UI
	 * @param forensicsRating - a {@link ForensicsRating} from zed's forensics  
	 * @return - a {@link FingerPrintRating} to display
	 */
	public static FingerPrintRating transpose( ForensicsRating forensicsRating) {
		switch( forensicsRating) {
		case ERROR:		
		case FATAL:
			return FingerPrintRating.error;		
		case WARN:
			return FingerPrintRating.warning;
		case INFO:
			return FingerPrintRating.info;						
		case IGNORE:			
			return FingerPrintRating.ignore;			
		case OK:
		default:
			return FingerPrintRating.ok;					
		}		
		
	}
}
