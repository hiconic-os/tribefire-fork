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
package com.braintribe.logging.juli.formatters.simple;

/**
 * Extends {@link SimpleFormatter} without adding any new methods or features. This class is used as a work-around for the problem that in JUL/JULI
 * one can only configure formatters by type (and not by instance). If one e.g. wants to have a verbose format with timestamps, class info, etc. for
 * the file handler and a more compact format for the console handler, this won't work when just using {@link SimpleFormatter} twice. Instead one can
 * use {@link SimpleFormatter1} and {@link SimpleFormatter2} (maximum is 10).
 */
// It seems that JUL/JULI unfortunately doesn't support inner classes. Therefore this class needs to be in its own file.
public class SimpleFormatter1 extends SimpleFormatter {
	// no additional methods or features
}
