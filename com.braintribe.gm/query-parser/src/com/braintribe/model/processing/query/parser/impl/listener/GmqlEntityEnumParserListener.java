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

import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.generic.value.EnumReference;
import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlParser.EntityReferenceContext;
import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlParser.EnumReferenceContext;
import com.braintribe.model.processing.query.parser.impl.autogenerated.GmqlParser.TypeSignatureContext;
import com.braintribe.model.processing.query.parser.impl.context.EntityReferenceCustomContext;
import com.braintribe.model.processing.query.parser.impl.context.EnumReferenceCustomContext;
import com.braintribe.model.processing.query.parser.impl.context.ObjectCustomContext;
import com.braintribe.model.processing.query.parser.impl.context.ValueCustomContext;
import com.braintribe.model.processing.query.parser.impl.context.basetype.BooleanCustomContext;
import com.braintribe.model.processing.query.parser.impl.context.basetype.DefaultCustomContext;

public abstract class GmqlEntityEnumParserListener extends GmqlIdentifierParserListener {

	@Override
	public void exitEntityReference(EntityReferenceContext ctx) {
		boolean persistedEntity = ctx.booleanValue() != null ? ((BooleanCustomContext) takeValue(ctx.booleanValue()).cast()).getReturnValue() : true;

		String typeSignature = ((DefaultCustomContext) takeValue(ctx.identifier()).cast()).getReturnValue();
		Object id = ((ValueCustomContext<?>) takeValue(ctx.value()).cast()).getReturnValue();
		String partition = ctx.stringValue() != null ? ((DefaultCustomContext) takeValue(ctx.stringValue()).cast()).getReturnValue() : null;

		EntityReference reference = $.entityReference(persistedEntity, typeSignature, id, partition);

		setValue(ctx, new EntityReferenceCustomContext(reference));
	}

	@Override
	public void exitEnumReference(EnumReferenceContext ctx) {
		String identifierPath = ((DefaultCustomContext) takeValue(ctx.identifier()).cast()).getReturnValue();
		String value = ((DefaultCustomContext) takeValue(ctx.genericIdentifier()).cast()).getReturnValue();
		EnumReference reference = $.enumReference(identifierPath, value);

		setValue(ctx, new EnumReferenceCustomContext(reference));
	}

	@Override
	public void exitTypeSignature(TypeSignatureContext ctx) {
		Object value = ((ValueCustomContext<?>) takeValue(ctx.value()).cast()).getReturnValue();
		setValue(ctx, new ObjectCustomContext($.entitySignature(value)));
	}

}
