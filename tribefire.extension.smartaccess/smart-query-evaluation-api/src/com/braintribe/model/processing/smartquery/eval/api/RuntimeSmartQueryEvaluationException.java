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
package com.braintribe.model.processing.smartquery.eval.api;

import com.braintribe.model.processing.query.eval.api.RuntimeQueryEvaluationException;

public class RuntimeSmartQueryEvaluationException extends RuntimeQueryEvaluationException {

	private static final long serialVersionUID = 277523785843230760L;

	public RuntimeSmartQueryEvaluationException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeSmartQueryEvaluationException(String message) {
		super(message);
	}

	public RuntimeSmartQueryEvaluationException(Throwable cause) {
		super(cause);
	}

}
