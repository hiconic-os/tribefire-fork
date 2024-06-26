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
package com.braintribe.qa.tribefire.process;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.qa.tribefire.test.Invoice;

import tribefire.extension.process.model.data.HasExecutionPriority;
import tribefire.extension.process.model.data.Process;

public interface InvoiceProcessWithPriority extends GenericEntity, Process, HasExecutionPriority {

	EntityType<InvoiceProcessWithPriority> T = EntityTypes.T(InvoiceProcessWithPriority.class);

	String finished = "finished";
	String testIterationId = "testIterationId";
	String invoice = "invoice";
	String invoiceState = "invoiceState";
	String ordering = "ordering";

	Invoice getInvoice();
	void setInvoice(Invoice paramInvoice);

	String getInvoiceState();
	void setInvoiceState(String paramString);

	String getTestIterationId();
	void setTestIterationId(String testIterationId);

	Date getFinished();
	void setFinished(Date finished);

	Long getOrdering();
	void setOrdering(Long ordering);

}
