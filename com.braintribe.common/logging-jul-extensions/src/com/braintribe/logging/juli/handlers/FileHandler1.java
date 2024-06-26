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
package com.braintribe.logging.juli.handlers;

import com.braintribe.logging.juli.formatters.simple.SimpleFormatter1;

/**
 * Extends {@link FileHandler} without adding any new methods or features. This class is used as a work-around for the problem that in JUL one can
 * only configure handlers by type (and not by instance). This is similar to {@link SimpleFormatter1}, except that for JULI this is not needed (since
 * JULI supports multiple handler configs, but JUL doesn't.)
 */
public class FileHandler1 extends FileHandler {
	// no additional methods or features
}
