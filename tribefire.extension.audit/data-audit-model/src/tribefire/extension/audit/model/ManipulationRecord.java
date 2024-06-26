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
package tribefire.extension.audit.model;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SelectiveInformation("${manipulationType} ${instanceType} ${instanceProperty}")
public interface ManipulationRecord extends GenericEntity {

	EntityType<ManipulationRecord> T = EntityTypes.T(ManipulationRecord.class);

	String user = "user";
	String date = "date";
	String manipulationType = "manipulationType";
	String instanceType = "instanceType";
	String instanceId = "instanceId";
	String instancePartition = "instancePartition";
	String instanceProperty = "instanceProperty";
	String value = "value";
	String previousValue = "previousValue";
	String overflowValue = "overflowValue";
	String overflowPreviousValue = "overflowPreviousValue";
	String transactionId = "transactionId";
	String sequenceNumber = "sequenceNumber";
	String userIpAddress = "userIpAddress";

	String getUser();
	void setUser(String user);

	Date getDate();
	void setDate(Date date);

	/** defines if an Entitiy is a preliminary instance or if the manipulation was persisted to the database */
	boolean getPreliminaryInstance();
	/** defines if an Entitiy is a preliminary instance or if the manipulation was persisted to the database */
	void setPreliminaryInstance(boolean arg);

	ManipulationType getManipulationType();
	void setManipulationType(ManipulationType manipulationType);

	String getInstanceType();
	void setInstanceType(String instanceType);

	String getInstanceId();
	void setInstanceId(String instanceId);
	
	String getInstancePartition();
	void setInstancePartition(String instancePartition);

	String getInstanceProperty();
	void setInstanceProperty(String propertyName);

	String getValue();
	void setValue(String value);
	
	String getPreviousValue();
	void setPreviousValue(String previousValue);
	
	String getOverflowValue();
	void setOverflowValue(String value);
	
	String getOverflowPreviousValue();
	void setOverflowPreviousValue(String value);
	
	String getTransactionId();
	void setTransactionId(String transactionId);

	long getSequenceNumber();
	void setSequenceNumber(long sequenceNumber);

	String getUserIpAddress();
	void setUserIpAddress(String userIpAddress);

	String getAccessId();
	void setAccessId(String accessId);
}