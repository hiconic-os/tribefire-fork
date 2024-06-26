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
package com.braintribe.model.email.service;

import com.braintribe.model.email.service.reason.MailServerConnectionError;
import com.braintribe.model.email.service.reason.MailServerError;
import com.braintribe.model.email.service.reason.PostProcessingError;
import com.braintribe.model.email.service.reason.RetrieveConnectorMissing;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.UnsatisfiedBy;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

@Description("Gets unread emails from the email server in .eml format.")
@UnsatisfiedBy(RetrieveConnectorMissing.class)
@UnsatisfiedBy(MailServerError.class)
@UnsatisfiedBy(PostProcessingError.class)
@UnsatisfiedBy(MailServerConnectionError.class)
public interface ReceiveEmails extends EmailServiceRequest {

	EntityType<ReceiveEmails> T = EntityTypes.T(ReceiveEmails.class);

	@Name("Connector ID")
	@Description("The external ID of the connection that should be used. When this is not set, the first matching connector will be used.")
	String getConnectorId();
	void setConnectorId(String connectorId);

	@Name("Folder")
	@Description("The folder that should be used for retrieving emails. This is only used for IMAP connections. The default value is 'Inbox'.")
	@Initializer("'Inbox'")
	String getFolder();
	void setFolder(String folder);

	@Name("Maximum Emails")
	@Description("The maximum number of emails that should be retrieved.")
	@Initializer("50")
	Integer getMaxEmailCount();
	void setMaxEmailCount(Integer maxEmailCount);

	@Name("Post-processing")
	@Description("Determines what should be done with the email after retrieval. By default, mails are marked as read.")
	@Initializer("enum(com.braintribe.model.email.service.ReceivedEmailPostProcessing,MARK_READ)")
	ReceivedEmailPostProcessing getPostProcessing();
	void setPostProcessing(ReceivedEmailPostProcessing prostProcessing);

	@Name("Get Unread Mails Only")
	@Description("When set to true, only new (unread) emails will be retrieved. If false, all emails available will be retrieved.")
	@Initializer("true")
	boolean getUnreadOnly();
	void setUnreadOnly(boolean unreadOnly);

	@Name("Search Expression")
	@Description("Limited search expression to select specific mails (e.g., 'from office@example.com'")
	String getSearchExpression();
	void setSearchExpression(String searchExpression);

	@Override
	EvalContext<? extends ReceivedEmails> eval(Evaluator<ServiceRequest> evaluator);

}
