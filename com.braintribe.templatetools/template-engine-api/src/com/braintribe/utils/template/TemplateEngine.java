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
package com.braintribe.utils.template;

import java.io.File;
import java.io.Reader;
import java.util.Map;

public interface TemplateEngine {

	public String applyTemplate( File template
			, String templateEncoding
			, Map<String, Object> data
			, boolean escapeHTMLEntities) throws Exception;

	public String applyTemplate( String buffer
			, Map<String, Object> data) throws Exception;

	public String applyTemplate( Reader templateReader
			, Map<String, Object> data) throws Exception;
	
	public String applyTemplate( Reader templateReader
			, Map<String, Object> data
			, boolean escapeHTMLEntities) throws Exception;

	public void applyTemplateToFile( File template
			, String templateEncoding
			, File targetFile
			, Map<String, Object> data
			, boolean escapeHTMLEntities) throws Exception;

}