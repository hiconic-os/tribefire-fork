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
package com.braintribe.gwt.gmview.client.parse.expert;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;

import com.braintribe.gwt.gmview.client.parse.SimpleTypeParser;
import com.braintribe.gwt.utils.client.FastSet;

public class LocalizedBooleanParser implements Function<String, Boolean> {

	private SimpleTypeParser owner;

	private Set<String> en_true = asSet("yes", "true");
	private Set<String> en_false = asSet("no", "false");

	private Set<String> de_true = asSet("ja", "wahr");
	private Set<String> de_false = asSet("nein", "falsch");

	private static Set<String> asSet(String... strings) {
		return new FastSet(Arrays.asList(strings));
	}

	public LocalizedBooleanParser(SimpleTypeParser owner) {
		this.owner = owner;
	}

	@Override
	public Boolean apply(String index) {
		Set<String> trues = en_true;
		Set<String> falses = en_false;

		if (owner.locale().startsWith("de")) {
			trues = de_true;
			falses = de_false;
		}

		if (trues.contains(index.toLowerCase())) {
			return Boolean.TRUE;
		}

		if (falses.contains(index.toLowerCase())) {
			return Boolean.FALSE;
		}

		return null;
	}

}
