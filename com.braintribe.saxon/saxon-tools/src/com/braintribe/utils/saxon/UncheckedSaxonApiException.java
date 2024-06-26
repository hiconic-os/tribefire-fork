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
package com.braintribe.utils.saxon;

import com.braintribe.common.lcd.uncheckedcounterpartexceptions.UncheckedCounterpartException;

import net.sf.saxon.s9api.SaxonApiException;

/**
 * Unchecked counterpart of {@link SaxonApiException}.
 *
 * @author michael.lafite
 */
public class UncheckedSaxonApiException extends UncheckedCounterpartException {

	private static final long serialVersionUID = 1316641584745480080L;

	public UncheckedSaxonApiException(final String message) {
		super(message);
	}

	public UncheckedSaxonApiException(final String message, final SaxonApiException cause) {
		super(message, cause);
	}
}
