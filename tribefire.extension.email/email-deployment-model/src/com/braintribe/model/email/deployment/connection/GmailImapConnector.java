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
package com.braintribe.model.email.deployment.connection;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Unmodifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SelectiveInformation("Gmail IMAP Connector")
public interface GmailImapConnector extends ImapConnector {

	final EntityType<GmailImapConnector> T = EntityTypes.T(GmailImapConnector.class);

	@Mandatory
	@Initializer("'imap.gmail.com'")
	@Unmodifiable
	@Override
	String getHost();

	@Mandatory
	@Initializer("993")
	@Unmodifiable
	@Override
	Integer getPort();

}
