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
package com.braintribe.gwt.security.client;

import java.util.function.Predicate;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.gm.model.reason.ReasonException;
import com.braintribe.gm.model.security.reason.InvalidSession;
import com.braintribe.gm.model.security.reason.SessionExpired;
import com.braintribe.gm.model.security.reason.SessionNotFound;

/**
 * This filter will match against a {@link SessionNotFound} or a {@link SessionExpired} reason, which are the reasons of a {@link ReasonException}.
 * @author michel.docouto
 *
 */
public class SessionNotFoundReasonFilter implements Predicate<Throwable> {

	@Override
	public boolean test(Throwable exception) {
		if (!(exception instanceof ReasonException))
			return false;
		
		Reason reason = ((ReasonException) exception).getReason();
		if (reason instanceof SessionNotFound || reason instanceof SessionExpired || reason instanceof InvalidSession)
			return true;
		
		return false;
	}

}
