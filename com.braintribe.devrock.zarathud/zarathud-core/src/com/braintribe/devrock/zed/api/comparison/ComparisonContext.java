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
package com.braintribe.devrock.zed.api.comparison;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.findings.ComparisonProcessFocus;

/** 
 * a context used during the comparison process 
 * @author pit
 */
public interface ComparisonContext {

	/**
	 * eagerly add name of currently processed entity 
	 * @param name - the name of the entity 
	 */
	void addProcessed(String name);
	boolean isProcessed(String name);

	/**
	 * @param fp - add a {@link FingerPrint} to the context
	 */
	void addFingerPrint(FingerPrint fp);
	
	/**
	 * @return - the accumulated {@link FingerPrint}s of differences found while comparing
	 */
	List<FingerPrint> getFingerPrints();
	
	/**
	 * @return - the entity currently being processed
	 */
	GenericEntity getCurrentEntity();
	
	/**
	 * @param current - the {@link GenericEntity} to be the new current entity 
	 */
	void pushCurrentEntity( GenericEntity current);
	
	/**
	 * drop the last pushed entitiy  
	 */
	void popCurrentEntity();
	
	/**
	 * @return - the entity currently being compared to the current base 
	 */
	GenericEntity getCurrentOther();
	
	/**
	 * @param current - the {@link GenericEntity} to be the next comparison target
	 */
	void pushCurrentOther( GenericEntity current);	
	
	/**
	 * drop the last pushed compared-to entity 
	 */
	void popCurrentOther();

	/**
	 * @return - the current {@link ComparisonProcessFocus} to contextualize generic comparators 
	 */
	ComparisonProcessFocus getCurrentProcessFocus();
	
	/**
	 * @param focus - the next {@link ComparisonProcessFocus}
	 */
	void pushCurrentProcessFocus( ComparisonProcessFocus focus);
	
	/**
	 * drop the last pushed {@link ComparisonProcessFocus} 
	 */
	void popCurrentProcessFocus();
}