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
package com.braintribe.model.wopi.service.integration;

import static tribefire.extension.wopi.model.WopiMetaDataConstants.DOCUMENT_MODE_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.DOCUMENT_MODE_NAME;

import java.util.Set;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.wopi.DocumentMode;

/**
 * Add TEST document
 * 
 *
 */
public interface EnsureTestDoc extends WopiRequest {

	EntityType<EnsureTestDoc> T = EntityTypes.T(EnsureTestDoc.class);

	@Override
	EvalContext<? extends EnsureTestDocResult> eval(Evaluator<ServiceRequest> evaluator);

	String documentMode = "documentMode";
	String testNames = "testNames";

	@Name(DOCUMENT_MODE_NAME)
	@Description(DOCUMENT_MODE_DESCRIPTION)
	@Initializer("enum(com.braintribe.model.wopi.DocumentMode,view)")
	@Mandatory
	DocumentMode getDocumentMode();
	void setDocumentMode(DocumentMode documentMode);

	// TODO: add name/description
	Set<String> getTestNames();
	void setTestNames(Set<String> testNames);

}