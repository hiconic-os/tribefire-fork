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
package com.braintribe.gwt.gmview.client.parse;

import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.braintribe.gwt.gmview.action.client.ParserResult;
import com.braintribe.model.generic.reflection.SimpleTypes;


public class MultiDescriptionStringParser implements Function<ParserArgument, List<ParserResult>> {
	
	private static final String STRING_SIGNATURE = SimpleTypes.TYPE_STRING.getTypeSignature();
	private final Set<String> descriptions;
	
	public MultiDescriptionStringParser(String... descriptions) {
		this(asSet(descriptions));
	}
	
	public MultiDescriptionStringParser(Set<String> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public List<ParserResult> apply(ParserArgument parserArgument) throws RuntimeException {
		if (!parserArgument.hasValue()) {
			return Collections.emptyList();
		}
		
		String value = parserArgument.getValue();
		
		List<ParserResult> results = new ArrayList<>();
		for (String description : descriptions) {
			results.add(new ParserResult(description, STRING_SIGNATURE, value));
		}
		
		return results;
	}

}
