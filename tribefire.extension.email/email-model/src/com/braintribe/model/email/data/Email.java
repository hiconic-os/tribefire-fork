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
package com.braintribe.model.email.data;

import java.util.Date;
import java.util.List;

import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

/**
 *
 */
@SelectiveInformation("Email")
public interface Email extends HasEmailAddress {

	final EntityType<Email> T = EntityTypes.T(Email.class);

	String subject = "subject";
	String textBody = "textBody";
	String htmlBody = "htmlBody";
	String attachments = "attachments";
	String inlineAttachments = "inlineAttachments";
	String bccList = "bccList";
	String creationDate = "creationDate";
	String fromList = "fromList";
	String ccList = "ccList";
	String bounceTo = "bounceTo";
	String sentDate = "sentDate";
	String dispositionNotificationTo = "dispositionNotificationTo";
	String returnReceiptTo = "returnReceiptTo";
	String replyToList = "replyToList";
	String toList = "toList";

	@Name("To")
	@Mandatory
	List<Recipient> getToList();
	void setToList(List<Recipient> toList);


	@Name("Reply To")
	List<Recipient> getReplyToList();
	void setReplyToList(List<Recipient> replyToList);

	@Name("Disposition Notification To")
	Recipient getDispositionNotificationTo();
	void setDispositionNotificationTo(Recipient dispositionNotificationTo);

	@Name("Return Receipt To")
	Recipient getReturnReceiptTo();
	void setReturnReceiptTo(Recipient returnReceiptTo);

	@Name("Sent Date")
	Date getSentDate();
	void setSentDate(Date sentDate);

	@Name("Bounce To")
	Recipient getBounceTo();
	void setBounceTo(Recipient bounceTo);

	@Name("CC")
	List<Recipient> getCcList();
	void setCcList(List<Recipient> ccList);

	@Name("From")
	@Mandatory
	List<Sender> getFromList();
	void setFromList(List<Sender> fromList);

	@Name("Creation Date")
	Date getCreationDate();
	void setCreationDate(Date creationDate);

	@Name("BCC")
	List<Recipient> getBccList();
	void setBccList(List<Recipient> bccList);

	@Name("Subject")
	@Mandatory
	String getSubject();
	void setSubject(String subject);

	@Name("Text Body")
	String getTextBody();
	void setTextBody(String textBody);

	@Name("HTML Body")
	String getHtmlBody();
	void setHtmlBody(String htmlBody);

	@Name("Attachments")
	List<Resource> getAttachments();
	void setAttachments(List<Resource> attachments);

	@Name("Inline/Embedded Attachments")
	List<Resource> getInlineAttachments();
	void setInlineAttachments(List<Resource> inlineAttachments);

}
