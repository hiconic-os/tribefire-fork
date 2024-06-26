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
package com.braintribe.model.generic.validation.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.validation.ValidatorResult;

public class ValidationGroupMessageExpert {
	
	public static List<ValidatorResult> prepareGroupValidatorResult(List<ValidatorResult> listValidatorResult) {
		if (listValidatorResult == null)
			return null;
		
		List<ValidatorResult> result = null;

		Map<String,List<ValidatorResult>> mapByPropertyName = new HashMap<>();
		
		//split by propertyName
        for (ValidatorResult validatorResult : listValidatorResult) {
        	if (validatorResult == null || validatorResult.getPropertyName() == null || validatorResult.getPropertyName().isEmpty()) 
        		continue;

        	List<ValidatorResult> listPropertyValidatorResult = mapByPropertyName.get(validatorResult.getPropertyName());
        	if (listPropertyValidatorResult == null)
        		listPropertyValidatorResult = new ArrayList<>();
        	
        	listPropertyValidatorResult.add(validatorResult);
        	mapByPropertyName.put(validatorResult.getPropertyName(), listPropertyValidatorResult);
        }
		
        if (mapByPropertyName.isEmpty())
        	return null;
        
        result = new ArrayList<>();
        for (String propertyName : mapByPropertyName.keySet()) {        
			String prior1 = null;
			String prior2 = null;
			String prior3 = null;
			
			List<Manipulation> listManipulation = new ArrayList<>();
			
	        for (ValidatorResult validatorResult : mapByPropertyName.get(propertyName)) {
	        		switch(validatorResult.getMessageType()) {
	        		  case mandatory:
	          			prior1 = LocalizedText.INSTANCE.stringDenyEmpty();
	        		    break;
	        		  case regex:
	           			prior2 = LocalizedText.INSTANCE.stringRegexpMessage();
	        		    break;
	        		  case greatherThan:
	             		prior2 = LocalizedText.INSTANCE.stringGreatherThan(validatorResult.getMessageParameter());
	          		    break;
	        		  case greatherEqual:
	             		prior2 = LocalizedText.INSTANCE.stringGreatherEqual(validatorResult.getMessageParameter());
	          		    break;
	        		  case greatherEqualLength:
		             		prior2 = LocalizedText.INSTANCE.stringGreatherEqualLength(validatorResult.getMessageParameter());
		          		    break;
	        		  case lessThan:
	        			  prior3 = LocalizedText.INSTANCE.stringLesserThan(validatorResult.getMessageParameter());
	            		   break;
	        		  case lessEqual:
	        			  prior3 = LocalizedText.INSTANCE.stringLesserEqual(validatorResult.getMessageParameter());
	          		    break;
	        		  case lessEqualLength:
	        			  prior3 = LocalizedText.INSTANCE.stringLesserEqualLength(validatorResult.getMessageParameter());
	          		    break;
					default:
						break;        		    
	        		}  
	        		listManipulation.addAll(validatorResult.getListManipulation());
	        }
	        
	        if (prior1 == null && prior2 == null && prior3 == null) {
	        	return null;
	        }
	
	        StringBuilder sb = new StringBuilder();
	        if (prior1 != null) {
	        	sb.append(prior1);
	        	if (prior2 != null || prior3 != null)
	        		sb.append(" ").append(LocalizedText.INSTANCE.stringAnd()).append(" ");
	        	
	        } else {
	        	sb.append(LocalizedText.INSTANCE.stringAllowEmpty());
	        	if (prior2 != null || prior3 != null)
	        		sb.append(" ").append(LocalizedText.INSTANCE.stringOr()).append(" ");
	        }
	        
	        if (prior2 != null) {
	        	sb.append(prior2);
	        	if (prior3 != null)
	        		sb.append(" ").append(LocalizedText.INSTANCE.stringAnd()).append(" ");        		
	        }
	        
	        if (prior3 != null) 
	        	sb.append(prior3);        
	        
	        ValidatorResult groupValidatorResult = new ValidatorResult();
	        groupValidatorResult.setPropertyName(propertyName);
	        groupValidatorResult.setResult(false);
	        groupValidatorResult.setResultMessage(sb.toString());
	        groupValidatorResult.getListManipulation().addAll(listManipulation);
	        result.add(groupValidatorResult); 
        }
		return result;
	}
}
