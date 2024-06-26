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
package tribefire.extension.demo.model.data.process;

import java.util.Date;

import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.process.Process;

import tribefire.extension.demo.model.data.Person;

public interface HolidayRequestProcess extends Process {
	
	EntityType<HolidayRequestProcess> T = EntityTypes.T(HolidayRequestProcess.class);
	
	
	/*
	 * Constants for each property name.
	 */
	public static final String employee = "employee";
	public static final String from = "from";
	public static final String to = "to";
	public static final String comment = "comment";
	public static final String approvalStatus = "approvalStatus";
	public static final String assignee = "assignee";
	
	@Mandatory
	Person getEmployee();
	void setEmployee(Person employee);
	
	@Mandatory
	Date getFrom();
	void setFrom(Date from);
	
	@Mandatory
	Date getTo();
	void setTo(Date to);
	
	String getComment();
	void setComment(String comment);
	
	String getApprovalStatus();
	void setApprovalStatus(String approvalStatus);
	
	Person getAssignee();
	void setAssignee(Person assignee);
	
	
}
