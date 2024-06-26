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
package com.braintribe.model.processing.query.parser.impl.listener;

import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlParser.JoinFunctionContext;
import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlParser.ListIndexContext;
import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlParser.MapKeyContext;
import com.braintribe.model.processing.query.parser.impl.context.JoinFunctionCustomContext;
import com.braintribe.model.processing.query.parser.impl.context.basetype.DefaultCustomContext;
import com.braintribe.model.query.Join;
import com.braintribe.model.query.functions.ListIndex;
import com.braintribe.model.query.functions.MapKey;

public abstract class GmqlJoinFunctionParserListener extends GmqlSelectQueryParserListener {

	@Override
	public void exitJoinFunction(JoinFunctionContext ctx) {
		propagateChildResult(ctx);
	}

	@Override
	public void exitListIndex(ListIndexContext ctx) {
		String alias = ((DefaultCustomContext) takeValue(ctx.alias()).cast()).getReturnValue();
		Join join = acquireJoin(alias);
		ListIndex listIndex = $.listIndex(join);
		addToObjectsWithSourcesList(listIndex);
		setValue(ctx, new JoinFunctionCustomContext(listIndex));
	}

	@Override
	public void exitMapKey(MapKeyContext ctx) {
		String alias = ((DefaultCustomContext) takeValue(ctx.alias()).cast()).getReturnValue();
		Join join = acquireJoin(alias);
		MapKey mapKey = $.mapKey(join);
		addToObjectsWithSourcesList(mapKey);
		setValue(ctx, new JoinFunctionCustomContext(mapKey));
	}

}
