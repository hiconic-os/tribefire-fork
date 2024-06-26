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
package com.braintribe.logging.juli.filters.logger;

import com.braintribe.logging.juli.formatters.simple.SimpleFormatter1;

/**
 * Extends {@link LoggerFilter} without adding any new methods or features. The purpose of this extension is to be able to configure multiple
 * <code>LoggerFilter</code> instances (with different settings). This is the same work-around as used by {@link SimpleFormatter1}.
 */
public class LoggerFilter6 extends LoggerFilter {
	// Intentionally left empty
}
