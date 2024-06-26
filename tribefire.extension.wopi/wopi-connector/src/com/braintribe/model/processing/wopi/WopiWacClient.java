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
package com.braintribe.model.processing.wopi;

import java.net.MalformedURLException;
import java.net.URL;

import com.braintribe.model.processing.wopi.model.WopiDiscovery.ProofKey;

/**
 * create URL for requesting document from MS Web App
 * 
 *
 */
public interface WopiWacClient {

	// get information which document it is and form URL (e.g. for .doc .xls .ppt etc.)
	String getUrlsrc(String actionName, String ext);

	URL getDocumentLink(String action, URL requestURL, String correlationId, String mimeType, String name, String accessToken, String accessTokenTtl)
			throws MalformedURLException;

	ProofKey proofKey();
}