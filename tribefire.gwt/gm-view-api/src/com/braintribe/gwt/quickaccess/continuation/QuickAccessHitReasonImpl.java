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
package com.braintribe.gwt.quickaccess.continuation;

import java.util.List;
import java.util.function.Function;

import com.braintribe.filter.pattern.Range;
import com.braintribe.gwt.quickaccess.api.QuickAccessHitReason;

public class QuickAccessHitReasonImpl<T> implements QuickAccessHitReason<T> {

	private Function<T, String> stringRepresentationProvider;
	private List<Range> ranges;

	public QuickAccessHitReasonImpl(Function<T, String> stringRepresentationProvider, List<Range> ranges) {
		super();
		this.stringRepresentationProvider = stringRepresentationProvider;
		this.ranges = ranges;
	}

	@Override
	public Function<T, String> getStringRepresentationProvider() {
		return stringRepresentationProvider;
	}

	@Override
	public List<Range> getRanges() {
		return ranges;
	}

}
