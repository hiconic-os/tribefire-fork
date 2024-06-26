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
package com.braintribe.model.wopi;

/**
 * Status of a {@link WopiSession}
 * 
 *
 */
public enum WopiStatus {
	/**
	 * {@link WopiSession} is ready to be used to edit/view documents
	 */
	open,
	/**
	 * {@link WopiSession} is expired because of too long opened {@link WopiSession} - can't be used for edit/view
	 * documents; will be cleanup up
	 */
	expired,
	/**
	 * {@link WopiSession} is closed by user interaction - can't be used for edit/view documents; will be cleaned up
	 */
	closed
}
