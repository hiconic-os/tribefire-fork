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
package com.braintribe.model.processing.query.stringifier.experts;

import com.braintribe.model.processing.query.api.stringifier.QueryStringifierRuntimeException;
import com.braintribe.model.processing.query.api.stringifier.experts.Alias;
import com.braintribe.model.processing.query.api.stringifier.experts.Stringifier;
import com.braintribe.model.processing.query.stringifier.BasicQueryStringifierContext;
import com.braintribe.model.query.Source;
import com.braintribe.model.query.conditions.FulltextComparison;

public class FulltextComparisonStringifier implements Stringifier<FulltextComparison, BasicQueryStringifierContext> {
	@Override
	public String stringify(FulltextComparison fulltextComparison, BasicQueryStringifierContext context) throws QueryStringifierRuntimeException {
		StringBuilder queryString = new StringBuilder();
		queryString.append("fullText(");

		Source source = fulltextComparison.getSource();
		Alias alias = context.acquireAlias(source);
		if (alias != null) {
			if (context.isReplaceAliasInUse()) {
				queryString.append(context.getReplaceAliasTag());
				if (source != null) {
					context.setDoRemoveReplaceAliasTag(false);
				}
			} else {
				queryString.append(alias.getName());
			}
			queryString.append(", ");
		}
		// Add full-text value
		context.stringifyAndAppend(fulltextComparison.getText(), queryString);

		queryString.append(")");
		return queryString.toString();
	}
}