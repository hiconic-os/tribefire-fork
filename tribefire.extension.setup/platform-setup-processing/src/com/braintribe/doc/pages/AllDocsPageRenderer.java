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
package com.braintribe.doc.pages;

import java.util.Map;

import com.braintribe.doc.UniversalPath;

public class AllDocsPageRenderer extends AbstractPageRenderer {

	public AllDocsPageRenderer() {
		super("all-docs.ftlh");
	}

	@Override
	protected Map<String, Object> dataModel(PageRenderingContext context) {
		Map<String, Object> dataModel = context.newDataModelWithAssets("");
		dataModel.put("tags", context.getDocumentationContent().getUsedTags());
		
		return dataModel;
	}

	@Override
	protected UniversalPath targetFile(PageRenderingContext context) {
		return UniversalPath.start("all-docs.html"); 
	}
	
	
}