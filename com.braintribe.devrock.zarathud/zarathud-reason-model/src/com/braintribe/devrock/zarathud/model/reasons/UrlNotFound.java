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
package com.braintribe.devrock.zarathud.model.reasons;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * a reason to reflect inner scan errors of zed
 * @author pit
 *
 */
@SelectiveInformation("no matching URL found while scanning for ${scanExpression} during analysis of ${scannedType} within ${scannedResource}")
public interface UrlNotFound extends Reason {
		
	EntityType<UrlNotFound> T = EntityTypes.T(UrlNotFound.class);
	
	String scanExpression = "scanExpression";
	String scannedResource = "scannedResource";
	String scannedType = "scannedType";
	String combinedUrl = "combinedUrl";

	/**
	 * @return - the expression a resource was searched for
	 */
	String getScanExpression();
	void setScanExpression(String value);
	
	/**
	 * @return - the resource that contained the scan
	 */
	String getScannedResource();
	void setScannedResource(String value);
	
	/**
	 * @return - the type of the resource that was scanned
	 */
	String getScannedType();
	void setScannedType(String value);
	
	/**
	 * @return - the raw resource URL (contains both resource & type)
	 */
	String getCombinedUrl();
	void setCombinedUrl(String value);


}
