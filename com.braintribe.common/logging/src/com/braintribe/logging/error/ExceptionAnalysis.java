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
package com.braintribe.logging.error;

import java.util.ArrayList;
import java.util.List;

public class ExceptionAnalysis implements ExceptionExplanations {
	private final List<String> explanations = new ArrayList<>();

	@Override
	public void addExplanation(final String text) {
		this.explanations.add(text);
	}

	public void analyse(final Throwable throwable, final Iterable<ExceptionAnalyser> analysers) {
		for (final ExceptionAnalyser analyser : analysers) {
			analyser.analyseException(throwable, this);
		}
	}

	public String getSummary() {
		final StringBuilder builder = new StringBuilder();

		if (this.explanations.size() > 0) {
			for (final String explanation : this.explanations) {
				if (builder.length() != 0) {
					builder.append("<br/>");
				}
				builder.append(explanation);
			}

			return builder.toString();
		} else {
			return null;
		}
	}
}
